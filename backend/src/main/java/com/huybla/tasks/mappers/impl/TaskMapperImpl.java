package com.huybla.tasks.mappers.impl;

import com.huybla.tasks.doamin.dto.TaskDto;
import com.huybla.tasks.doamin.entity.Task;
import com.huybla.tasks.mappers.TaskMapper;

public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id(),
                null,
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.taskStatus(),
                taskDto.taskPriority(),
                null,null
        );
    }

    @Override
    public Task toDto(Task task) {
        return null;
    }
}
