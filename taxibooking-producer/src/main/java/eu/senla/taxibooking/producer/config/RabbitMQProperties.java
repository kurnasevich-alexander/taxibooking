package eu.senla.taxibooking.producer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.rabbitmq")
@Configuration
@Data
public class RabbitMQProperties {
    private String messageExchange;
    private String addBookingRoutingKey;
    private String editBookingRoutingKey;
    private String deleteBookingRoutingKey;
}
