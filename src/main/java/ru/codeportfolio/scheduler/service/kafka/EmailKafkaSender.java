package ru.codeportfolio.scheduler.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailKafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmailKafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMail(String string) {
        kafkaTemplate.send("EMAIL_SENDING_TASKS", string);
    }
}
