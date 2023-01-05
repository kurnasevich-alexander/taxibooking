package eu.senla.taxibooking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "taxibooking.rabbitmq")
@Configuration
@Data
public class RabbitMQProperties {
    private String addBookingQueue;
    private String editBookingQueue;
    private String deleteBookingQueue;
    private String messageBookingQueue;
    private String messageExchange;
    private String bookingExchange;
    private String addBookingRoutingKey;
    private String editBookingRoutingKey;
    private String deleteBookingRoutingKey;
}
