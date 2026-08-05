package ru.codeportfolio.sheduler.dto;

import java.util.List;

public record ReportRequestDto(
        List<UserDto> usersDto
) {
}
