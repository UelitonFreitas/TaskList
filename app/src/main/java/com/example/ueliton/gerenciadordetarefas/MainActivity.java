package com.example.ueliton.gerenciadordetarefas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ueliton.gerenciadordetarefas.dao.TaskListsDAO;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListView = (ListView) findViewById(R.id.lista);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                TaskList tasks = (TaskList) adapter.getItemAtPosition(position);
                Intent taskFormActivity = new Intent(MainActivity.this, TaskListForm.class);
                taskFormActivity.putExtra("selectedTasksList", tasks);
                startActivity(taskFormActivity);
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "Clique longo: " + adapter.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Pega as listas de tarefas.
        TaskListsDAO dao = new TaskListsDAO(this);
        List<TaskList> taskLists = dao.getTaskLists();
        dao.close();

        ArrayAdapter arrayAdapter = new ArrayAdapter<TaskList>(this,
                android.R.layout.simple_list_item_1,
                taskLists);
        
        taskListView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

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
                Intent newTask = new Intent(MainActivity.this, TaskListForm.class);
                startActivity(newTask);
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
