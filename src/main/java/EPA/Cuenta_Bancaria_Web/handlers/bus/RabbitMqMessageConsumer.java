package EPA.Cuenta_Bancaria_Web.handlers.bus;


import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

import java.util.Arrays;

@Component
@Order(1)
public class RabbitMqMessageConsumer implements CommandLineRunner {

    @Autowired
    private Receiver receiver;

    @Autowired
    private Gson gson;

    @Override
    public void run(String... args) throws Exception {
        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_TRANSACTIONS)
                .map(message -> {
                    M_Transaccion_DTO transaccion = gson
                            .fromJson(new String(message.getBody()),
                                    M_Transaccion_DTO.class);

                    System.out.println("Nueva Transaccion:  " + transaccion);
                    return message;
                }).subscribe();

        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_ACCOUNTS)
                .map(message -> {
                   M_Cuenta_DTO cuenta = gson
                           .fromJson(new String(message.getBody()),
                                   M_Cuenta_DTO.class);

                    System.out.println("La cuenta creada fue:  " + cuenta);
                    return cuenta;
                }).subscribe();

    }

}
