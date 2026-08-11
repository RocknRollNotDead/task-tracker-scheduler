package ru.codeportfolio.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.service.kafka.RequestReportKafkaSender;

@Slf4j
@Service
public class RequestReportSendService {

    private final RequestReportKafkaSender requestReportKafkaSender;
    private final TaskService taskService;

    public RequestReportSendService(RequestReportKafkaSender requestReportKafkaSender, TaskService taskService) {
        this.requestReportKafkaSender = requestReportKafkaSender;
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 26 20 * * *", zone = "Europe/Moscow")
    public void sendRequest() {

        log.info("get reports");
        ReportRequestDto reportRequestDto = taskService.getDtoForReports();

        requestReportKafkaSender.sendRequest(reportRequestDto);
    }

}
