package ru.codeportfolio.scheduler.dto;

import java.util.List;

public record UserDto(
        Long id,
        String name,
        List<TaskDto> tasks
) {
}
