package com.sclab.library.service;

import com.sclab.library.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC_NAME = "email_msg";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendBookIssuedNotification(String cardId,
                                           String bookName, String authors, String dueDate) {
        authors = authors.equals("")?"NA,":authors;
        String message = String.format("Book Issued on %s - Book Name: %s, Authors: %s Due Date: %s",
                TimeUtil.currentTimeStamp(), bookName, authors, dueDate);
        kafkaTemplate.send(TOPIC_NAME, cardId, message);
    }

    public void sendBookReturnedNotification(String cardId,
                                             String bookName, String authors, String issueDate, String dueDate,
                                             double fineAmount) {
        authors = authors.equals("")?"NA,":authors;
        String message = String.format("Book Returned on %s - Book Name: %s, Authors: %s Issue Date: %s, Due Date: %s, Fine Amount: ₹%s/-",
                TimeUtil.currentTimeStamp(), bookName, authors, issueDate, dueDate, fineAmount);
        kafkaTemplate.send(TOPIC_NAME, cardId, message);
    }

}