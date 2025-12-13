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

import com.project.tasks.domain.dto.UserDto;
import com.project.tasks.domain.entities.User;
import com.project.tasks.mappers.UserMapper;
import com.project.tasks.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        if (userDto.username() == null || userDto.username().isBlank()) {
            throw new IllegalArgumentException("User must have a username");
        }
        User saved = userRepository.save(userMapper.fromDto(userDto));
        return userMapper.toDto(saved);
    }

    @GetMapping(path = "/{user_id}")
    public UserDto getUserById(@PathVariable("user_id") UUID userId) {
        return userRepository.findById(userId).map(userMapper::toDto).orElse(null);
    }

    @PutMapping(path = "/{user_id}")
    public UserDto updateUser(@PathVariable("user_id") UUID userId, @RequestBody UserDto userDto) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        existing.setUsername(userDto.username());
        existing.setEmail(userDto.email());
        existing.setDisplayName(userDto.displayName());
        existing.setActive(userDto.active());
        User updated = userRepository.save(existing);
        return userMapper.toDto(updated);
    }

    @DeleteMapping(path = "/{user_id}")
    public void deleteUser(@PathVariable("user_id") UUID userId) {
        userRepository.deleteById(userId);
    }
}
