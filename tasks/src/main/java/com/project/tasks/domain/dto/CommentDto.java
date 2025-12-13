package com.project.tasks.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDto(
        UUID id,
        UUID taskId,
        UUID authorId,
        String message,
        LocalDateTime created) {
}
