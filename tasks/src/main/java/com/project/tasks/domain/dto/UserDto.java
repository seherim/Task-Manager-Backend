package com.project.tasks.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        String displayName,
        boolean active,
        LocalDateTime created,
        LocalDateTime lastLogin) {
}
