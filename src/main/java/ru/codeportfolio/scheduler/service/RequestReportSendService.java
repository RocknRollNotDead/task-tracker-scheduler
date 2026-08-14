package ru.codeportfolio.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.service.kafka.RequestReportKafkaSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestReportSendService {

    private final RequestReportKafkaSender requestReportKafkaSender;
    private final TaskService taskService;


    @Scheduled(cron = "${schedule.cron}", zone = "${schedule.zone}")
    public void sendRequest() {

        log.info("get reports");
        ReportRequestDto reportRequestDto = taskService.getDtoForReports();

        requestReportKafkaSender.sendRequest(reportRequestDto);
    }

}
