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

import com.project.tasks.domain.dto.ProjectDto;
import com.project.tasks.domain.entities.Project;
import com.project.tasks.domain.entities.User;
import com.project.tasks.domain.entities.Workspace;
import com.project.tasks.mappers.ProjectMapper;
import com.project.tasks.repositories.ProjectRepository;
import com.project.tasks.repositories.UserRepository;
import com.project.tasks.repositories.WorkspaceRepository;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public ProjectController(ProjectRepository projectRepository, ProjectMapper projectMapper,
            WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::toDto).toList();
    }

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        if (projectDto.name() == null || projectDto.name().isBlank()) {
            throw new IllegalArgumentException("Project must have a name");
        }
        Project p = projectMapper.fromDto(projectDto);
        if (projectDto.workspaceId() != null) {
            Workspace ws = workspaceRepository.findById(projectDto.workspaceId())
                    .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));
            p.setWorkspace(ws);
        }
        if (projectDto.ownerId() != null) {
            User owner = userRepository.findById(projectDto.ownerId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
            p.setOwner(owner);
        }
        Project saved = projectRepository.save(p);
        return projectMapper.toDto(saved);
    }

    @GetMapping(path = "/{project_id}")
    public ProjectDto getProjectById(@PathVariable("project_id") UUID projectId) {
        return projectRepository.findById(projectId).map(projectMapper::toDto).orElse(null);
    }

    @PutMapping(path = "/{project_id}")
    public ProjectDto updateProject(@PathVariable("project_id") UUID projectId, @RequestBody ProjectDto projectDto) {
        Project existing = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        existing.setName(projectDto.name());
        existing.setDescription(projectDto.description());
        if (projectDto.workspaceId() != null) {
            Workspace ws = workspaceRepository.findById(projectDto.workspaceId())
                    .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));
            existing.setWorkspace(ws);
        }
        if (projectDto.ownerId() != null) {
            User owner = userRepository.findById(projectDto.ownerId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
            existing.setOwner(owner);
        }
        Project updated = projectRepository.save(existing);
        return projectMapper.toDto(updated);
    }

    @DeleteMapping(path = "/{project_id}")
    public void deleteProject(@PathVariable("project_id") UUID projectId) {
        projectRepository.deleteById(projectId);
    }
}
