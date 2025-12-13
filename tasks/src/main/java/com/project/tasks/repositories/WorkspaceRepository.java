package com.project.tasks.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tasks.domain.entities.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
}
