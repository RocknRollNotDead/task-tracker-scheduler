package ru.codeportfolio.scheduler.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.scheduler.service.ReportSendService;


@Controller
public class KafkaConsumer {

    private final ReportSendService reportSendService;

    public KafkaConsumer(ReportSendService reportSendService) {
        this.reportSendService = reportSendService;
    }


    @KafkaListener(topics = "SUMMARIZATION_SENDING")
    public void consume(String text, @Header(KafkaHeaders.RECEIVED_KEY) Long userId) {
        reportSendService.send(userId, text);
    }
}
