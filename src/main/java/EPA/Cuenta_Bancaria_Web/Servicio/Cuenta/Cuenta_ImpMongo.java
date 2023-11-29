package EPA.Cuenta_Bancaria_Web.Servicio.Cuenta;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.JPA.I_Repositorio_Cuenta_JPA;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_RepositorioCuentaMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
@Qualifier("MONGO")
public class Cuenta_ImpMongo implements I_Cuenta
{
    @Autowired
    I_RepositorioCuentaMongo repositorio_Cuenta;

    @Override
    public M_Cuenta_DTO crear_Cuenta(M_Cuenta_DTO p_Cuenta_DTO)
    {
        M_CuentaMongo cuenta = new M_CuentaMongo(p_Cuenta_DTO.getId(),
                new M_ClienteMongo(p_Cuenta_DTO.getCliente().getId(),
                        p_Cuenta_DTO.getCliente().getNombre()),
                p_Cuenta_DTO.getSaldo_Global());

        //M_Cuenta cuenta_Creada = repositorio_Cuenta.create(cuenta);
        M_CuentaMongo cuenta_Creada = repositorio_Cuenta.save(cuenta);

        if(cuenta_Creada == null)
        {
            return null;
        }

        M_Cuenta_DTO dtoCreado = new M_Cuenta_DTO(cuenta_Creada.getId(),
                new M_Cliente_DTO(cuenta_Creada.getCliente().getId(),
                        cuenta_Creada.getCliente().getNombre()),
                cuenta_Creada.getSaldo_Global());

        return dtoCreado;
    }

    @Override
    public List<M_Cuenta_DTO> findAll()
    {
        List<M_CuentaMongo> l_Cuentas = repositorio_Cuenta.findAll();
        List<M_Cuenta_DTO> l_Cuentas_DTO = new ArrayList<M_Cuenta_DTO>();

        for(M_CuentaMongo c : l_Cuentas)
        {
            M_Cuenta_DTO dtoCreado = new M_Cuenta_DTO(c.getId(),
                    new M_Cliente_DTO(c.getCliente().getId(),
                            c.getCliente().getNombre()),
                    c.getSaldo_Global());
            l_Cuentas_DTO.add(dtoCreado);
        }

        return l_Cuentas_DTO;
    }
}
