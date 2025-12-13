package com.project.tasks.mappers;

import com.project.tasks.domain.dto.RoleDto;
import com.project.tasks.domain.entities.Role;

public interface RoleMapper {
    Role fromDto(RoleDto dto);

    RoleDto toDto(Role role);
}
