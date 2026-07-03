package com.huybla.tasks.services.impl;

import com.huybla.tasks.domain.entity.Task;
import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.domain.entity.TaskPriority;
import com.huybla.tasks.domain.entity.TaskStatus;
import com.huybla.tasks.exceptions.ResourceNotFoundException;
import com.huybla.tasks.repositories.TaskListRepository;
import com.huybla.tasks.repositories.TaskRepository;
import com.huybla.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        if(task.getId() != null){
            throw new IllegalArgumentException("Task already has an Id");
        }

        if(task.getTitle() == null || task.getTitle().isBlank()){
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
                taskList
        );

        return this.taskRepository.save(newTask);
    }

    // get a Task by id
    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        // check Task List is exists in database
        if(!taskListRepository.existsById(taskListId)){
            throw new ResourceNotFoundException("Not found Task List Id in DB");
        }

        if(!taskRepository.)
        return this.taskRepository.findByTaskListIdAndId(taskListId,taskId);
    }
}
