package com.example.ueliton.gerenciadordetarefas;

import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ueliton.gerenciadordetarefas.model.Task;

/**
 * Created by Ueliton on 26/05/2015.
 */
public class TaskItemFormHelper {
    private EditText taskTitle;
    private CheckBox done;

    public TaskItemFormHelper(TaskItemForm taskItemForm) {
        taskTitle = (EditText) taskItemForm.findViewById(R.id.task_item_title);
    }

    public CheckBox getDone() {
        return done;
    }

    public void setDone(CheckBox done) {
        this.done = done;
    }

    public EditText getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(EditText taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Task getTask() {
        return new Task(taskTitle.getText().toString());
    }

    public void setName(String name) {
        this.taskTitle.setText(name);
    }

    public void setDone(boolean done) {
        this.done.setChecked(done);
    }
}
