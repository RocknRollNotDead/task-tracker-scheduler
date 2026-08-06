package ru.codeportfolio.scheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codeportfolio.scheduler.model.Status;
import ru.codeportfolio.scheduler.model.Task;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getTasksByTimestampAfter(Timestamp timestampAfter);

    List<Task> getTasksByStatus(Status status);
}
