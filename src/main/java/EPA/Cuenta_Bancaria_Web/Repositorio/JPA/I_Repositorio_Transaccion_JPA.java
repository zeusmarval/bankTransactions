package EPA.Cuenta_Bancaria_Web.Repositorio.JPA;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface I_Repositorio_Transaccion_JPA extends JpaRepository<M_Transaccion, String>
{
    //List<M_Transaccion> findAll();

    //M_Transaccion create(M_Transaccion p_Transaccion);
}
