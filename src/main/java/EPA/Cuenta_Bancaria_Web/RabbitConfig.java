package EPA.Cuenta_Bancaria_Web;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME_TRANSACTIONS = "transactions-queue";
    public static final String QUEUE_NAME_ACCOUNTS = "accounts-queue";
    public static final String QUEUE_NAME_ALL = "all-queue";
    public static final String QUEUE_NAME_ERRORS_TRANSACTIONS = "transactions-errors-queue";
    public static final String QUEUE_NAME_ERRORS_ACCOUNTS = "accounts-errors-queue";
    public static final String QUEUE_NAME_ERRORS = "errors-queue";
    public static final String EXCHANGE_NAME = "bank-exchange";
    public static final String ROUTING_KEY_NAME_ACCOUNTS = "accounts.routing.key";
    public static final String ROUTING_KEY_NAME_TRANSACTIONS = "transactions.routing.key";
    public static final String ROUTING_KEY_NAME_ALL = "all.routing.key";
    public static final String ROUTING_KEY_NAME_ERROR = "errors.routing.key";
    public static final String ROUTING_KEY_NAME_ERROR_TRANSACTIONS = "errors.transactions.routing.key";
    public static final String ROUTING_KEY_NAME_ERROR_ACCOUNTS = "errors.accounts.routing.key";
    //public static final String URI_NAME = "amqps://jotoppxd:OXipdv5_3evj0aMozQ0N4hvW8jzPG2xF@cow.rmq2.cloudamqp.com/jotoppxd";
    public static final String URI_NAME = "amqps://frrbuzmm:ywSFdD7KMqE6rfoe2osmgX9w_Av74CoJ@cow.rmq2.cloudamqp.com/frrbuzmm";
    //public static final String URI_NAME = "amqps://guest:guest@rabbitmq/guest";

    @Bean
    public AmqpAdmin amqpAdmin() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(URI.create(URI_NAME));
        var amqpAdmin =  new RabbitAdmin(connectionFactory);

        var exchange = new TopicExchange(EXCHANGE_NAME);
        var queue_transactions = new Queue(QUEUE_NAME_TRANSACTIONS, true, false, false);
        var queue_accounts = new Queue(QUEUE_NAME_ACCOUNTS, true, false, false);
        var queue_errors_transactions = new Queue(QUEUE_NAME_ERRORS_TRANSACTIONS, true, false, false);
        var queue_errors_accounts = new Queue(QUEUE_NAME_ERRORS_ACCOUNTS, true, false, false);
        var queue_errors = new Queue(QUEUE_NAME_ERRORS, true, false, false);
        var queue_all = new Queue(QUEUE_NAME_ALL, true, false, false);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue_transactions);
        amqpAdmin.declareQueue(queue_accounts);
        amqpAdmin.declareQueue(queue_errors_transactions);
        amqpAdmin.declareQueue(queue_errors_accounts);
        amqpAdmin.declareQueue(queue_errors);
        amqpAdmin.declareQueue(queue_all);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue_transactions).to(exchange).with(ROUTING_KEY_NAME_TRANSACTIONS));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue_accounts).to(exchange).with(ROUTING_KEY_NAME_ACCOUNTS));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue_all).to(exchange).with(ROUTING_KEY_NAME_ALL));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue_errors_transactions).to(exchange).with(ROUTING_KEY_NAME_ERROR_TRANSACTIONS));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue_errors).to(exchange).with(ROUTING_KEY_NAME_ERROR));
        amqpAdmin.declareBinding(BindingBuilder.bind(queue_errors_accounts).to(exchange).with(ROUTING_KEY_NAME_ERROR_ACCOUNTS));

        return amqpAdmin;
    }


    @Bean
    public ConnectionFactory connectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        connectionFactory.setUri(URI_NAME);
        return connectionFactory;
    }

    @Bean
    public Mono<Connection> connectionMono(@Value("spring.application.name") String name, ConnectionFactory connectionFactory)  {
        return Mono.fromCallable(() -> connectionFactory.newConnection(name)).cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions()
                .connectionMono(connectionMono)
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }


    @Bean
    public ReceiverOptions receiverOptions(Mono<Connection> connectionMono) {
        return new ReceiverOptions()
                .connectionMono(connectionMono);
    }

    @Bean
    public Receiver receiver(ReceiverOptions receiverOptions) {
        return RabbitFlux.createReceiver(receiverOptions);
    }
}
