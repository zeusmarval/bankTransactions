package EPA.Cuenta_Bancaria_Web;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.useCases.transaccion.GetTransacciones;
import EPA.Cuenta_Bancaria_Web.useCases.transaccion.TransaccionResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTransaccionesTest {

    @Mock
    private I_Repositorio_TransaccionMongo iRepositorioTransaccionMongo;
    //private TransaccionResource transaccionResource;

    @InjectMocks
    private GetTransacciones getTransacciones;

    /*@Test
    void testGetTransacciones() {
        // Datos ficticios para la prueba
        M_Cuenta_DTO cuentaDTO = new M_Cuenta_DTO("1", new M_Cliente_DTO("101", "John Doe"), BigDecimal.valueOf(1000.0));
        M_Transaccion_DTO transaccion1 = new M_Transaccion_DTO("1", cuentaDTO, BigDecimal.valueOf(50.0), BigDecimal.valueOf(950.0), BigDecimal.valueOf(1000.0), BigDecimal.valueOf(2.0), "Compra");
        M_Transaccion_DTO transaccion2 = new M_Transaccion_DTO("2", cuentaDTO, BigDecimal.valueOf(30.0), BigDecimal.valueOf(920.0), BigDecimal.valueOf(950.0), BigDecimal.valueOf(1.0), "Venta");

        when(iRepositorioTransaccionMongo.findAll()).thenReturn(Flux.just(transaccion1, transaccion2).map(transaccionesList -> transaccionesList).collectList());

        // Crear una implementación mock de ServerRequest
        ServerRequest request = mock(ServerRequest.class);

        // Configurar el comportamiento deseado cuando se llama al método queryParam
        //when(request.queryParam("parametro", String.class)).thenReturn(Optional.of("valor"));


        Mono<ServerResponse> result = getTransacciones.apply(request);


        StepVerifier.create(result)
                .expectNext(transaccion1)
                .expectNext(transaccion2)
                .verifyComplete();

    }
    */
}
