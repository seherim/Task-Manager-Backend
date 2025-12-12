package com.project.tasks.domain.dto;

import java.util.UUID;

import com.project.tasks.domain.entities.TaskPriority;
import com.project.tasks.domain.entities.TaskStatus;

public record TaskDto(
    UUID id,
    String title,
    String description,
    String dateDue,
    TaskPriority priority,
    TaskStatus status         
) {

}
