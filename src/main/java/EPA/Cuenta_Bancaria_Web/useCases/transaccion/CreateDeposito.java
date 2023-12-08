package EPA.Cuenta_Bancaria_Web.useCases.transaccion;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Enum_Tipos_Deposito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CreateDeposito {

    @Autowired
    TransaccionResource transaccionResource;

    @Autowired
    private RabbitMqPublisher eventBus;

    public Mono<ServerResponse> apply(ServerRequest request) {
        String idCuenta = request.pathVariable("id_Cuenta");
        BigDecimal monto = new BigDecimal(request.pathVariable("monto"));
        Enum_Tipos_Deposito tipo = Enum_Tipos_Deposito.valueOf(request.pathVariable("tipo").toUpperCase());

        Mono<M_Transaccion_DTO> resultado = transaccionResource.Procesar_Deposito(idCuenta, tipo, monto);

        return resultado.flatMap(transaccionDTO ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(transaccionDTO))
                                .doOnSuccess(success -> eventBus.publishTransaccions(transaccionDTO))
                                .switchIfEmpty(ServerResponse.badRequest().build())
                )
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable error) {
        eventBus.publishError(error.getMessage());

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Error al procesar el depósito"));
    }
}
