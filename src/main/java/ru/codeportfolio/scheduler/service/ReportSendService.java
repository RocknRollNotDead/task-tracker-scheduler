package ru.codeportfolio.scheduler.service;

import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.service.kafka.EmailKafkaSender;
import ru.codeportfolio.scheduler.service.mapper.EmailMapperService;


@Service
public class ReportSendService {

    private final static String HEADER = "Summary по вашим задачам от Task Ledger";

    private final EmailKafkaSender emailKafkaSender;
    private final EmailMapperService emailMapperService;

    public ReportSendService(EmailKafkaSender emailKafkaSender, EmailMapperService emailMapperService) {
        this.emailKafkaSender = emailKafkaSender;
        this.emailMapperService = emailMapperService;
    }


    public void send(Long userId, String text) {

        emailKafkaSender.sendMail(
                emailMapperService.getEmailDto(userId, text, HEADER)
        );
    }


}
