package ru.codeportfolio.sсheduler.dto;

import ru.codeportfolio.sсheduler.model.Status;

public record TaskDto(
        String name,
        Status status
) {
}
