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

import com.project.tasks.domain.dto.SubtaskDto;
import com.project.tasks.domain.entities.Subtask;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.mappers.SubtaskMapper;
import com.project.tasks.repositories.SubtaskRepository;
import com.project.tasks.repositories.TaskRepository;

@RestController
@RequestMapping("/subtasks")
public class SubtaskController {
    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;
    private final TaskRepository taskRepository;

    public SubtaskController(SubtaskRepository subtaskRepository, SubtaskMapper subtaskMapper,
            TaskRepository taskRepository) {
        this.subtaskRepository = subtaskRepository;
        this.subtaskMapper = subtaskMapper;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<SubtaskDto> getAllSubtasks() {
        return subtaskRepository.findAll().stream().map(subtaskMapper::toDto).toList();
    }

    @PostMapping
    public SubtaskDto createSubtask(@RequestBody SubtaskDto subtaskDto) {
        if (subtaskDto.title() == null || subtaskDto.title().isBlank()) {
            throw new IllegalArgumentException("Subtask must have a title");
        }
        Subtask s = subtaskMapper.fromDto(subtaskDto);
        if (subtaskDto.parentTaskId() != null) {
            Task parent = taskRepository.findById(subtaskDto.parentTaskId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent task not found"));
            s.setParentTask(parent);
        }
        Subtask saved = subtaskRepository.save(s);
        return subtaskMapper.toDto(saved);
    }

    @GetMapping(path = "/{subtask_id}")
    public SubtaskDto getSubtaskById(@PathVariable("subtask_id") UUID subtaskId) {
        return subtaskRepository.findById(subtaskId).map(subtaskMapper::toDto).orElse(null);
    }

    @PutMapping(path = "/{subtask_id}")
    public SubtaskDto updateSubtask(@PathVariable("subtask_id") UUID subtaskId, @RequestBody SubtaskDto subtaskDto) {
        Subtask existing = subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new IllegalArgumentException("Subtask not found"));
        existing.setTitle(subtaskDto.title());
        existing.setDescription(subtaskDto.description());
        existing.setPriority(subtaskDto.priority());
        existing.setStatus(subtaskDto.status());
        Subtask updated = subtaskRepository.save(existing);
        return subtaskMapper.toDto(updated);
    }

    @DeleteMapping(path = "/{subtask_id}")
    public void deleteSubtask(@PathVariable("subtask_id") UUID subtaskId) {
        subtaskRepository.deleteById(subtaskId);
    }
}
