package eu.senla.taxibooking;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class TaxibookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxibookingApplication.class, args);
    }

}
