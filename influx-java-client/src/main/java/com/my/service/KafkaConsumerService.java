package com.my.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile(value = "kafka")
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
    }

}
