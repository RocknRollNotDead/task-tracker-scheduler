package ru.codeportfolio.scheduler.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

@Slf4j
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
        kafkaTemplate.send("REPORT_REQUEST", request)
                .whenComplete((result, e) ->
                {
                    if (e != null) {
                        log.error("Error to send to kafka request to report, time: {}", Instant.now());
                    }
                });
    }
}
