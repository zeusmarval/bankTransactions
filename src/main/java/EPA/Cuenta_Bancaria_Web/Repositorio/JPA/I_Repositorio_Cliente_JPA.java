package EPA.Cuenta_Bancaria_Web.Repositorio.JPA;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cliente;
import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_Repositorio_Cliente_JPA extends JpaRepository<M_Cliente, String>
{
}
