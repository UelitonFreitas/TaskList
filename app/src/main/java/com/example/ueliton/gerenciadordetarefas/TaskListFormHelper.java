package com.example.ueliton.gerenciadordetarefas;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.List;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class TaskListFormHelper {

    ListView listView;
    List<Task> tasks;
    EditText editName;

    public EditText getEditName() {
        return editName;
    }

    public TaskListFormHelper(TaskListForm taskListForm) {
        editName = (EditText) taskListForm.findViewById(R.id.taskListName);
        listView = (ListView) taskListForm.findViewById(R.id.task_list_itens);
    }

    public Task getTask() {
        return new Task(this.editName.toString());
    }

    public TaskList getTasksLists(){

        TaskList tasks = new TaskList(this.editName.getText().toString());
        return tasks;
    }

    public void setTaskLists(TaskList aTaskList) {

        editName.setText(aTaskList.getName());
        //List<Task> listOfTasks = aTaskList.getListOfTasks();
       // for (int i = 0; i < listOfTasks.size(); i++) {
       //     TextView name = (TextView) list.getChildAt(i);
       //     name.setText(listOfTasks.get(i).getName());
        //}
    }
}
