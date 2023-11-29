package EPA.Cuenta_Bancaria_Web.Repositorio;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Transaccion;

import java.util.List;

public interface I_Repositorio_Transaccion
{
    List<M_Transaccion> findAll();

    M_Transaccion create(M_Transaccion p_Transaccion);
}
