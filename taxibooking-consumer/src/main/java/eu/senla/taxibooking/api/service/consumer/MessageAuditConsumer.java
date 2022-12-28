package eu.senla.taxibooking.api.service.consumer;

import org.springframework.amqp.core.Message;

public interface MessageAuditConsumer {
    void auditMessage(Message message);
}
