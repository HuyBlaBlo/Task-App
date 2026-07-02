package com.huybla.tasks.mappers;

import com.huybla.tasks.domain.dto.TaskDto;
import com.huybla.tasks.domain.entity.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);

}
