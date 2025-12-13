package com.project.tasks.mappers;

import com.project.tasks.domain.dto.ProjectDto;
import com.project.tasks.domain.entities.Project;

public interface ProjectMapper {
    Project fromDto(ProjectDto dto);

    ProjectDto toDto(Project project);
}
