package ru.codeportfolio.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.scheduler.dao.TaskRepository;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.model.Status;
import ru.codeportfolio.scheduler.service.mapper.TasksReportMapper;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TasksReportMapper tasksReportMapper;

    public ReportRequestDto getDtoForReports() {
        var tasks = taskRepository.getTasksByTimestampAfter(
                Timestamp.from(Instant.now().minus(Duration.ofDays(1)))
        );
        var notDoneTasks = taskRepository.getTasksByStatus(Status.IN_PROGRESS);
        return tasksReportMapper.createDtoFromTasks(tasks, notDoneTasks);
    }




}

