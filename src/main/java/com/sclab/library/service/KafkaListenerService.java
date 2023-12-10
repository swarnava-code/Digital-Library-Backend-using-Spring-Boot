package com.sclab.library.service;

import com.sclab.library.entity.Card;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private static final String TOPIC_NAME = "email_msg";

    @Autowired
    private EmailService emailService;

    @Autowired
    private CardService cardService;

    @Value("${email.bcc}")
    private String bcc;

    @KafkaListener(topics = TOPIC_NAME, groupId = "spring_boot_consumer")
    public void consume(ConsumerRecord<String, String> payload) {
        logger.info("Kafka Message:");
        logger.info(String.format("key: %s, \nvalue: %s", payload.key(), payload.value()));
        Card card = cardService.getById(payload.key());
        String toMail = card.getEmail();
        String mailBody = "Hi " + toMail + ", \n" + payload.value();
        emailService.sendSimpleEmail(toMail, "Library Transaction", mailBody, bcc);
        logger.info("mail has been sent to "+toMail);
    }

}