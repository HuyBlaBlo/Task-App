package com.huybla.tasks.doamin.dto;

import com.huybla.tasks.doamin.entity.TaskPriority;
import com.huybla.tasks.doamin.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String decription,
        LocalDateTime dueDate,
        TaskPriority taskPriority,
        TaskStatus taskStatus
) {

}
