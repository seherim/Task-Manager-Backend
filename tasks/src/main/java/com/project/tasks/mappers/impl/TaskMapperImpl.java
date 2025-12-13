package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

import com.project.tasks.domain.dto.TaskDto;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.mappers.TaskMapper;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
        String d = taskDto.dateDue();
        LocalDateTime dateDue = (d == null || d.isBlank()) ? null : LocalDateTime.parse(d);

        return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                dateDue,
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
