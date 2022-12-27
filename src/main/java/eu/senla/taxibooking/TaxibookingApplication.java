package eu.senla.taxibooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaxibookingApplication {

    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    public static void main(String[] args) {
        SpringApplication.run(TaxibookingApplication.class, args);
    }

}
