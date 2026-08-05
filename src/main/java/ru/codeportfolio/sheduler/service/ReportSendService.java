package ru.codeportfolio.sheduler.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.sheduler.TasksMapper;
import ru.codeportfolio.sheduler.controller.EmailKafkaSender;
import ru.codeportfolio.sheduler.controller.ReportRequestKafkaSender;
import ru.codeportfolio.sheduler.dao.TaskRepository;
import ru.codeportfolio.sheduler.dto.ReportDto;
import ru.codeportfolio.sheduler.dto.ReportRequestDto;
import ru.codeportfolio.sheduler.model.Status;
import tools.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;


@Service
public class ReportSendService {


    private final EmailKafkaSender emailKafkaSender;
    private final ReportRequestKafkaSender reportRequestKafkaSender;
    private final ObjectMapper objectMapper;
    private final TaskService taskService;


    public ReportSendService(EmailKafkaSender emailKafkaSender, ReportRequestKafkaSender reportRequestKafkaSender, ObjectMapper objectMapper, TaskService taskService) {
        this.emailKafkaSender = emailKafkaSender;
        this.reportRequestKafkaSender = reportRequestKafkaSender;
        this.objectMapper = objectMapper;
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendRequest(){
        // ищутся все юзеры у кого есть невыполненные или сделанные сегодня задачи
        // упаковываются в ReportRequestDto


        ReportRequestDto reportRequestDto = taskService.getDtoForReports();

        String request = objectMapper.writeValueAsString(reportRequestDto);
        reportRequestKafkaSender.sendRequest(request);
    }




    public void send(ReportDto reportDto) {

        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(reportDto);
        emailKafkaSender.sendMail(request);


    }



}
