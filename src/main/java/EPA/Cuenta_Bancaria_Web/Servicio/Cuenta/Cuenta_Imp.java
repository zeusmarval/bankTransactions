package EPA.Cuenta_Bancaria_Web.Servicio.Cuenta;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Repositorio.JPA.I_Repositorio_Cuenta_JPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
@Qualifier("JPA")
public class Cuenta_Imp implements I_Cuenta
{
    @Autowired
    I_Repositorio_Cuenta_JPA repositorio_Cuenta;

    @Override
    public M_Cuenta_DTO crear_Cuenta(M_Cuenta_DTO p_Cuenta_DTO)
    {
        M_Cuenta cuenta = new M_Cuenta(p_Cuenta_DTO.getId(),
                                       new M_Cliente(p_Cuenta_DTO.getCliente().getId(),
                                                     p_Cuenta_DTO.getCliente().getNombre()),
                                       p_Cuenta_DTO.getSaldo_Global());

        //M_Cuenta cuenta_Creada = repositorio_Cuenta.create(cuenta);
        M_Cuenta cuenta_Creada = repositorio_Cuenta.save(cuenta);

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
        List<M_Cuenta> l_Cuentas = repositorio_Cuenta.findAll();
        List<M_Cuenta_DTO> l_Cuentas_DTO = new ArrayList<M_Cuenta_DTO>();

        for(M_Cuenta c : l_Cuentas)
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
