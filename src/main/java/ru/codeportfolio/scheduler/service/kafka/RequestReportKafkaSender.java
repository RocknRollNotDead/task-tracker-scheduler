package ru.codeportfolio.scheduler.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import tools.jackson.databind.ObjectMapper;

@Service
public class RequestReportKafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RequestReportKafkaSender(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendRequest(ReportRequestDto reportRequestDto) {
        String request = objectMapper.writeValueAsString(reportRequestDto);
        kafkaTemplate.send("REPORT_REQUEST", request);
    }
}
