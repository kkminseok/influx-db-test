package com.my.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "rabbitmq")
@Component
public class RabbitMqConsumer {

    @RabbitListener(queues = "sample-queue")
    public void receiveMessage(String message) {
        System.out.println("🐰 Received Message: " + message);
    }

}
