package com.project.tasks.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tasks.domain.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
