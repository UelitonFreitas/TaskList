package com.example.ueliton.gerenciadordetarefas;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class FormHelper {

    ListView list;
    EditText editName;

    public EditText getEditName() {
        return editName;
    }

    public FormHelper(TaskListForm taskListForm) {
        editName = (EditText) taskListForm.findViewById(R.id.taskListName);
        list = (ListView) taskListForm.findViewById(R.id.taskList);
    }

    public Task getTask() {
        return new Task(this.editName.toString());
    }


    public TaskList getTasksLists(){

        TaskList tasks = new TaskList(this.editName.getText().toString());

        for (int i = 0; i < list.getCount(); i++) {
            TextView name = (TextView) list.getChildAt(i);
            Task task = new Task(name.getText().toString());
            tasks.addTask(task);
        }
        return tasks;
    }
}
