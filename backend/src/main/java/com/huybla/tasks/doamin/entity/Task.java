package com.huybla.tasks.doamin.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
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

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;

    public Task() {
    }

    public Task(UUID id, LocalDateTime created, String title, String decription, LocalDateTime dueDate, TaskStatus taskStatus, TaskPriority taskPriority, LocalDateTime updated, TaskList taskList) {
        this.created = created;
        this.id = id;
        this.title = title;
        this.decription = decription;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus;
        this.taskPriority = taskPriority;
        this.updated = updated;
        this.taskList = taskList;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", decription='" + decription + '\'' +
                ", dueDate=" + dueDate +
                ", taskStatus=" + taskStatus +
                ", taskPriority=" + taskPriority +
                ", created=" + created +
                ", updated=" + updated +
                ", taskList=" + taskList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(decription, task.decription) && Objects.equals(dueDate, task.dueDate) && taskStatus == task.taskStatus && taskPriority == task.taskPriority && Objects.equals(created, task.created) && Objects.equals(updated, task.updated) && Objects.equals(taskList, task.taskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, decription, dueDate, taskStatus, taskPriority, created, updated, taskList);
    }
}
