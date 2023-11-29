package EPA.Cuenta_Bancaria_Web.Repositorio.Memoria;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Transaccion;
import EPA.Cuenta_Bancaria_Web.Repositorio.I_Repositorio_Transaccion;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Repositorio_Transaccion_Imp implements I_Repositorio_Transaccion
{
    List<M_Transaccion> lista_Global = new ArrayList<M_Transaccion>();

    //--------------------------------------------------------------------------------------------

    @Override
    public List<M_Transaccion> findAll()
    {
        return lista_Global;
    }

    @Override
    public M_Transaccion create(M_Transaccion p_Transaccion)
    {
        lista_Global.add(p_Transaccion);
        return p_Transaccion;
    }
}
