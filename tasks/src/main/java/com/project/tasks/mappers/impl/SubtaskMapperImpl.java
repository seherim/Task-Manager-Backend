package com.project.tasks.mappers.impl;

import org.springframework.stereotype.Component;

import com.project.tasks.domain.dto.SubtaskDto;
import com.project.tasks.domain.entities.Subtask;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.mappers.SubtaskMapper;

@Component
public class SubtaskMapperImpl implements SubtaskMapper {

    @Override
    public Subtask fromDto(SubtaskDto dto) {
        if (dto == null)
            return null;
        Task parent = dto.parentTaskId() == null ? null : new Task(dto.parentTaskId(), null, null, null, null, null);
        return new Subtask(dto.id(), parent, dto.title(), dto.description(), dto.status(), dto.priority());
    }

    @Override
    public SubtaskDto toDto(Subtask subtask) {
        if (subtask == null)
            return null;
        return new SubtaskDto(subtask.getId(),
                subtask.getParentTask() == null ? null : subtask.getParentTask().getId(),
                subtask.getTitle(), subtask.getDescription(), subtask.getStatus(), subtask.getPriority());
    }
}
