package com.huybla.tasks.services.impl;

import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.repositories.TaskListRepository;
import com.huybla.tasks.services.TaskListSevice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListSevice {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskList() {
        return this.taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getId() != null){
            throw new IllegalArgumentException("Task List has an ID");
        }

        if(taskList.getTitle() == null || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task List title must be present!");
        }

        // get the local date
        LocalDateTime now = LocalDateTime.now();

        return this.taskListRepository.save(new TaskList(
                null,
                now,
                taskList.getTitle(),
                taskList.getDescription(),
                now,
                null
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return this.taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {

        if (taskList.getId() == null){
            throw new IllegalArgumentException("Task list must have an Id");
        }

        // catch exception when user try to chance the task list id
        if(!Objects.equals(taskListId, taskList.getId())){
            throw new IllegalArgumentException("Attempting to change task list id, this's not permitted!");
        }

        TaskList existingTaskList = this.taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return this.taskListRepository.save(existingTaskList);
    }


}
