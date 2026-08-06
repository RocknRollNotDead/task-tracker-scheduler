package ru.codeportfolio.scheduler.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RequestReportKafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public RequestReportKafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequest(String string) {
        kafkaTemplate.send("REPORT_REQUEST", string);
    }
}
