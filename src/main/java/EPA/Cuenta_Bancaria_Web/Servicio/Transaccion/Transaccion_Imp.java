package EPA.Cuenta_Bancaria_Web.Servicio.Transaccion;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Transaccion;
import EPA.Cuenta_Bancaria_Web.Repositorio.I_Repositorio_Cuenta;
import EPA.Cuenta_Bancaria_Web.Repositorio.I_Repositorio_Transaccion;
import EPA.Cuenta_Bancaria_Web.Repositorio.JPA.I_Repositorio_Cuenta_JPA;
import EPA.Cuenta_Bancaria_Web.Repositorio.JPA.I_Repositorio_Transaccion_JPA;
import EPA.Cuenta_Bancaria_Web.Servicio.Cuenta.I_Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("JPA")
public class Transaccion_Imp implements I_Transaccion
{
    @Autowired
    I_Repositorio_Transaccion_JPA transaccion_repositorio;

    @Autowired
    I_Repositorio_Cuenta_JPA cuenta_repositorio;

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

        M_Cuenta cuentaCliente = cuenta_repositorio.findByid(id_Cuenta);

        if(cuentaCliente == null)
        {
            return null;
        }

        BigDecimal bdSaldoActual = cuentaCliente.getSaldo_Global();
        BigDecimal bdSaldoNuevo  = cuentaCliente.getSaldo_Global().add(monto.subtract(costo));

        M_Transaccion transaccion = new M_Transaccion("Autogenerado",
                cuentaCliente,
                monto,
                bdSaldoActual,
                bdSaldoNuevo,
                costo,
                tipo.toString()
        );

        cuentaCliente.setSaldo_Global(bdSaldoNuevo);

        //M_Transaccion transaccion_Creada = transaccion_repositorio.create(transaccion);
        M_Transaccion transaccion_Creada = transaccion_repositorio.save(transaccion);

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

    @Override
    public List<M_Transaccion_DTO> findAll()
    {
        List<M_Transaccion> l_transacciones = transaccion_repositorio.findAll();
        List<M_Transaccion_DTO> l_transacciones_DTO = new ArrayList<M_Transaccion_DTO>();

        for(M_Transaccion c : l_transacciones)
        {
            M_Transaccion_DTO dtoCreado = new M_Transaccion_DTO(c.getId(),
                    new M_Cuenta_DTO(c.getCuenta().getId(),
                            new M_Cliente_DTO(c.getCuenta().getCliente().getId(),
                                    c.getCuenta().getCliente().getNombre()
                            ),
                            c.getCuenta().getSaldo_Global()
                    ), c.getMonto_transaccion(),
                    c.getSaldo_inicial(),
                    c.getSaldo_final(),
                    c.getCosto_tansaccion(),
                    c.getTipo()
            );

            l_transacciones_DTO.add(dtoCreado);
        }

        return l_transacciones_DTO;
    }
}
