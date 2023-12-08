package EPA.Cuenta_Bancaria_Web.useCases.transaccion;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
@Service
public class TransaccionResource {

    private final double COSTO_CAJERO = 2.0;

    private final double COSTO_SUCURSAL = 0.0;

    private final double COSTO_OTRO = 1.5;

    @Autowired
    I_RepositorioCuentaMongo cuenta_repositorio;

    @Autowired
    I_Repositorio_TransaccionMongo transaccion_repositorio;

    public Mono<M_Transaccion_DTO> Procesar_Deposito(String id_Cuenta, Enum_Tipos_Deposito tipo, BigDecimal monto)
    {
        return cuenta_repositorio.findById(id_Cuenta)
                .flatMap(cuenta -> {
                    BigDecimal costo = switch (tipo) {
                        case CAJERO -> BigDecimal.valueOf(COSTO_CAJERO);
                        case SUCURSAL -> BigDecimal.valueOf(COSTO_SUCURSAL);
                        case OTRA_CUENTA -> BigDecimal.valueOf(COSTO_OTRO);
                    };

                    BigDecimal bdSaldoActual = cuenta.getSaldo_Global();
                    BigDecimal bdSaldoNuevo  = cuenta.getSaldo_Global().add(monto.subtract(costo));

                    cuenta.setSaldo_Global(bdSaldoNuevo);

                    M_TransaccionMongo transaccion = new M_TransaccionMongo (
                            cuenta,
                            monto,
                            bdSaldoActual,
                            bdSaldoNuevo,
                            costo,
                            tipo.toString()
                    );

                    return transaccion_repositorio.save(transaccion)
                            .then(cuenta_repositorio.save(cuenta))
                            .thenReturn(transaccion);
                })
                .map(transactionModel -> new M_Transaccion_DTO(
                        transactionModel.getId(),
                        new M_Cuenta_DTO(
                                transactionModel.getCuenta().getId(),
                                new M_Cliente_DTO(
                                        transactionModel.getCuenta().getCliente().getId(),
                                        transactionModel.getCuenta().getCliente().getNombre()
                                ),
                                transactionModel.getCuenta().getSaldo_Global()
                        ),
                        transactionModel.getMonto_transaccion(),
                        transactionModel.getSaldo_inicial(),
                        transactionModel.getSaldo_final(),
                        transactionModel.getCosto_tansaccion(),
                        transactionModel.getTipo()
                ));
    }

}
