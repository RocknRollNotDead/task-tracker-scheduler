package ru.codeportfolio.sсheduler.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ReportRequestKafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ReportRequestKafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequest(String string){
        kafkaTemplate.send("REPORT_REQUEST", string);
    }
}
