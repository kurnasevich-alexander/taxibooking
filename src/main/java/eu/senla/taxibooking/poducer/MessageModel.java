package eu.senla.taxibooking.poducer;

import lombok.Data;

@Data
public class MessageModel {

    private String message;
    private String routingKey;
}
