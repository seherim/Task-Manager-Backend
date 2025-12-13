package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.UserDto;
import com.project.tasks.domain.entities.User;
import com.project.tasks.mappers.UserMapper;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDto(UserDto dto) {
        if (dto == null)
            return null;
        return new User(
                dto.id(),
                dto.username(),
                dto.email(),
                null,
                dto.displayName(),
                dto.active(),
                dto.created(),
                dto.lastLogin());
    }

    @Override
    public UserDto toDto(User user) {
        if (user == null)
            return null;
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisplayName(),
                user.isActive(),
                user.getCreated(),
                user.getLastLogin());
    }
}
