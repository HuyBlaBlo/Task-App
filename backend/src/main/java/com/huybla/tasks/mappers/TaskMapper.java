package com.huybla.tasks.mappers;

import com.huybla.tasks.doamin.dto.TaskDto;
import com.huybla.tasks.doamin.entity.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    Task toDto(Task task);

}
