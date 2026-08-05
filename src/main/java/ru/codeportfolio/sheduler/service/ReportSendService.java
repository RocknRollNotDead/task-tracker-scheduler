package ru.codeportfolio.sheduler.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.sheduler.controller.EmailKafkaSender;
import ru.codeportfolio.sheduler.controller.ReportRequestKafkaSender;
import ru.codeportfolio.sheduler.dao.TaskRepository;
import ru.codeportfolio.sheduler.dto.ReportDto;
import ru.codeportfolio.sheduler.dto.ReportRequestDto;
import tools.jackson.databind.ObjectMapper;

@Transactional
@Service
public class ReportSendService {


    private final EmailKafkaSender emailKafkaSender;
    private final ReportRequestKafkaSender reportRequestKafkaSender;
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public ReportSendService(EmailKafkaSender emailKafkaSender, ReportRequestKafkaSender reportRequestKafkaSender, ObjectMapper objectMapper, TaskRepository taskRepository) {
        this.emailKafkaSender = emailKafkaSender;
        this.reportRequestKafkaSender = reportRequestKafkaSender;
        this.objectMapper = objectMapper;
        this.taskRepository = taskRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendRequest(){
        // ищутся все юзеры у кого есть невыполненные или сделанные сегодня задачи
        // упаковываются в ReportRequestDto
        taskRepository.getTasksByTimestampBefore();
        ReportRequestDto reportRequestDto = null;
        String request = objectMapper.writeValueAsString(reportRequestDto);
        reportRequestKafkaSender.sendRequest(request);
    }


    public void send(ReportDto reportDto) {

        // ищется email и имя по user id


        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(reportDto);
        emailKafkaSender.sendMail(request);


    }



}
