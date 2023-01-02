package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.api.service.consumer.BookingDeleteConsumer;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingDeleteConsumerImpl implements BookingDeleteConsumer {

    @Autowired
    private BookingService bookingService;

    @Override
    @RabbitListener(queues = "${rabbitmq.delete_booking_queue}")
    public void deleteBooking(Long id) {
        try {
            bookingService.deleteBooking(id);
        } catch (EntityNotFoundException ex) {
            log.info(ex.getLocalizedMessage());
        }
    }

}
