package EPA.Cuenta_Bancaria_Web.Repositorio.Memoria;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Repositorio.I_Repositorio_Cuenta;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Repositorio_Cuenta_Imp implements I_Repositorio_Cuenta
{

    List<M_Cuenta> lista_Global = new ArrayList<>(
            List.of(
                        new M_Cuenta("1",
                                         new M_Cliente("1", "Alexander"),
                                         BigDecimal.valueOf(1000000))
                   )
    );

    //--------------------------------------------------------------------------------------------
    @Override
    public List<M_Cuenta> findAll()
    {
        return lista_Global;
    }

    @Override
    public M_Cuenta create(M_Cuenta p_Cuenta) {
        lista_Global.add(p_Cuenta);
        return p_Cuenta;
    }

    @Override
    public M_Cuenta findById(String p_sId)
    {
        for (M_Cuenta cuenta : lista_Global) {
            if (cuenta.getId().equals(p_sId)) {
                return cuenta;
            }
        }
        return null;
    }


}
