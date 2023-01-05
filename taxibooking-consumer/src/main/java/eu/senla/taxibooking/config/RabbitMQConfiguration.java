package eu.senla.taxibooking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Autowired
    private RabbitMQProperties properties;

    @Bean
    public RabbitTemplate rabbitTemplate(final ObjectMapper mapper, final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(mapper));
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter producerJackson2MessageConverter(final ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    Queue addQueue() {
        return new Queue(properties.getAddBookingQueue());
    }

    @Bean
    Queue editQueue() {
        return new Queue(properties.getEditBookingQueue());
    }

    @Bean
    Queue deleteQueue() {
        return new Queue(properties.getDeleteBookingQueue());
    }

    @Bean
    Queue messageQueue() {
        return new Queue(properties.getMessageBookingQueue());
    }

    @Bean
    FanoutExchange messageExchange() {
        return new FanoutExchange(properties.getMessageExchange());
    }

    @Bean
    DirectExchange bookingExchange() {
        return new DirectExchange(properties.getBookingExchange());
    }

    @Bean
    Binding bookingExchangeBinding(DirectExchange bookingExchange, FanoutExchange messageExchange) {
        return BindingBuilder
                .bind(bookingExchange)
                .to(messageExchange);
    }

    @Bean
    Binding messageQueueBinding(Queue messageQueue, FanoutExchange messageExchange) {
        return BindingBuilder
                .bind(messageQueue)
                .to(messageExchange);
    }

    @Bean
    Binding addQueueBinding(Queue addQueue, DirectExchange bookingExchange) {
        return BindingBuilder
                .bind(addQueue)
                .to(bookingExchange)
                .with(properties.getAddBookingRoutingKey());
    }

    @Bean
    Binding editQueueBinding(Queue editQueue, DirectExchange bookingExchange) {
        return BindingBuilder
                .bind(editQueue)
                .to(bookingExchange)
                .with(properties.getEditBookingRoutingKey());
    }

    @Bean
    Binding deleteQueueBinding(Queue deleteQueue, DirectExchange bookingExchange) {
        return BindingBuilder
                .bind(deleteQueue)
                .to(bookingExchange)
                .with(properties.getDeleteBookingRoutingKey());
    }

}
