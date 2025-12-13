package com.project.tasks.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectDto(
        UUID id,
        String name,
        String description,
        UUID workspaceId,
        UUID ownerId,
        LocalDate startDate,
        LocalDate endDate) {
}
