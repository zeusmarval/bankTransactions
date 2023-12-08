package EPA.Cuenta_Bancaria_Web.handlers.routes;

import EPA.Cuenta_Bancaria_Web.useCases.transaccion.CreateDeposito;
import EPA.Cuenta_Bancaria_Web.useCases.transaccion.CreateDepositoError;
import EPA.Cuenta_Bancaria_Web.useCases.transaccion.GetTransaccionError;
import EPA.Cuenta_Bancaria_Web.useCases.transaccion.GetTransacciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class C_Transaccion {

    @Autowired
    GetTransacciones getTransacciones;
    @Autowired
    GetTransaccionError getTransaccionError;
    @Autowired
    CreateDeposito createDeposito;
    @Autowired
    CreateDepositoError createDepositoError;
    @Bean
    public RouterFunction<ServerResponse> TransactionRoutes() {
        return RouterFunctions.route()
                .GET("/Transacciones/listar_transacciones", accept(MediaType.APPLICATION_JSON), getTransacciones::apply)
                .GET("/Transacciones/listar_transacciones/error", accept(MediaType.APPLICATION_JSON), getTransaccionError::apply)
                .POST("/Transacciones/Crear/Deposito/{tipo}/{id_Cuenta}/{monto}", createDeposito::apply)
                .POST("/Transacciones/error/Crear/Deposito/{tipo}/{id_Cuenta}/{monto}", createDepositoError::apply)
                .build();
    }

}
