package com.project.tasks.domain.dto;

import java.util.UUID;

import com.project.tasks.domain.entities.TaskPriority;
import com.project.tasks.domain.entities.TaskStatus;

public record SubtaskDto(
        UUID id,
        UUID parentTaskId,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority) {
}
