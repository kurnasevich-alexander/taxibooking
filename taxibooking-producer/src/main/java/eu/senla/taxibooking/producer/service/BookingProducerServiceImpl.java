package eu.senla.taxibooking.producer.service;

import eu.senla.taxibooking.model.dto.BookingDto;
import eu.senla.taxibooking.producer.api.service.BookingProducerService;
import eu.senla.taxibooking.producer.config.RabbitMQProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingProducerServiceImpl implements BookingProducerService {

    @Autowired
    private RabbitMQProperties properties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void addBooking(BookingDto bookingDto) {
        rabbitTemplate.convertAndSend(properties.getMessageExchange(),
                properties.getAddBookingRoutingKey(), bookingDto);
    }

    @Override
    public void updateBooking(BookingDto bookingDto) {
        rabbitTemplate.convertAndSend(properties.getMessageExchange(),
                properties.getEditBookingRoutingKey(), bookingDto);
    }

    @Override
    public void deleteBooking(Long id) {
        rabbitTemplate.convertAndSend(properties.getMessageExchange(), properties.getDeleteBookingRoutingKey(), id);
    }

}
