package ru.codeportfolio.sсheduler.dto;

import java.util.List;

public record ReportRequestDto(
        List<UserDto> usersDto
) {
}
