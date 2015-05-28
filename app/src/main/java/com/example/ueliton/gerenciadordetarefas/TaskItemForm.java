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

import com.example.ueliton.gerenciadordetarefas.dao.TaskListsDAO;
import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.List;


public class TaskItemForm extends ActionBarActivity {

    TaskItemFormHelper taskHelper;
    TaskList aTaskList;
    List<Task> taskItens;
    Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_item_form);

        taskHelper = new TaskItemFormHelper(TaskItemForm.this);
        Button saveButton = (Button) findViewById(R.id.button_item_list_save);

        aTaskList = (TaskList) this.getIntent().getSerializableExtra("tasksList");
        task =  (Task) this.getIntent().getSerializableExtra("task");

        if (task != null){
            taskHelper.setName(task.getName());
        }

        task = taskHelper.getTask();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskListsDAO dao = new TaskListsDAO(TaskItemForm.this);
                if (task != null){
                    dao.update(task);
                }
                else {
                    dao.save(aTaskList.getId(), task);
                }
                dao.close();
                finish();
            }
        });

       // TaskListsDAO dao = new TaskListsDAO(this);
        //taskItens = dao.getTaskItens(aTaskList.getId());
        //dao.close();
    }

    protected void onResume() {
        super.onResume();
        //Pega as listas de tarefas.
        // ArrayAdapter arrayAdapter = new ArrayAdapter<Task>(this,
        //        android.R.layout.simple_list_item_1,
        //         taskItens);

         //itens.setAdapter(arrayAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_item_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
