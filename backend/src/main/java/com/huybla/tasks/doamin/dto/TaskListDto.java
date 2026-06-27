package com.huybla.tasks.doamin.dto;

import com.huybla.tasks.doamin.entity.Task;

import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        String title,
        String description,
        int count,
        Double progress,
        List<TaskDto> tasks
        ) {
}
