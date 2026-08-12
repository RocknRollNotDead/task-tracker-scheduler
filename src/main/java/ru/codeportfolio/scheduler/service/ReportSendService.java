package ru.codeportfolio.scheduler.service;

import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.EmailDto;
import ru.codeportfolio.scheduler.service.kafka.EmailKafkaSender;


@Service
public class ReportSendService {

    private final static String HEADER = "Summary по вашим задачам от Task Ledger";

    private final EmailKafkaSender emailKafkaSender;
    private final UserService userService;

    public ReportSendService(EmailKafkaSender emailKafkaSender, UserService userService) {
        this.emailKafkaSender = emailKafkaSender;
        this.userService = userService;
    }


    public void send(Long userId, String text) {

        emailKafkaSender.sendMail(
                new EmailDto(userService.getUserEmail(userId), text, HEADER)
        );
    }


}
