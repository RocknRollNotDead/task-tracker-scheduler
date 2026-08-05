package ru.codeportfolio.sheduler;

import org.springframework.stereotype.Component;
import ru.codeportfolio.sheduler.dao.UserRepository;
import ru.codeportfolio.sheduler.dto.ReportRequestDto;
import ru.codeportfolio.sheduler.dto.TaskDto;
import ru.codeportfolio.sheduler.dto.UserDto;
import ru.codeportfolio.sheduler.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TasksMapper {
    private final UserRepository userRepository;

    public TasksMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ReportRequestDto createDtoFromTasks(List<Task> tasks, List<Task> notDoneTasks){
        List<UserDto> userDtos = new ArrayList<>();

        Map<Long, List<TaskDto>> usersTask = new HashMap<>();

        putTasksInMap(tasks, usersTask);

        putTasksInMap(notDoneTasks, usersTask);


        usersTask.forEach( (id, value) -> {
            userDtos.add(new UserDto(
                    id,
                    userRepository.findById(id).orElseThrow().getUsername(),
                    value
            ));
        });

        return new ReportRequestDto(userDtos);

    }

    private void putTasksInMap(List<Task> tasks, Map<Long, List<TaskDto>> usersTask) {
        for (Task task : tasks) {

            Long userId = task.getOwner().getId();

            usersTask.computeIfAbsent(userId, k -> new ArrayList<>()).add(mapTask(task));

        }
    }

    public TaskDto mapTask(Task task) {
        return new TaskDto(task.getName(), task.getStatus());
    }


}
