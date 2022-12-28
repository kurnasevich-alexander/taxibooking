package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.consumer.BookingAddConsumer;
import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.service.mapper.BookingDtoMapper;
import eu.senla.taxibooking_model.dto.BookingDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingAddConsumerImpl implements BookingAddConsumer {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDtoMapper bookingDtoMapper;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.add_booking_queue}")
    public void addBooking(BookingDto booking) {
        bookingService.addBooking(bookingDtoMapper.bookingDtoToBooking(booking));
    }

}
