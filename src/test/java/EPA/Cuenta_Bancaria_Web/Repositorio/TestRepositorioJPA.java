package EPA.Cuenta_Bancaria_Web.Repositorio;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.JPA.I_Repositorio_Cuenta_JPA;
import EPA.Cuenta_Bancaria_Web.Repositorio.JPA.I_Repositorio_Transaccion_JPA;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_Repositorio_TransaccionMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorioJPA
{
    @Autowired
    private I_Repositorio_Cuenta_JPA repositorioCuenta;

    @Autowired
    private I_Repositorio_Transaccion_JPA repositorioTransaccion;


    @Test
    @DisplayName("Obtener una cuenta especifica")
    public void testObtenerCuentaCreada()
    {
        // Fase 1. Guardar un documento en la base de datos embebida
        repositorioCuenta.save(new M_Cuenta("100",
                                             new M_Cliente("1", "Alexander"),
                                             BigDecimal.valueOf(100)
                )
        );

        //  Buscar el documento por su identificador
        Optional<M_Cuenta> cuentaBuscada = repositorioCuenta.findById("100");

        // Hago verificaciones
        assert(cuentaBuscada.isPresent());
        assert(cuentaBuscada.get().getId().equals("100"));
    }
}
