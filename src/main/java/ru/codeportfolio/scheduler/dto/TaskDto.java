package ru.codeportfolio.scheduler.dto;

import ru.codeportfolio.scheduler.model.Status;

public record TaskDto(
        String name,
        Status status
) {
}
