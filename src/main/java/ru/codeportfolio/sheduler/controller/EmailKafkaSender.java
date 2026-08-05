package ru.codeportfolio.sheduler.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class EmailKafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmailKafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMail(String string){
        kafkaTemplate.send("EMAIL_SENDING_TASKS", string);
    }
}
