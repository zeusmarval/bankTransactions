package EPA.Cuenta_Bancaria_Web.Servicio.Transaccion;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_Repositorio_TransaccionMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("MONGO")
public class Transaccion_ImpMongo implements I_Transaccion
{
    @Autowired
    I_Repositorio_TransaccionMongo transaccion_repositorio;

    @Autowired
    I_RepositorioCuentaMongo cuenta_repositorio;
/*
    @Override
    public M_Transaccion_DTO Procesar_Deposito(String id_Cuenta, Enum_Tipos_Deposito tipo, BigDecimal monto)
    {
        BigDecimal costo = BigDecimal.ZERO;

        switch (tipo)
        {
            case CAJERO: costo = BigDecimal.valueOf(Double.valueOf(System.getenv("EPA.Deposito.Cajero"))); break;
            case SUCURSAL: costo = BigDecimal.valueOf(Double.valueOf(System.getenv("EPA.Deposito.Sucursal")));  break;
            case OTRA_CUENTA: costo = BigDecimal.valueOf(Double.valueOf(System.getenv("EPA.Deposito.OtraCuenta")));  break;
        }

        M_CuentaMongo cuentaCliente = cuenta_repositorio.findByid(id_Cuenta);

        if(cuentaCliente == null)
        {
            return null;
        }

        BigDecimal bdSaldoActual = cuentaCliente.getSaldo_Global();
        BigDecimal bdSaldoNuevo  = cuentaCliente.getSaldo_Global().add(monto.subtract(costo));

        M_TransaccionMongo transaccion = new M_TransaccionMongo(
                cuentaCliente,
                monto,
                bdSaldoActual,
                bdSaldoNuevo,
                costo,
                tipo.toString()
        );

        cuentaCliente.setSaldo_Global(bdSaldoNuevo);

        //M_Transaccion transaccion_Creada = transaccion_repositorio.create(transaccion);
        M_TransaccionMongo transaccion_Creada = transaccion_repositorio.save(transaccion);
        M_CuentaMongo cuentaActualizada = cuenta_repositorio.save(cuentaCliente);

        if(transaccion_Creada == null)
        {
            return null;
        }

        M_Transaccion_DTO dtoCreado = new M_Transaccion_DTO(transaccion_Creada.getId(),
                new M_Cuenta_DTO(transaccion_Creada.getCuenta().getId(),
                        new M_Cliente_DTO(transaccion_Creada.getCuenta().getCliente().getId(),
                                transaccion_Creada.getCuenta().getCliente().getNombre()
                        ),
                        transaccion_Creada.getCuenta().getSaldo_Global()
                ), transaccion_Creada.getMonto_transaccion(),
                transaccion_Creada.getSaldo_inicial(),
                transaccion_Creada.getSaldo_final(),
                transaccion_Creada.getCosto_tansaccion(),
                transaccion_Creada.getTipo()
        );

        return dtoCreado;
    }
*/
    @Override
    public Flux<M_Transaccion_DTO> findAll()
    {
        return transaccion_repositorio.findAll()
                .map(transaccion -> {
                    return new M_Transaccion_DTO(transaccion.getId(),
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
                    );
                });
    }
}
