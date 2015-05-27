package com.example.ueliton.gerenciadordetarefas;

import android.widget.EditText;

import com.example.ueliton.gerenciadordetarefas.model.Task;

/**
 * Created by Ueliton on 26/05/2015.
 */
public class TaskItemFormHelper {
    private EditText taskTitle;

    public TaskItemFormHelper(TaskItemForm taskItemForm) {
        taskTitle = (EditText) taskItemForm.findViewById(R.id.task_item_title);
    }

    public Task getTask() {
        return new Task(taskTitle.getText().toString());
    }
}
