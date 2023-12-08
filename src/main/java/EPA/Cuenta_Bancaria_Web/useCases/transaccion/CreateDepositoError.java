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
public class CreateDepositoError {

    @Autowired
    TransaccionResource transaccionResource;

    @Autowired
    private RabbitMqPublisher eventBus;

    public Mono<ServerResponse> apply(ServerRequest request) {
        String idCuenta = request.pathVariable("id_Cuenta");
        BigDecimal monto = new BigDecimal(request.pathVariable("monto"));
        Enum_Tipos_Deposito tipo = Enum_Tipos_Deposito.valueOf(request.pathVariable("tipo").toUpperCase());

        Mono<M_Transaccion_DTO> resultado = transaccionResource.Procesar_Deposito(idCuenta, tipo, monto);

        final M_Transaccion_DTO[] transaccionDTOContainer = new M_Transaccion_DTO[1];

        return resultado
                .flatMap(transaccionDTO -> {
                            transaccionDTOContainer[0] = (M_Transaccion_DTO) transaccionDTO;
                            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue("Error al procesar el depósito"))
                                    .switchIfEmpty(ServerResponse.badRequest().build())
                                    .doFinally(fini -> eventBus.publishTransaccionsError(transaccionDTO));
                        }
                )
                .onErrorResume(error -> handleError(error, transaccionDTOContainer[0]));
    }

    private Mono<ServerResponse> handleError(Throwable error, M_Transaccion_DTO transaccionDTO) {
        eventBus.publishError(transaccionDTO);
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Error al procesar el depósito"));
    }

}

