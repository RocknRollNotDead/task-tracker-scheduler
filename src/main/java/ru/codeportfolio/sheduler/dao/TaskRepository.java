package ru.codeportfolio.sheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codeportfolio.sheduler.model.Task;


import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByOwner_Id(Long ownerId);

    Task getTaskById(Long id);

    List<Task> getTasksByTimestampBefore(Timestamp timestampBefore);
}
