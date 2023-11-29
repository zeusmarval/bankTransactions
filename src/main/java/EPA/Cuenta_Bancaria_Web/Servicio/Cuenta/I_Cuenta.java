package EPA.Cuenta_Bancaria_Web.Servicio.Cuenta;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;

import java.util.List;

public interface I_Cuenta
{
    M_Cuenta_DTO crear_Cuenta(M_Cuenta_DTO p_Cuenta);

    List<M_Cuenta_DTO> findAll();
}
