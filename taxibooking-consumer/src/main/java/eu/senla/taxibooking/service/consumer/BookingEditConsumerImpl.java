package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.consumer.BookingEditConsumer;
import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import eu.senla.taxibooking.service.mapper.BookingDtoMapper;
import eu.senla.taxibooking_model.dto.BookingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingEditConsumerImpl implements BookingEditConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BookingEditConsumerImpl.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDtoMapper bookingDtoMapper;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.edit_booking_queue}")
    public void updateBooking(BookingDto booking) {
        try {
            bookingService.updateBooking(bookingDtoMapper.bookingDtoToBooking(booking));
        } catch (EntityNotFoundException ex) {
            logger.info(ex.getLocalizedMessage());
        }
    }

}
