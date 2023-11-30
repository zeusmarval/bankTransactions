package EPA.Cuenta_Bancaria_Web.Servicio.Cuenta;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_RepositorioCuentaMongo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.util.ArrayList;
import java.util.List;


@Service()
@Qualifier("MONGO")
public class Cuenta_ImpMongo implements I_Cuenta
{
    @Autowired
    I_RepositorioCuentaMongo repositorio_Cuenta;

    @Autowired
    private Gson gson;

    @Autowired
    private Sender sender;

    @Override
    public Mono<M_Cuenta_DTO> crear_Cuenta(M_Cuenta_DTO p_Cuenta_DTO)
    {
        M_CuentaMongo cuenta = new M_CuentaMongo(p_Cuenta_DTO.getId(),
                new M_ClienteMongo(p_Cuenta_DTO.getCliente().getId(),
                        p_Cuenta_DTO.getCliente().getNombre()),
                p_Cuenta_DTO.getSaldo_Global());

        sender
                .send(Mono.just(new OutboundMessage(RabbitConfig.EXCHANGE_NAME,
                        RabbitConfig.ROUTING_KEY_NAME, gson.toJson(p_Cuenta_DTO).getBytes()))).subscribe();


        return repositorio_Cuenta.save(cuenta)
                .map(cuentaModel-> {
                    return new M_Cuenta_DTO(cuentaModel.getId(),
                            new M_Cliente_DTO(cuentaModel.getCliente().getId(),
                                    cuentaModel.getCliente().getNombre()),
                            cuentaModel.getSaldo_Global());
                });
    }

    @Override
    public Flux<M_Cuenta_DTO> findAll()
    {
        return repositorio_Cuenta.findAll()
                .map(cuentaModel -> new M_Cuenta_DTO(cuentaModel.getId(),
                        new M_Cliente_DTO(cuentaModel.getCliente().getId(),
                                cuentaModel.getCliente().getNombre()),
                        cuentaModel.getSaldo_Global()));
    }
}