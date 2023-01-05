package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.api.service.consumer.BookingEditConsumer;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import eu.senla.taxibooking.model.dto.BookingDto;
import eu.senla.taxibooking.service.mapper.BookingDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingEditConsumerImpl implements BookingEditConsumer {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDtoMapper bookingDtoMapper;

    @Override
    @RabbitListener(queues = "${taxibooking.rabbitmq.edit_booking_queue}")
    public void updateBooking(BookingDto booking) {
        try {
            bookingService.updateBooking(bookingDtoMapper.bookingDtoToBooking(booking));
        } catch (EntityNotFoundException ex) {
            log.info("Received a request to update a non-existent booking: ", ex);
        }
    }

}
