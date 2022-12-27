package eu.senla.taxibooking.poducer;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);
}
