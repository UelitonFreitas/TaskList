package com.example.ueliton.gerenciadordetarefas.model;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class Task {

    private Long id;
    private String name;
    private boolean done;

    public Task(String s) {
        this.name = s;
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


}
