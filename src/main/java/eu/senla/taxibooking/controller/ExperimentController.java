package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.poducer.MessageModel;
import eu.senla.taxibooking.poducer.RabbitMQProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentController {

    @Autowired
    private RabbitMQProducerService mqProducerService;

    @GetMapping("/send")
    public void sendMessageToRabbit(@RequestBody MessageModel messageModel) {
        mqProducerService.sendMessage(messageModel.getMessage(), messageModel.getRoutingKey());
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
