package com.example.ueliton.gerenciadordetarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class TaskListsDAO extends SQLiteOpenHelper{
    private static final String name = "TaskList_db";
    private static final int version = 9;

    private static final String TABLE_TASKLISTS = "taskLists";
    private static final String TABLE_TASKITENS = "taskListsItens";
    private static final String COLUM_ID = "id";
    private static final String COLUM_TASK_ID = "task_id";
    private static final String COLUM_NAME = "name";
    private static final String COLUM_DONE = "done";
    private String TAG  = "TaskListDAO";


    public TaskListsDAO(Context context) {
        super(context, name, null, version);
    }


    public void save(Long id, Task task) {
        ContentValues values = new ContentValues();
        values.put(COLUM_NAME, task.getName());
        values.put(COLUM_TASK_ID, id);
        getWritableDatabase().insert(TABLE_TASKITENS, null, values);
    }

    public void save(TaskList task) {
        ContentValues values = new ContentValues();
        values.put(COLUM_NAME, task.getName());
        getWritableDatabase().insert(TABLE_TASKLISTS, null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE "+TABLE_TASKLISTS+" ("+COLUM_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " "+COLUM_NAME+" TEXT UNIQUE NOT NULL, "
                +COLUM_DONE+ " BOOLEAN);";

        db.execSQL(ddl);

        ddl = "CREATE TABLE "+TABLE_TASKITENS+" ("+COLUM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUM_NAME+" TEXT NOT NULL,"
                +COLUM_TASK_ID+" INTEGER,"
                +COLUM_DONE+ " BOOLEAN,"
                +"FOREIGN KEY("+COLUM_TASK_ID+") REFERENCES "+TABLE_TASKLISTS+"( "+COLUM_ID+" )); ";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS "+TABLE_TASKLISTS+";";
        db.execSQL(ddl);
        ddl = "DROP TABLE IF EXISTS "+TABLE_TASKITENS+";";
        db.execSQL(ddl);

        this.onCreate(db);
    }

    public List<TaskList> getTaskLists() {

        String[] colums = {COLUM_ID, COLUM_NAME};
        Cursor cursor = getWritableDatabase().query(TABLE_TASKLISTS,
                                                    colums, null, null,null,null,null);

        ArrayList<TaskList> aLotOfTaskLists = new ArrayList<TaskList>();
        while(cursor.moveToNext()){
            TaskList taskLists = new TaskList();
            taskLists.setId(cursor.getLong(0));
            taskLists.setName(cursor.getString(1));
            aLotOfTaskLists.add(taskLists);
        }

        return aLotOfTaskLists;
    }

    public TaskList getTaskList(String taskName) {
        String[] colums = {COLUM_ID, COLUM_NAME};
        Cursor cursor = getWritableDatabase().query(TABLE_TASKLISTS,
                colums, COLUM_NAME + " = " + taskName , null,null,null,null);

        cursor.moveToNext();
        TaskList taskList = new TaskList();
        taskList.setId(cursor.getLong(0));
        taskList.setName(cursor.getString(1));

        return taskList;
    }

    public void update(TaskList taskList) {

        ContentValues values = new ContentValues();
        String[] args = {Long.toString(taskList.getId())};

        values.put(COLUM_ID, taskList.getId());
        values.put(COLUM_NAME, taskList.getName());

        getWritableDatabase().update(TABLE_TASKLISTS, values, COLUM_ID + "=?", args);


    }

    public boolean taskIsDone(TaskList taskList){

        String[] colums = {COLUM_DONE};
        Cursor cursor = getWritableDatabase().query(TABLE_TASKITENS,
                colums, COLUM_TASK_ID + " = " + Long.toString(taskList.getId()), null,null,null,null);

        ArrayList<Task> aLotOfTasks = new ArrayList<Task>();
        while(cursor.moveToNext()){
           if (cursor.getInt(0) == 0) {
               return false;
           }
        }
        return true;
    }

    public void listDone(TaskList taskList){

        ContentValues values = new ContentValues();
        String[] args = {Long.toString(taskList.getId())};

        values.put(COLUM_DONE, taskList.getDone());
        getWritableDatabase().update(TABLE_TASKITENS, values, COLUM_TASK_ID + "=?", args);
    }

    public void update(Task task) {

        ContentValues values = new ContentValues();

        values.put(COLUM_ID, task.getId());
        values.put(COLUM_NAME, task.getName());
        values.put(COLUM_DONE, task.getDone()?1:0);

        String[] args = {Long.toString(task.getId())};
        getWritableDatabase().update(TABLE_TASKITENS, values, COLUM_ID+"=?", args);
    }

    public List<Task> getTaskItens(Long id) {

        String[] colums = {COLUM_ID, COLUM_NAME, COLUM_DONE, COLUM_TASK_ID};
        Cursor cursor = getWritableDatabase().query(TABLE_TASKITENS,
                colums, COLUM_TASK_ID + " = " + Long.toString(id), null,null,null,null);

        ArrayList<Task> aLotOfTasks = new ArrayList<Task>();
        while(cursor.moveToNext()){
            Task task = new Task();
            task.setId(cursor.getLong(0));
            task.setName(cursor.getString(1));
            int a = cursor.getInt(2);
            task.setDone(cursor.getInt(2) == 1? true : false);
            task.setTaskListId(cursor.getLong(3));
            aLotOfTasks.add(task);
        }

        return aLotOfTasks;
    }

    public void delete(TaskList list) {
        String[] args = {Long.toString(list.getId())};
        getWritableDatabase().delete(TABLE_TASKITENS, COLUM_TASK_ID+"=?", args);
        getWritableDatabase().delete(TABLE_TASKLISTS, COLUM_ID + "=?", args);
    }

    public void delete(Task task) {
        String[] args = {Long.toString(task.getId())};
        getWritableDatabase().delete(TABLE_TASKITENS, COLUM_ID+"=?", args);
    }

    public TaskList getTaskListByName(String taskName) {
        String[] colums = {COLUM_ID, COLUM_NAME, COLUM_DONE};
        Cursor cursor = getWritableDatabase().query(TABLE_TASKLISTS, colums, COLUM_NAME + " =? ", new String[]{taskName}, null,null,null,null);

        if (cursor.moveToNext())
            return new TaskList(cursor.getLong(0), cursor.getString(1),
                    cursor.getInt(2)==1 ? true:false);

        return null;
    }
}
