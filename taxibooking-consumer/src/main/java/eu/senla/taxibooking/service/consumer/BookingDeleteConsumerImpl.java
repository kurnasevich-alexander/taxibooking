package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.consumer.BookingDeleteConsumer;
import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingDeleteConsumerImpl implements BookingDeleteConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BookingDeleteConsumerImpl.class);

    @Autowired
    private BookingService bookingService;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.delete_booking_queue}")
    public void deleteBooking(Long id) {
        try {
            bookingService.deleteBooking(id);
        } catch (EntityNotFoundException ex) {
            logger.info(ex.getLocalizedMessage());
        }
    }

}
