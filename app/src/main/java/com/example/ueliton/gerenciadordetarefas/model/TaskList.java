package com.example.ueliton.gerenciadordetarefas.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class TaskList {
    private Long id;
    private String name;
    private List<Task> listOfTasks;

    public TaskList(String name) {
        this.name = name;
        this.listOfTasks = new LinkedList<Task>();
    }

    public TaskList() {
        //do nothing;
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
}
