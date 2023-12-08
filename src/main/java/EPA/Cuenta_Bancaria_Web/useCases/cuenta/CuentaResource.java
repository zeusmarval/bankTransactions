package EPA.Cuenta_Bancaria_Web.useCases.cuenta;

import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CuentaResource {

    @Autowired
    private I_RepositorioCuentaMongo repositorioCuentaMongo;

    public Mono<M_Cuenta_DTO> crearCuenta(M_Cuenta_DTO cuentaDTO) {
        M_CuentaMongo cuenta = new M_CuentaMongo(
                cuentaDTO.getId(),
                new M_ClienteMongo(
                        cuentaDTO.getCliente().getId(),
                        cuentaDTO.getCliente().getNombre()
                ),
                cuentaDTO.getSaldo_Global()
        );

        return repositorioCuentaMongo.save(cuenta)
                .map(cuentaModel -> new M_Cuenta_DTO(
                        cuentaModel.getId(),
                        new M_Cliente_DTO(
                                cuentaModel.getCliente().getId(),
                                cuentaModel.getCliente().getNombre()
                        ),
                        cuentaModel.getSaldo_Global()
                ));
    }

}
