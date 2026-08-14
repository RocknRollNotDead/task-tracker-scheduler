package ru.codeportfolio.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.EmailDto;
import ru.codeportfolio.scheduler.service.kafka.EmailKafkaSender;


@Service
@RequiredArgsConstructor
public class ReportSendService {

    private final static String HEADER = "Summary по вашим задачам от Task Ledger";

    private final EmailKafkaSender emailKafkaSender;
    private final UserService userService;

    public void send(Long userId, String text) {

        emailKafkaSender.sendMail(
                new EmailDto(userService.getUserEmail(userId), HEADER, text)
        );
    }


}
