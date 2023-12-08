package EPA.Cuenta_Bancaria_Web.useCases.transaccion;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GetTransacciones {

    @Autowired
    I_Repositorio_TransaccionMongo iRepositorioTransaccionMongo;
    @Autowired
    private RabbitMqPublisher eventBus;
    public Mono<ServerResponse> apply(ServerRequest request) {
        Flux<M_TransaccionMongo> transaccionesFlux = iRepositorioTransaccionMongo.findAll();

        return transaccionesFlux.map(transaccion -> new M_Transaccion_DTO(transaccion.getId(),
                new M_Cuenta_DTO(transaccion.getCuenta().getId(),
                        new M_Cliente_DTO(transaccion.getCuenta().getCliente().getId(),
                                transaccion.getCuenta().getCliente().getNombre()
                        ),
                        transaccion.getCuenta().getSaldo_Global()
                ),
                transaccion.getMonto_transaccion(),
                transaccion.getSaldo_inicial(),
                transaccion.getSaldo_final(),
                transaccion.getCosto_tansaccion(),
                transaccion.getTipo()
        )).collectList()
                .flatMap(transaccionesList ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(transaccionesList))
                                .doOnSuccess(success -> eventBus.publishAll(transaccionesList))
                )
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(this::handleError);

    }

    private Mono<ServerResponse> handleError(Throwable error) {
        eventBus.publishError(error.getMessage());

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Error al obtener la lista de transacciones"));
    }

}
