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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ueliton.gerenciadordetarefas.dao.TaskListsDAO;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ueliton on 27/05/2015.
 */


public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<TaskList> list;
    private MainActivity activity;
    public Resources res;
    public TaskList tempValues = null;

    public ListAdapter(MainActivity context, List<TaskList> list) {
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

        public TaskList taskList;
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
            holder.taskList = (TaskList) getItem(position);
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
            tempValues = ( TaskList ) list.get( position );

            holder.text.setText(tempValues.getName());
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent taskFormActivity = new Intent(activity, TaskListForm.class);
                    taskFormActivity.putExtra("selectedTasksList", holder.taskList);
                    activity.startActivity(taskFormActivity);
                }
            });
            holder.text.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TaskListsDAO dao = new TaskListsDAO(activity);
                    dao.delete(holder.taskList);
                    dao.close();
                    activity.loadAdapter();
                    Toast.makeText(activity, "Lista Deletada!!", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            //Se todas as tarefas foram cumpridas.
            TaskListsDAO dao = new TaskListsDAO(activity);
            if (dao.taskIsDone(holder.taskList)) {
                holder.check.setChecked(true);
            }
            holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TaskListsDAO dao = new TaskListsDAO(activity);
                    holder.taskList.setDone(isChecked);
                    dao.listDone(holder.taskList);
                    dao.close();
                }
            });
        }
        return view;
    }

}
