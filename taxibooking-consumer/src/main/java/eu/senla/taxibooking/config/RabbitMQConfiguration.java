package eu.senla.taxibooking.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${spring.rabbitmq.add_booking_queue}")
    private String addBookingQueue;

    @Value("${spring.rabbitmq.edit_booking_queue}")
    private String editBookingQueue;

    @Value("${spring.rabbitmq.delete_booking_queue}")
    private String deleteBookingQueue;

    @Value("${spring.rabbitmq.message_booking_queue}")
    private String messageBookingQueue;

    @Value("${spring.rabbitmq.message_exchange}")
    private String messageExchange;

    @Value("${spring.rabbitmq.booking_exchange}")
    private String bookingExchange;

    @Value("${spring.rabbitmq.add_booking_routing_key}")
    private String addBookingRK;

    @Value("${spring.rabbitmq.edit_booking_routing_key}")
    private String editBookingRK;

    @Value("${spring.rabbitmq.delete_booking_routing_key}")
    private String deleteBookingRK;

    @Bean
    Queue addQueue() {
        return new Queue(addBookingQueue, false);
    }

    @Bean
    Queue editQueue() {
        return new Queue(editBookingQueue, false);
    }

    @Bean
    Queue deleteQueue() {
        return new Queue(deleteBookingQueue, false);
    }

    @Bean
    Queue messageQueue() {
        return new Queue(messageBookingQueue, false);
    }

    @Bean
    FanoutExchange messageExchange() {
        return new FanoutExchange(messageExchange);
    }

    @Bean
    DirectExchange bookingExchange() {
        return new DirectExchange(bookingExchange);
    }

    @Bean
    Binding bookingExchangeBinding(DirectExchange bookingExchange, FanoutExchange messageExchange) {
        return BindingBuilder.bind(bookingExchange).to(messageExchange);
    }

    @Bean
    Binding messageQueueBinding(Queue messageQueue, FanoutExchange messageExchange) {
        return BindingBuilder.bind(messageQueue).to(messageExchange);
    }

    @Bean
    Binding addQueueBinding(Queue addQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(addQueue).to(bookingExchange).with(addBookingRK);
    }

    @Bean
    Binding editQueueBinding(Queue editQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(editQueue).to(bookingExchange).with(editBookingRK);
    }

    @Bean
    Binding deleteQueueBinding(Queue deleteQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(deleteQueue).to(bookingExchange).with(deleteBookingRK);
    }

}
