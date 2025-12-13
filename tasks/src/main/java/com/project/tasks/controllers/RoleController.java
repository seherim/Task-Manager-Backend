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

import com.project.tasks.domain.dto.RoleDto;
import com.project.tasks.domain.entities.Role;
import com.project.tasks.mappers.RoleMapper;
import com.project.tasks.repositories.RoleRepository;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleController(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @GetMapping
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).toList();
    }

    @PostMapping
    public RoleDto createRole(@RequestBody RoleDto roleDto) {
        if (roleDto.name() == null || roleDto.name().isBlank()) {
            throw new IllegalArgumentException("Role must have a name");
        }
        Role saved = roleRepository.save(roleMapper.fromDto(roleDto));
        return roleMapper.toDto(saved);
    }

    @GetMapping(path = "/{role_id}")
    public RoleDto getRoleById(@PathVariable("role_id") UUID roleId) {
        return roleRepository.findById(roleId).map(roleMapper::toDto).orElse(null);
    }

    @PutMapping(path = "/{role_id}")
    public RoleDto updateRole(@PathVariable("role_id") UUID roleId, @RequestBody RoleDto roleDto) {
        Role existing = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        existing.setName(roleDto.name());
        existing.setDescription(roleDto.description());
        Role updated = roleRepository.save(existing);
        return roleMapper.toDto(updated);
    }

    @DeleteMapping(path = "/{role_id}")
    public void deleteRole(@PathVariable("role_id") UUID roleId) {
        roleRepository.deleteById(roleId);
    }
}
