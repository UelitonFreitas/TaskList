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

        EditText taskNameEdit = (EditText) findViewById(R.id.taskListName);
        final Button buttonSave = (Button) findViewById(R.id.button_save_task_list);

        taskListFormHelper = new TaskListFormHelper(TaskListForm.this);

        aTaskList = (TaskList) getIntent().getSerializableExtra("selectedTasksList");

        if (aTaskList != null){
            buttonSave.setText("Alterar");
            taskListFormHelper.setTaskLists(aTaskList);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskList taskList = taskListFormHelper.getTasksLists();

                TaskList tasks = taskListFormHelper.getTasksLists();
                TaskListsDAO dao = new TaskListsDAO(TaskListForm.this);
                if (aTaskList == null) {
                    dao.save(tasks);
                }
                else {
                    taskList.setId(aTaskList.getId());
                    dao.update(taskList);
                }
                dao.close();
                finish();
            }
        });
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
        //Pega as listas de tarefas.

        TaskListsDAO dao = new TaskListsDAO(this);
        List<Task> taskLists = dao.getTaskItens();
        dao.close();

        ArrayAdapter arrayAdapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1,
                taskLists);

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
                Intent newTask = new Intent(TaskListForm.this, TaskItemForm.class);
                newTask.putExtra("tasksList", TaskListForm.this.aTaskList);
                startActivity(newTask);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
