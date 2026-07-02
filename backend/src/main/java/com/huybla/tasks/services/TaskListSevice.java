package com.huybla.tasks.services;

import com.huybla.tasks.domain.entity.TaskList;

import java.util.List;

public interface TaskListSevice {
    List<TaskList> listTaskList();
    TaskList createTaskList(TaskList taskList);
}
