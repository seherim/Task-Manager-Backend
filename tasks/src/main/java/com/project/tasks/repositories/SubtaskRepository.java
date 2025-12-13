package com.project.tasks.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tasks.domain.entities.Subtask;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {
}
