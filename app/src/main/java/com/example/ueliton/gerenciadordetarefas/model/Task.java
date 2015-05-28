package com.example.ueliton.gerenciadordetarefas.model;

import java.io.Serializable;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class Task implements Serializable {

    private Long id;
    private Long taskListId;
    private String name;
    private boolean done;

    public Task(String s) {
        this.name = s;
        this.done = false;
    }

    public Task() {
        this.done = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean getDone() {
        return done;
    }
}
