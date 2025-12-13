package com.project.tasks.mappers;

import com.project.tasks.domain.dto.WorkspaceDto;
import com.project.tasks.domain.entities.Workspace;

public interface WorkspaceMapper {
    Workspace fromDto(WorkspaceDto dto);

    WorkspaceDto toDto(Workspace workspace);
}
