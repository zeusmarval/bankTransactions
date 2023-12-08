package EPA.Cuenta_Bancaria_Web.handlers.bus;

import EPA.Cuenta_Bancaria_Web.RabbitConfig;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.drivenAdapters.repositorios.I_Repositorio_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.models.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.models.Mongo.M_TransaccionMongo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

@Component
@Order(2)
public class RabbitMqErrorConsumer implements CommandLineRunner {

    @Autowired
    private Receiver receiver;
    @Autowired
    private Gson gson;
    @Autowired
    private I_RepositorioCuentaMongo iRepositorioCuentaMongo;
    @Autowired
    private I_Repositorio_TransaccionMongo iRepositorioTransaccionMongo;

    @Override
    public void run(String... args) throws Exception {

        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_ERRORS_TRANSACTIONS)
                .map(message -> {
                    String errorMessage = new String(message.getBody());
                    System.err.println("Error en crear transaccion: " + errorMessage);
                    M_TransaccionMongo transaction = gson
                            .fromJson(new String(message.getBody()),
                                    M_TransaccionMongo.class);

                    M_CuentaMongo cuenta = transaction.getCuenta();
                    cuenta.setSaldo_Global(transaction.getSaldo_inicial());
                    iRepositorioCuentaMongo.save(cuenta).subscribe();
                    Disposable subscribe = iRepositorioTransaccionMongo.deleteById(transaction.getId()).subscribe();

                    System.out.println(subscribe);

                    return errorMessage;

                }).subscribe();

        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_ERRORS_ACCOUNTS)
                .map(message -> {
                    String errorMessage = new String(message.getBody());
                    System.err.println("Error en crear cuenta: " + errorMessage);

                    M_CuentaMongo cuenta = gson
                            .fromJson(new String(message.getBody()),
                                    M_CuentaMongo.class);

                    String idCuenta = cuenta.getId();

                    iRepositorioCuentaMongo.deleteById(idCuenta).subscribe();

                    return errorMessage;

                }).subscribe();

        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME_ERRORS)
                .map(message -> {
                    String errorMessage = new String(message.getBody());
                    System.err.println("Error en la cola de errores: " + errorMessage);

                    return errorMessage;

                }).subscribe();
    }
}
