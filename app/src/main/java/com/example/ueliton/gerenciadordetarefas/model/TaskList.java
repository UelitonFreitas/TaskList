package com.example.ueliton.gerenciadordetarefas.model;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class TaskList implements Serializable{
    private Long id;
    private String name;
    private List<Task> listOfTasks;
    private boolean done;

    public TaskList(Long anInt, String string, boolean b) {
        this.id = anInt;
        this.name = string;
        this.done = b;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TaskList(String name) {
        this.name = name;
        this.listOfTasks = new LinkedList<Task>();
        this.done = false;
    }

    public TaskList() {
        this.done = false;
    }

    public void addTask(Task aTask) {
        listOfTasks.add(aTask);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Task> getListOfTasks() {
        return this.listOfTasks;
    }

    public Long getId() {
        return id;
    }

    public boolean getDone() {
        return done;
    }
}
