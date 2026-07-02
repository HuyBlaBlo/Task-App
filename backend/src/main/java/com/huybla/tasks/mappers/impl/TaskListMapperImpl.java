package com.huybla.tasks.mappers.impl;

import com.huybla.tasks.domain.dto.TaskListDto;
import com.huybla.tasks.domain.entity.Task;
import com.huybla.tasks.domain.entity.TaskList;
import com.huybla.tasks.domain.entity.TaskStatus;
import com.huybla.tasks.mappers.TaskListMapper;
import com.huybla.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                null,
                taskListDto.title(),
                taskListDto.description(),
                null,
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                        .map(taskMapper::fromDto)
                                        .toList()
                        ).orElse(null)
                );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                caculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto)
                                .toList()
                        ).orElse(null)

        );
    }

    private Double caculateTaskListProgress(List<Task> tasks){
        if(tasks == null){
            return null;
        }

        Long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED ==  task.getTaskStatus()).count();
        return (double)closedTaskCount / tasks.size();
    }

}
