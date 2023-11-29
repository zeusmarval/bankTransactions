package EPA.Cuenta_Bancaria_Web.Repositorio.Mongo;

import EPA.Cuenta_Bancaria_Web.Modelo.M_Cuenta;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface I_RepositorioCuentaMongo extends MongoRepository<M_CuentaMongo, String>
{
    public M_CuentaMongo findByid(String id);
}
