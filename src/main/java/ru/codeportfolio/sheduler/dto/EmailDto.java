package ru.codeportfolio.sheduler.dto;

public record EmailDto (
        String email,
        String header,
        String text
) {
}
