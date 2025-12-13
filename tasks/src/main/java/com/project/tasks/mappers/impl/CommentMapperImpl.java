package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.CommentDto;
import com.project.tasks.domain.entities.Comment;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.domain.entities.User;
import com.project.tasks.mappers.CommentMapper;

@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment fromDto(CommentDto dto) {
        if (dto == null)
            return null;
        Task task = dto.taskId() == null ? null : new Task(dto.taskId(), null, null, null, null, null);
        User author = dto.authorId() == null ? null
                : new User(dto.authorId(), null, null, null, null, false, null, null);
        return new Comment(dto.id(), task, author, dto.message(), dto.created());
    }

    @Override
    public CommentDto toDto(Comment comment) {
        if (comment == null)
            return null;
        return new CommentDto(comment.getId(),
                comment.getTask() == null ? null : comment.getTask().getId(),
                comment.getAuthor() == null ? null : comment.getAuthor().getId(),
                comment.getMessage(), comment.getCreated());
    }
}
