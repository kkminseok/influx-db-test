package com.my.rabbitmqserver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageSender messageSender;

    public MessageController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping
    public String send(@RequestParam String message) {
        messageSender.sendMessage(message);
        return "메시지를 RabbitMQ로 보냈습니다.";
    }
}
