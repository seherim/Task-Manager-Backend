package com.project.tasks.services;

import com.project.tasks.domain.entities.Task;
import java.util.UUID;

import java.util.List;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);

    Task createTask(UUID taskListId, Task task);
}
