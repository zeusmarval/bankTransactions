package EPA.Cuenta_Bancaria_Web.useCases.cuenta;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
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
public class GetCuentasError {

    @Autowired
    I_RepositorioCuentaMongo iRepositorioCuentaMongo;

    @Autowired
    private RabbitMqPublisher eventBus;

    public Mono<ServerResponse> apply(ServerRequest request) {
        Flux<M_CuentaMongo> cuenta = iRepositorioCuentaMongo.findAll();
        return cuenta.collectList()
                .flatMap(transaccione -> Mono.error(new RuntimeException("error en cuenta")))
                .flatMap(cuentaList -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(cuentaList))
                        .doOnSuccess(success -> eventBus.publishAll(cuenta))
                )
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable error) {
        eventBus.publishError(error.getMessage());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Error al obtener la lista de cuentas"));
    }
}
