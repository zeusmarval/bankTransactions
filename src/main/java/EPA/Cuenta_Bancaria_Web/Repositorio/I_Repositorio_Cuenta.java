package EPA.Cuenta_Bancaria_Web.Repositorio;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;

import java.util.List;

public interface I_Repositorio_Cuenta
{
    List<M_Cuenta> findAll();

    M_Cuenta create(M_Cuenta p_Cuenta);

    M_Cuenta findById(String p_sId);
}
