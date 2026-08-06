package ru.codeportfolio.scheduler.service;

import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.service.kafka.EmailKafkaSender;
import ru.codeportfolio.scheduler.dto.ReportDto;
import tools.jackson.databind.ObjectMapper;


@Service
public class ReportSendService {


    private final EmailKafkaSender emailKafkaSender;

    public ReportSendService(EmailKafkaSender emailKafkaSender) {
        this.emailKafkaSender = emailKafkaSender;
    }


    public void send(ReportDto reportDto) {

        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(reportDto);
        emailKafkaSender.sendMail(request);


    }


}
