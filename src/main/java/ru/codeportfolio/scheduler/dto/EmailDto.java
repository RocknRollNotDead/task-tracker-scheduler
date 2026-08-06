package ru.codeportfolio.scheduler.dto;

public record EmailDto (
        String email,
        String header,
        String text
) {
}
