package ru.codeportfolio.sсheduler.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.sсheduler.dto.ReportDto;
import ru.codeportfolio.sсheduler.service.ReportSendService;
import tools.jackson.databind.ObjectMapper;


@Controller
public class KafkaConsumer {

    private final ReportSendService reportSendService;

    public KafkaConsumer(ReportSendService reportSendService) {
        this.reportSendService = reportSendService;
    }


    @KafkaListener(topics = "SUMMARIZATION_SENDING", groupId = "id")
    public void consume(String json){
        ObjectMapper mapper = new ObjectMapper();
        ReportDto reportDto = mapper.readValue(json, ReportDto.class);
        reportSendService.send(reportDto);
    }
}
