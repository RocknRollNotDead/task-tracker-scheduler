package ru.codeportfolio.sсheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codeportfolio.sсheduler.model.Status;
import ru.codeportfolio.sсheduler.model.Task;


import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getTasksByTimestampAfter(Timestamp timestampAfter);

    List<Task> getTasksByStatus(Status status);
}
