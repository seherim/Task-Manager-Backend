package com.project.tasks.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.tasks.domain.dto.TaskListDto;
import com.project.tasks.domain.entities.TaskList;
import com.project.tasks.repositories.TaskListRepository;
import com.project.tasks.services.TaskListService;

import org.springframework.stereotype.Service;

@Service
public class TaskListServiceImpl implements TaskListService {
    
    private final TaskListRepository taskListRepository;
    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> ListTaskLists() {

        return taskListRepository.findAll();
    }
    
    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()) {
            throw new IllegalArgumentException("Task list already has an ID");
        }
        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title must be present");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now));
    }
    
    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }
    public Optional <TaskListDto> getTaskListDto (UUID id) {
        return taskListRepository.findById(id).map(taskList -> new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                taskList.getTasks() != null ? taskList.getTasks().size() : 0,
                0,
                null
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTaskList'");
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTaskList'");
    }

}
