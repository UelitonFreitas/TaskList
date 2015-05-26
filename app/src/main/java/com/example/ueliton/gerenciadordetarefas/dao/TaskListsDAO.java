package com.example.ueliton.gerenciadordetarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ueliton.gerenciadordetarefas.model.Task;
import com.example.ueliton.gerenciadordetarefas.model.TaskList;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ueliton on 25/05/2015.
 */
public class TaskListsDAO extends SQLiteOpenHelper{
    private static final String name = "TaskList_db";
    private static final int version = 3;

    private static final String TABLE_TASKLISTS = "taskLists";
    private static final String COLUM_ID = "id";
    private static final String COLUM_NAME = "name";
    private String TAG  = "TaskListDAO";


    public TaskListsDAO(Context context) {
        super(context, name, null, version);
    }

    public void save(TaskList task) {
        ContentValues values = new ContentValues();
        values.put(COLUM_NAME, task.getName());
        getWritableDatabase().insert(TABLE_TASKLISTS, null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE "+TABLE_TASKLISTS+" ("+COLUM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " "+COLUM_NAME+" TEXT UNIQUE NOT NULL); ";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS "+TABLE_TASKLISTS+";";
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
            String field = cursor.getString(1);
            Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + cursor.getString(1));
            taskLists.setName(cursor.getString(1));
            aLotOfTaskLists.add(taskLists);
        }

        return aLotOfTaskLists;
    }
}