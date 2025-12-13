package com.project.tasks.domain.dto;

import java.util.UUID;

public record RoleDto(
        UUID id,
        String name,
        String description) {
}
