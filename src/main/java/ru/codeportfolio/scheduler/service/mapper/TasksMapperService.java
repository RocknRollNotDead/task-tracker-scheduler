package ru.codeportfolio.scheduler.service.mapper;

import org.springframework.stereotype.Component;
import ru.codeportfolio.scheduler.dao.UserRepository;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.dto.TaskDto;
import ru.codeportfolio.scheduler.dto.UserDto;
import ru.codeportfolio.scheduler.model.Task;
import ru.codeportfolio.scheduler.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TasksMapperService {


    private final UserService userService;

    public TasksMapperService(UserService userService) {
        this.userService = userService;
    }

    public ReportRequestDto createDtoFromTasks(List<Task> tasks, List<Task> notDoneTasks) {
        List<UserDto> userDtos = new ArrayList<>();

        Map<Long, List<TaskDto>> usersTask = new HashMap<>();

        putTasksInMap(tasks, usersTask);

        putTasksInMap(notDoneTasks, usersTask);


        usersTask.forEach((id, value) -> {
            userDtos.add(new UserDto(
                    id,
                    userService.getUsername(id),
                    value.toString()
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

    private TaskDto mapTask(Task task) {
        return new TaskDto(task.getName(), task.getStatus());
    }


}
