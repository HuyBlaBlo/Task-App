package com.huybla.tasks.controller;

import com.huybla.tasks.domain.dto.TaskDto;
import com.huybla.tasks.domain.entity.Task;
import com.huybla.tasks.mappers.TaskMapper;
import com.huybla.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/task")
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  public TaskController(TaskService taskService, TaskMapper taskMapper) {
    this.taskService = taskService;
    this.taskMapper = taskMapper;
  }

  @GetMapping
  public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
    return this.taskService.listTasks(taskListId)
        .stream()
        .map(task -> taskMapper.toDto(task))
        .toList();
  }

  @PostMapping
  public TaskDto createTask(
      @PathVariable("task_list_id") UUID taskListId,
      @RequestBody TaskDto taskDto) {
    Task createTask = this.taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
    return taskMapper.toDto(createTask);
  }

  @GetMapping(path = "/{task_id}")
  public Optional<TaskDto> getTask(
      @PathVariable("task_list_id") UUID taskListId,
      @PathVariable("task_id") UUID taskId) {
    return this.taskService.getTask(taskListId, taskId).map(task -> taskMapper.toDto(task));
  }

  // update task
  @PutMapping(path = "/{task_id}")
  public TaskDto updateTask(
      @PathVariable("task_list_id") UUID taskListId,
      @PathVariable("task_id") UUID taskId,
      @RequestBody TaskDto taskDto) {

    Task updateTask = this.taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDto));

    return this.taskMapper.toDto(updateTask);

  }

  // delete task
  @DeleteMapping(path = "/{task_id}")
  public void deleteTask(
      @PathVariable("task_list_id") UUID taskListId,
      @PathVariable("task_id") UUID taskId) {
    this.taskService.deleteTask(taskListId, taskId);
  }
}
