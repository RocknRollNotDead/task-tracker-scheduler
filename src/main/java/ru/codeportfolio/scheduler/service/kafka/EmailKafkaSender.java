package ru.codeportfolio.scheduler.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.EmailDto;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class EmailKafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EmailKafkaSender(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMail(EmailDto emailDto) {
        log.info("send report to {}", emailDto.email());
        String request = objectMapper.writeValueAsString(emailDto);
        kafkaTemplate.send("EMAIL_SENDING_TASKS", request);
    }
}
