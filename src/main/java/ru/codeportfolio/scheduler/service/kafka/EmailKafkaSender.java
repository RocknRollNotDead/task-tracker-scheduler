package ru.codeportfolio.scheduler.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.EmailDto;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailKafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMail(EmailDto emailDto) {

        String request = objectMapper.writeValueAsString(emailDto);
        kafkaTemplate.send("EMAIL_SENDING_TASKS", request)
                .whenComplete((result, e) ->
                {
                    if (e != null) {
                        log.error("Error to send to kafka mail report for address {}", emailDto.email());
                    }
                });
    }
}
