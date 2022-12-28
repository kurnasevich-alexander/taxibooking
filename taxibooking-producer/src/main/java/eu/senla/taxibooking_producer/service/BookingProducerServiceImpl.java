package eu.senla.taxibooking_producer.service;

import eu.senla.taxibooking_model.dto.BookingDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookingProducerServiceImpl implements BookingProducerService {

    @Value("${spring.rabbitmq.message_exchange}")
    private String messageExchange;

    @Value("${spring.rabbitmq.add_booking_routing_key}")
    private String addBookingRK;

    @Value("${spring.rabbitmq.edit_booking_routing_key}")
    private String editBookingRK;

    @Value("${spring.rabbitmq.delete_booking_routing_key}")
    private String deleteBookingRK;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void addBooking(BookingDto bookingDto) {
        rabbitTemplate.setExchange(messageExchange);
        rabbitTemplate.convertAndSend(addBookingRK, bookingDto);
    }

    @Override
    public void updateBooking(BookingDto bookingDto) {
        rabbitTemplate.setExchange(messageExchange);
        rabbitTemplate.convertAndSend(editBookingRK, bookingDto);
    }

    @Override
    public void deleteBooking(Long id) {
        rabbitTemplate.setExchange(messageExchange);
        rabbitTemplate.convertAndSend(deleteBookingRK, id);
    }

}
