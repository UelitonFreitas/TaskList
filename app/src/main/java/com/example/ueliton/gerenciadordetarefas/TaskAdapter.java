package com.example.ueliton.gerenciadordetarefas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ueliton.gerenciadordetarefas.dao.TaskListsDAO;
import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ueliton on 27/05/2015.
 */


public class TaskAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Task> list;
    private Activity activity;
    public Resources res;
    public Task tempValues = null;

    public TaskAdapter(Activity context, List<Task> list) {
        this.activity = context;
        this.list = list;
        this.inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public Task task;
        public TextView text;
        public CheckBox check;
        public int position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if(convertView==null){

            view = inflater.inflate(R.layout.item_raw, null);

            holder = new ViewHolder();
            holder.position = position;
            holder.text = (TextView) view.findViewById(R.id.listEditText);
            holder.check = (CheckBox)view.findViewById(R.id.listCheckBox);
            holder.task = (Task) getItem(position);
            view.setTag( holder );
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if(list.size()<=0)
        {
            holder.text.setText("No Data");
        }
        else
        {
            tempValues = null;
            tempValues = ( Task ) list.get( position );

            holder.text.setText(tempValues.getName());
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = activity.getIntent();
                    TaskList taskList = (TaskList) intent.getSerializableExtra("selectedTasksList");
                    TaskListsDAO dao = new TaskListsDAO(activity);

                    taskList.setId(taskList.getId());
                    dao.update(taskList);

                    dao.close();
                    intent = new Intent(activity, TaskItemForm.class);
                    intent.putExtra("tasksList", taskList);
                    intent.putExtra("task", holder.task);
                    activity.startActivity(intent);
                }

            });
            holder.check.setChecked(holder.task.getDone());
        }
        return view;
    }

}
