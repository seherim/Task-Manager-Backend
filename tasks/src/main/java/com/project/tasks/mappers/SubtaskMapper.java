package com.project.tasks.mappers;

import com.project.tasks.domain.dto.SubtaskDto;
import com.project.tasks.domain.entities.Subtask;

public interface SubtaskMapper {
    Subtask fromDto(SubtaskDto dto);

    SubtaskDto toDto(Subtask subtask);
}
