package com.project.tasks.mappers;

import com.project.tasks.domain.dto.UserDto;
import com.project.tasks.domain.entities.User;

public interface UserMapper {
    User fromDto(UserDto dto);

    UserDto toDto(User user);
}
