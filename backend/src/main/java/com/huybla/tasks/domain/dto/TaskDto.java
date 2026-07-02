package com.huybla.tasks.domain.dto;

import com.huybla.tasks.domain.entity.TaskPriority;
import com.huybla.tasks.domain.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority taskPriority,
        TaskStatus taskStatus
) {

}
