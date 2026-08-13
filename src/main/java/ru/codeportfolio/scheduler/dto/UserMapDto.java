package ru.codeportfolio.scheduler.dto;

import java.util.List;

public record UserMapDto(
        Long id,
        String name,
        List<TaskDto> tasks
) {
}
