package EPA.Cuenta_Bancaria_Web.handlers.routes;

import EPA.Cuenta_Bancaria_Web.useCases.cuenta.CreateCuenta;
import EPA.Cuenta_Bancaria_Web.useCases.cuenta.CreateCuentaError;
import EPA.Cuenta_Bancaria_Web.useCases.cuenta.GetCuentas;
import EPA.Cuenta_Bancaria_Web.useCases.cuenta.GetCuentasError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class C_Cuenta {

    @Autowired
    GetCuentas getCuentas;
    @Autowired
    GetCuentasError getCuentasError;
    @Autowired
    CreateCuenta createCuenta;
    @Autowired
    CreateCuentaError createCuentaError;

    @Bean
    public RouterFunction<ServerResponse> AccountsRoutes() {
        return RouterFunctions.route()
                .GET("/Cuentas/listar_cuentas", accept(MediaType.APPLICATION_JSON), getCuentas::apply)
                .GET("/Cuentas/listar_cuentas/error", accept(MediaType.APPLICATION_JSON), getCuentasError::apply)
                .POST("/Cuentas/Crear", accept(MediaType.APPLICATION_JSON), createCuenta::apply)
                .POST("/Cuentas/Crear/error", accept(MediaType.APPLICATION_JSON), createCuentaError::apply)
                .build();
    }
}
