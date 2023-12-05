package com.sclab.library.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private static final String TOPIC_NAME = "email_msg";

    @KafkaListener(topics = TOPIC_NAME, groupId = "spring_boot_consumer")
    public void consume(ConsumerRecord<String, String> payload) {
        System.out.println("Kafka Message:");
        System.out.println(String.format("key: %s, \nvalue: %s", payload.key(), payload.value()));
    }

}