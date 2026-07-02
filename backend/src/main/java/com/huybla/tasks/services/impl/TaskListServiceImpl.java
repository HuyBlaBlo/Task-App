package com.huybla.tasks.services.impl;

import com.huybla.tasks.doamin.entity.TaskList;
import com.huybla.tasks.repositories.TaskListRepository;
import com.huybla.tasks.services.TaskListSevice;
import org.springframework.stereotype.Service;

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


}
