package ru.codeportfolio.scheduler.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.scheduler.service.ReportSendService;


@Controller
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ReportSendService reportSendService;


    @KafkaListener(topics = "SUMMARIZATION_SENDING")
    public void consume(String text, @Header(KafkaHeaders.RECEIVED_KEY) Long userId) {
        reportSendService.send(userId, text);
    }
}
