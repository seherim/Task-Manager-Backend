package com.project.tasks.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tasks.domain.dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.project.tasks.domain.entities.Task;
import com.project.tasks.mappers.TaskMapper;
import com.project.tasks.services.TaskService;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();

    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskDto taskDto) {
        log.info("Received createTask request for taskListId={} with payload: {}", taskListId, taskDto);
        Task createdTask = taskService.createTask(
                taskListId,
                taskMapper.fromDto(taskDto));
        return taskMapper.toDto(createdTask);
    }
}
