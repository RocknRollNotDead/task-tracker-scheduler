package ru.codeportfolio.scheduler.dto;

import java.util.List;

public record ReportRequestDto(
        List<UserDto> usersDto
) {
}
