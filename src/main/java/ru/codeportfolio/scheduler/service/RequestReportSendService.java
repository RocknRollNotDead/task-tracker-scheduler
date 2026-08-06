package ru.codeportfolio.scheduler.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.service.kafka.RequestReportKafkaSender;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import tools.jackson.databind.ObjectMapper;

@Service
public class RequestReportSendService {

    private final ObjectMapper objectMapper;
    private final RequestReportKafkaSender requestReportKafkaSender;
    private final TaskService taskService;

    public RequestReportSendService(ObjectMapper objectMapper, RequestReportKafkaSender requestReportKafkaSender, TaskService taskService) {
        this.objectMapper = objectMapper;
        this.requestReportKafkaSender = requestReportKafkaSender;
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendRequest() {

        ReportRequestDto reportRequestDto = taskService.getDtoForReports();

        String request = objectMapper.writeValueAsString(reportRequestDto);
        requestReportKafkaSender.sendRequest(request);
    }

}
