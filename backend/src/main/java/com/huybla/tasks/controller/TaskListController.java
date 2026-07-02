package com.huybla.tasks.controller;

import com.huybla.tasks.domain.dto.TaskListDto;
import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.mappers.TaskListMapper;
import com.huybla.tasks.services.TaskListSevice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListSevice taskListSevice;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListSevice taskListSevice, TaskListMapper taskListMapper) {
        this.taskListSevice = taskListSevice;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists(){
         return this.taskListSevice.listTaskList()
                 .stream()
                 .map(taskListMapper::toDto)
                 .toList();
    }

    @PostMapping(path = "")
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createTaskList = taskListSevice.createTaskList(taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(createTaskList);
    }
}
