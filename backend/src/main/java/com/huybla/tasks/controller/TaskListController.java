package com.huybla.tasks.controller;

import com.huybla.tasks.domain.dto.TaskListDto;
import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.mappers.TaskListMapper;
import com.huybla.tasks.services.TaskListSevice;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListSevice taskListSevice;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListSevice taskListSevice, TaskListMapper taskListMapper) {
        this.taskListSevice = taskListSevice;
        this.taskListMapper = taskListMapper;
    }

    // get all Task List
    @GetMapping
    public List<TaskListDto> listTaskLists(){
         return this.taskListSevice.listTaskList()
                 .stream()
                 .map(taskListMapper::toDto)
                 .toList();
    }

    // create a new Task List
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createTaskList = taskListSevice.createTaskList(taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(createTaskList);
    }

    // get a Task List by Id
    @GetMapping(path = "/{id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("id")UUID taskListId){
        return this.taskListSevice.getTaskList(taskListId)
                .map(taskListMapper::toDto);
    }
}
