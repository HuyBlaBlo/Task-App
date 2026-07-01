package com.huybla.tasks.mappers;

import com.huybla.tasks.doamin.dto.TaskListDto;
import com.huybla.tasks.doamin.entity.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
