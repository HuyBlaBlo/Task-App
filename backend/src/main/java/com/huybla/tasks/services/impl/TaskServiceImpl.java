package com.huybla.tasks.services.impl;

import com.huybla.tasks.domain.entity.Task;
import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.domain.entity.TaskPriority;
import com.huybla.tasks.domain.entity.TaskStatus;
import com.huybla.tasks.repositories.TaskListRepository;
import com.huybla.tasks.repositories.TaskRepository;
import com.huybla.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskListRepository taskListRepository;

  public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
    this.taskRepository = taskRepository;
    this.taskListRepository = taskListRepository;
  }

  @Override
  public List<Task> listTasks(UUID taskListId) {
    return this.taskRepository.findByTaskListId(taskListId);
  }

  @Override
  public Task createTask(UUID taskListId, Task task) {
    // to make sure that only backend can create id for task
    if (task.getId() != null) {
      throw new IllegalArgumentException("Task already has an Id");
    }

    if (task.getTitle() == null || task.getTitle().isBlank()) {
      throw new IllegalArgumentException("Task must has a title");
    }

    TaskPriority taskPriority = Optional.ofNullable((task.getTaskPriority())).orElse(TaskPriority.MEDIUM);

    TaskStatus taskStatus = TaskStatus.OPEN;

    TaskList taskList = taskListRepository.findById(taskListId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Task List Id"));

    Task newTask = new Task(
        null,
        LocalDateTime.now(),
        task.getTitle(),
        task.getDecription(),
        task.getDueDate(),
        taskStatus,
        taskPriority,
        LocalDateTime.now(),
        taskList);

    return this.taskRepository.save(newTask);
  }

  // get a Task by id
  @Override
  public Optional<Task> getTask(UUID taskListId, UUID taskId) {
    return this.taskRepository.findByTaskListIdAndId(taskListId, taskId);
  }

  @Override
  public Task updateTask(UUID taskListId, UUID taskId, Task task) {
    if (task.getId() == null) {
      throw new IllegalArgumentException("Task must have an Id");
    }

    if (!Objects.equals(taskId, task.getId())) {
      throw new IllegalArgumentException("Task Id do not match");
    }

    if (task.getTaskPriority() == null) {
      throw new IllegalArgumentException("Task must have a valid Priority");
    }

    if (task.getTitle() == null) {
      throw new IllegalArgumentException("Task must have a valid Title");
    }
    Task existsTask = this.taskRepository.findByTaskListIdAndId(taskListId, taskId)
        .orElseThrow(() -> new IllegalArgumentException("Task not found"));

    // NOTE: The json request body must include the 'taskStatus' field.
    existsTask.setTitle(task.getTitle());
    existsTask.setDecription(task.getDecription());
    existsTask.setDueDate(task.getDueDate());
    existsTask.setTaskPriority(task.getTaskPriority());
    existsTask.setTaskStatus(task.getTaskStatus());
    existsTask.setUpdated(LocalDateTime.now());
    return this.taskRepository.save(existsTask);
  }

  // deleteTask
  @Override
  public void deleteTask(UUID taskListId, UUID taskId) {
    this.taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
  }
}
