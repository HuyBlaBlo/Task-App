package com.huybla.tasks.mappers;

import com.huybla.tasks.domain.dto.TaskListDto;
import com.huybla.tasks.domain.entity.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
