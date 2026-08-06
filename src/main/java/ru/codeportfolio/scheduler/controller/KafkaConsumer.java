package ru.codeportfolio.scheduler.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.scheduler.dto.ReportDto;
import ru.codeportfolio.scheduler.service.ReportSendService;
import tools.jackson.databind.ObjectMapper;


@Controller
public class KafkaConsumer {

    private final ReportSendService reportSendService;

    public KafkaConsumer(ReportSendService reportSendService) {
        this.reportSendService = reportSendService;
    }


    @KafkaListener(topics = "SUMMARIZATION_SENDING")
    public void consume(String json) {
        ObjectMapper mapper = new ObjectMapper();
        ReportDto reportDto = mapper.readValue(json, ReportDto.class);
        reportSendService.send(reportDto);
    }
}
