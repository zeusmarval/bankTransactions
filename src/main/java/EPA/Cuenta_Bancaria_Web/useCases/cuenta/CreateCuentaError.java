package EPA.Cuenta_Bancaria_Web.useCases.cuenta;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.bus.RabbitMqPublisher;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CreateCuentaError {

    @Autowired
    private CuentaResource cuentaResource;

    @Autowired
    private RabbitMqPublisher eventBus;

    public Mono<ServerResponse> apply(ServerRequest request) {
        Mono<M_Cuenta_DTO> cuentaMono = request.bodyToMono(M_Cuenta_DTO.class);

        return cuentaMono
                .flatMap(cuentaDTO -> {
                            return cuentaResource.crearCuenta(cuentaDTO)
                                    .flatMap(mCuentaDto -> Mono.error(new RuntimeException("error en listar cuentas")))
                                    .flatMap(cuenta -> ServerResponse.status(HttpStatus.CREATED)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(BodyInserters.fromValue(cuenta))

                                    ).onErrorResume(this::handleError);
                        }
                )
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    private Mono<ServerResponse> handleError(Throwable error) {
        eventBus.publishError(error.getMessage());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Error en la creación de la cuenta"));
    }
}
