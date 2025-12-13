package com.project.tasks.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tasks.domain.dto.CommentDto;
import com.project.tasks.domain.entities.Comment;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.domain.entities.User;
import com.project.tasks.mappers.CommentMapper;
import com.project.tasks.repositories.CommentRepository;
import com.project.tasks.repositories.TaskRepository;
import com.project.tasks.repositories.UserRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CommentController(CommentRepository commentRepository, CommentMapper commentMapper,
            TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(commentMapper::toDto).toList();
    }

    @PostMapping
    public CommentDto createComment(@RequestBody CommentDto commentDto) {
        if (commentDto.message() == null || commentDto.message().isBlank()) {
            throw new IllegalArgumentException("Comment must have a message");
        }
        Comment c = commentMapper.fromDto(commentDto);
        if (commentDto.taskId() != null) {
            Task task = taskRepository.findById(commentDto.taskId())
                    .orElseThrow(() -> new IllegalArgumentException("Task not found"));
            c.setTask(task);
        }
        if (commentDto.authorId() != null) {
            User author = userRepository.findById(commentDto.authorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author not found"));
            c.setAuthor(author);
        }
        Comment saved = commentRepository.save(c);
        return commentMapper.toDto(saved);
    }

    @GetMapping(path = "/{comment_id}")
    public CommentDto getCommentById(@PathVariable("comment_id") UUID commentId) {
        return commentRepository.findById(commentId).map(commentMapper::toDto).orElse(null);
    }

    @PutMapping(path = "/{comment_id}")
    public CommentDto updateComment(@PathVariable("comment_id") UUID commentId, @RequestBody CommentDto commentDto) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        existing.setMessage(commentDto.message());
        Comment updated = commentRepository.save(existing);
        return commentMapper.toDto(updated);
    }

    @DeleteMapping(path = "/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") UUID commentId) {
        commentRepository.deleteById(commentId);
    }
}
