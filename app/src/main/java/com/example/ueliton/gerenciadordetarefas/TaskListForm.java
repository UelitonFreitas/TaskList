package com.example.ueliton.gerenciadordetarefas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ueliton.gerenciadordetarefas.dao.TaskListsDAO;
import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.List;


public class TaskListForm extends ActionBarActivity {

    TaskListFormHelper taskListFormHelper;
    ListView    itens;
    TaskList    aTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        itens = (ListView) findViewById(R.id.task_list_itens);

        taskListFormHelper = new TaskListFormHelper(TaskListForm.this);

        aTaskList = (TaskList) getIntent().getSerializableExtra("selectedTasksList");

        if (aTaskList != null){
            taskListFormHelper.setTaskLists(aTaskList);
        }
    }

    @Override
    public void onBackPressed()
    {
        TaskList taskList = taskListFormHelper.getTasksLists();
        TaskListsDAO dao = new TaskListsDAO(TaskListForm.this);
        if (aTaskList == null) {
            dao.save(taskList);
        }
        else {
            taskList.setId(aTaskList.getId());
            dao.update(taskList);
        }
        dao.close();
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (aTaskList == null) {
            return;
        }
        loadAdapter();
    }

    public void loadAdapter() {
        TaskListsDAO dao = new TaskListsDAO(this);
        List<Task> taskLists = dao.getTaskItens(aTaskList.getId());
        dao.close();
        TaskAdapter arrayAdapter = new TaskAdapter(this, taskLists);
        itens.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.new_task_list_item:

                String taskName = taskListFormHelper.getEditName().getText().toString();
                Intent newTask = new Intent(TaskListForm.this, TaskItemForm.class);

                if (taskName != null) {
                    TaskListsDAO dao = new TaskListsDAO(TaskListForm.this);
                    dao.save(new TaskList(taskName));
                    TaskList taskList = dao.getTaskListByName(taskName);
                    dao.close();
                    newTask.putExtra("tasksList", taskList);
                }
                else {
                    newTask.putExtra("tasksList", TaskListForm.this.aTaskList);
                }
                startActivity(newTask);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
