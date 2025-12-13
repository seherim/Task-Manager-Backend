package com.project.tasks.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkspaceDto(
        UUID id,
        String name,
        UUID ownerId,
        LocalDateTime created) {
}
