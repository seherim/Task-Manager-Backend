package com.project.tasks.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.tasks.domain.entities.TaskList;

public interface TaskListService {

    List<TaskList> ListTaskLists();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList(UUID taskListId, TaskList taskList);
}
