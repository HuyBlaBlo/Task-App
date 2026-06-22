package com.huybla.tasks.doamin.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "task_list")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tite", nullable = true)
    private String title;


    @Column(name = "description")
    private String description;

    @Column(name = "crated", nullable = false)
    private LocalDateTime crated;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;


    
}
