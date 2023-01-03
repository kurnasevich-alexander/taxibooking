package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.api.service.consumer.MessageAuditConsumer;
import eu.senla.taxibooking.service.mapper.BookingDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageAuditConsumerImpl implements MessageAuditConsumer {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDtoMapper bookingDtoMapper;

    @Override
    @RabbitListener(queues = "${taxibooking.rabbitmq.message_booking_queue}")
    public void auditMessage(Message message) {
        var body = new String(message.getBody());
        log.debug("Received message with routing key: {} || Message body: {}",
                message.getMessageProperties().getReceivedRoutingKey(), body);
    }

}
