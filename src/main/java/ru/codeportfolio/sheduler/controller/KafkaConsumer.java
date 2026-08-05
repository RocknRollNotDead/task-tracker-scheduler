package ru.codeportfolio.sheduler.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.sheduler.dto.ReportDto;
import ru.codeportfolio.sheduler.service.ReportSendService;
import tools.jackson.databind.ObjectMapper;


@Controller
public class KafkaConsumer {

    private final ReportSendService reportSendService;

    public KafkaConsumer(ReportSendService reportSendService) {
        this.reportSendService = reportSendService;
    }


    @KafkaListener(topics = "SUMMARIZATION_SENDING")
    public void consume(String json){
        ObjectMapper mapper = new ObjectMapper();
        ReportDto reportDto = mapper.readValue(json, ReportDto.class);
        reportSendService.send(reportDto);
    }
}
