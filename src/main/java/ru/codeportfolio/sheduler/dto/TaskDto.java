package ru.codeportfolio.sheduler.dto;

import ru.codeportfolio.sheduler.model.Status;

public record TaskDto(
        String name,
        Status status
) {
}
