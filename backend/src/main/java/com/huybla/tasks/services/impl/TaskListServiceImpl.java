package com.huybla.tasks.services.impl;

import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.repositories.TaskListRepository;
import com.huybla.tasks.services.TaskListSevice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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


}
