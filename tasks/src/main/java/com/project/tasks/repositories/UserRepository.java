package com.project.tasks.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tasks.domain.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
