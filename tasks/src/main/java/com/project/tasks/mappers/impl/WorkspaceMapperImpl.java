package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.WorkspaceDto;
import com.project.tasks.domain.entities.User;
import com.project.tasks.domain.entities.Workspace;
import com.project.tasks.mappers.WorkspaceMapper;

@Component
public class WorkspaceMapperImpl implements WorkspaceMapper {

    @Override
    public Workspace fromDto(WorkspaceDto dto) {
        if (dto == null)
            return null;
        User owner = dto.ownerId() == null ? null : new User(dto.ownerId(), null, null, null, null, false, null, null);
        return new Workspace(dto.id(), dto.name(), owner, dto.created());
    }

    @Override
    public WorkspaceDto toDto(Workspace workspace) {
        if (workspace == null)
            return null;
        return new WorkspaceDto(workspace.getId(), workspace.getName(),
                workspace.getOwner() == null ? null : workspace.getOwner().getId(), workspace.getCreated());
    }
}
