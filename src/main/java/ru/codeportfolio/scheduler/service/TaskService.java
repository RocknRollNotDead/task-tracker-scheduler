package ru.codeportfolio.scheduler.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.scheduler.dao.TaskRepository;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.model.Status;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TasksMapper tasksMapper;

    public TaskService(TaskRepository taskRepository, TasksMapper tasksMapper) {
        this.taskRepository = taskRepository;
        this.tasksMapper = tasksMapper;
    }

    public ReportRequestDto getDtoForReports() {
        var tasks = taskRepository.getTasksByTimestampAfter(Timestamp.from(Instant.now().minus(Duration.ofDays(1))));
        var notDoneTasks = taskRepository.getTasksByStatus(Status.IN_PROGRESS);
        return tasksMapper.createDtoFromTasks(tasks, notDoneTasks);
    }
}
