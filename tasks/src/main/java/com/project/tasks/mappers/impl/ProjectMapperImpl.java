package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.ProjectDto;
import com.project.tasks.domain.entities.Project;
import com.project.tasks.domain.entities.User;
import com.project.tasks.domain.entities.Workspace;
import com.project.tasks.mappers.ProjectMapper;

@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project fromDto(ProjectDto dto) {
        if (dto == null)
            return null;
        Workspace ws = dto.workspaceId() == null ? null : new Workspace(dto.workspaceId(), null, null, null);
        User owner = dto.ownerId() == null ? null : new User(dto.ownerId(), null, null, null, null, false, null, null);
        return new Project(dto.id(), dto.name(), dto.description(), ws, owner, dto.startDate(), dto.endDate());
    }

    @Override
    public ProjectDto toDto(Project project) {
        if (project == null)
            return null;
        return new ProjectDto(project.getId(), project.getName(), project.getDescription(),
                project.getWorkspace() == null ? null : project.getWorkspace().getId(),
                project.getOwner() == null ? null : project.getOwner().getId(),
                project.getStartDate(), project.getEndDate());
    }
}
