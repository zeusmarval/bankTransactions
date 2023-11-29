package EPA.Cuenta_Bancaria_Web.Repositorio.JPA;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface I_Repositorio_Cuenta_JPA extends JpaRepository<M_Cuenta, String>
{
    //List<M_Cuenta> findAll();

    //M_Cuenta create(M_Cuenta p_Cuenta);

    public M_Cuenta findByid(String id);
}
