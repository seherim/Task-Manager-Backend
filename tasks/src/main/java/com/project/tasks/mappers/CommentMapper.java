package com.project.tasks.mappers;

import com.project.tasks.domain.dto.CommentDto;
import com.project.tasks.domain.entities.Comment;

public interface CommentMapper {
    Comment fromDto(CommentDto dto);

    CommentDto toDto(Comment comment);
}
