package com.huybla.tasks.doamin.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false,nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, updatable = true)
    private String title;

    @Column(name = "decription")
    private String decription;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "status", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "priority", nullable = false)
    private TaskPriority taskPriority;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;


}
