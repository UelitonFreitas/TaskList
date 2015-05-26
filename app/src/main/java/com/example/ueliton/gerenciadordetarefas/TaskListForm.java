package com.example.ueliton.gerenciadordetarefas;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ueliton.gerenciadordetarefas.dao.TaskListsDAO;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;


public class TaskListForm extends ActionBarActivity {

    FormHelper formHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        EditText taskNameEdit = (EditText) findViewById(R.id.taskListName);
        final Button buttonSave = (Button) findViewById(R.id.button_save_task_list);
        formHelper = new FormHelper(TaskListForm.this);

        final TaskList aTaskList = (TaskList) getIntent().getSerializableExtra("selectedTasksList");

        if (aTaskList != null){
            buttonSave.setText("Alterar");
            formHelper.setTaskLists(aTaskList);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskList taskList = formHelper.getTasksLists();

                TaskList tasks = formHelper.getTasksLists();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.new_task_list:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
