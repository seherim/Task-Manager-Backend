package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.TaskDto;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.mappers.TaskMapper;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dateDue(),
                taskDto.status(),
                taskDto.priority(),
                null,
                null,
                null);
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDateDue().toString(),
                task.getPriority(),
                task.getStatus());
    }
}
