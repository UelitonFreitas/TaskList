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
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadAdapter();

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

    public void loadAdapter() {
        TaskListsDAO dao = new TaskListsDAO(this);
        List<TaskList> taskLists = dao.getTaskLists();
        dao.close();
        ListAdapter arrayAdapter = new ListAdapter(this, taskLists);
        ((ListView) findViewById(R.id.lista)).setAdapter(arrayAdapter);
    }
}
