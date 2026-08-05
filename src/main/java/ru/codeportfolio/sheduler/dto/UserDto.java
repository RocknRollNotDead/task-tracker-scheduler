package ru.codeportfolio.sheduler.dto;

import java.util.List;

public record UserDto(
        Long id,
        String name,
        List<TaskDto> tasks
) {
}
