package com.project.tasks.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tasks.domain.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
