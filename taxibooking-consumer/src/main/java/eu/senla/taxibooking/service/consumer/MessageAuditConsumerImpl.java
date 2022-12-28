package eu.senla.taxibooking.service.consumer;

import eu.senla.taxibooking.api.service.consumer.MessageAuditConsumer;
import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.service.mapper.BookingDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageAuditConsumerImpl implements MessageAuditConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageAuditConsumerImpl.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDtoMapper bookingDtoMapper;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.message_booking_queue}")
    public void auditMessage(Message message) {
        logger.info("Received message with routing key " + message.getMessageProperties().getReceivedRoutingKey());
    }

}
