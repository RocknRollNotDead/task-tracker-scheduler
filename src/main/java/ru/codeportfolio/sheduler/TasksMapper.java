package ru.codeportfolio.sheduler;

import ru.codeportfolio.sheduler.dto.ReportRequestDto;
import ru.codeportfolio.sheduler.dto.UserDto;
import ru.codeportfolio.sheduler.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksMapper {
    private TasksMapper() {
    }

    public static ReportRequestDto createDtoFromTasks(List<Task> tasks){
        List<UserDto> userDtos = new ArrayList<>();

        for (Task task : tasks) {
            if (userDtos.contains())
        }

    }

}
