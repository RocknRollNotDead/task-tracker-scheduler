package ru.codeportfolio.scheduler.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.codeportfolio.scheduler.dto.ReportRequestDto;
import ru.codeportfolio.scheduler.dto.TaskDto;
import ru.codeportfolio.scheduler.dto.UserDto;
import ru.codeportfolio.scheduler.dto.UserMapDto;
import ru.codeportfolio.scheduler.model.Task;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TasksMapperService {


    private final ObjectMapper objectMapper;

    public ReportRequestDto createDtoFromTasks(List<Task> doneTasks, List<Task> notDoneTasks) {

        List<Task> tasks = new ArrayList<>();

        tasks.addAll(doneTasks);
        tasks.addAll(notDoneTasks);

        Map<Long, UserMapDto> userDtoMap = new HashMap<>();


        for (Task task : tasks) {

            Long userId = task.getOwner().getId();
            List<TaskDto> tasksList;


            if (!userDtoMap.containsKey(userId)){
                tasksList = List.of(
                                mapTask(task)
                        );
            } else {
                tasksList = new ArrayList<>(userDtoMap.get(userId).tasks());
                tasksList.add(mapTask(task));
            }

            userDtoMap.put(userId, new UserMapDto(
                    userId,
                    task.getOwner().getUsername(),
                    tasksList));
        }

        return new ReportRequestDto(
                mapUserDto(new ArrayList<>(userDtoMap.values())));

    }

    private List<UserDto> mapUserDto(List<UserMapDto> userMapDtoList) {
        return userMapDtoList.stream()
                .map(userMapDto -> new UserDto(
                        userMapDto.id(),
                        userMapDto.name(),
                        objectMapper.writeValueAsString(userMapDto.tasks())
                ))
                .toList();
    }

    private TaskDto mapTask(Task task) {
        return new TaskDto(task.getName(), task.getStatus());
    }


}
