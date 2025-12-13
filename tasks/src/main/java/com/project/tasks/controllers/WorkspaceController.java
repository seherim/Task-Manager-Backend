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

import com.project.tasks.domain.dto.WorkspaceDto;
import com.project.tasks.domain.entities.User;
import com.project.tasks.domain.entities.Workspace;
import com.project.tasks.mappers.WorkspaceMapper;
import com.project.tasks.repositories.UserRepository;
import com.project.tasks.repositories.WorkspaceRepository;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserRepository userRepository;

    public WorkspaceController(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper,
            UserRepository userRepository) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<WorkspaceDto> getAllWorkspaces() {
        return workspaceRepository.findAll().stream().map(workspaceMapper::toDto).toList();
    }

    @PostMapping
    public WorkspaceDto createWorkspace(@RequestBody WorkspaceDto workspaceDto) {
        if (workspaceDto.name() == null || workspaceDto.name().isBlank()) {
            throw new IllegalArgumentException("Workspace must have a name");
        }
        Workspace ws = workspaceMapper.fromDto(workspaceDto);
        if (workspaceDto.ownerId() != null) {
            User owner = userRepository.findById(workspaceDto.ownerId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner user not found"));
            ws.setOwner(owner);
        }
        Workspace saved = workspaceRepository.save(ws);
        return workspaceMapper.toDto(saved);
    }

    @GetMapping(path = "/{workspace_id}")
    public WorkspaceDto getWorkspaceById(@PathVariable("workspace_id") UUID workspaceId) {
        return workspaceRepository.findById(workspaceId).map(workspaceMapper::toDto).orElse(null);
    }

    @PutMapping(path = "/{workspace_id}")
    public WorkspaceDto updateWorkspace(@PathVariable("workspace_id") UUID workspaceId,
            @RequestBody WorkspaceDto workspaceDto) {
        Workspace existing = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));
        existing.setName(workspaceDto.name());
        if (workspaceDto.ownerId() != null) {
            User owner = userRepository.findById(workspaceDto.ownerId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner user not found"));
            existing.setOwner(owner);
        }
        Workspace updated = workspaceRepository.save(existing);
        return workspaceMapper.toDto(updated);
    }

    @DeleteMapping(path = "/{workspace_id}")
    public void deleteWorkspace(@PathVariable("workspace_id") UUID workspaceId) {
        workspaceRepository.deleteById(workspaceId);
    }
}
