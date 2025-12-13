package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.RoleDto;
import com.project.tasks.domain.entities.Role;
import com.project.tasks.mappers.RoleMapper;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role fromDto(RoleDto dto) {
        if (dto == null)
            return null;
        return new Role(dto.id(), dto.name(), dto.description());
    }

    @Override
    public RoleDto toDto(Role role) {
        if (role == null)
            return null;
        return new RoleDto(role.getId(), role.getName(), role.getDescription());
    }
}
