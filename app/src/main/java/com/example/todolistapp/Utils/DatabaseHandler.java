package com.example.todolistapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolistapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String STATUS = "status";
    private static final String TASKNAME = "taskName";
    private static final String DEADLINE = "deadline";
    private static final String PRIORITY = "priority";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
    + TASKNAME + " TEXT, " + STATUS + " INTEGER, " + DEADLINE + " TEXT, " + PRIORITY + " TEXT)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop den gamle tabel.
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);

        // Lav tabellen igen
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTask(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null,null,null,null,null, null, null);
            if (cur != null){
                if (cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setName(cur.getString(cur.getColumnIndex(TASKNAME)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        task.setDeadline(cur.getString(cur.getColumnIndex(DEADLINE)));
                        task.setPriority(cur.getString(cur.getColumnIndex(PRIORITY)));

                        taskList.add(task);


                    }while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }

    public void InsertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASKNAME, task.getName());
        cv.put(STATUS, 0);
        cv.put(DEADLINE, task.getDeadline());
        cv.put(PRIORITY, task.getPriority());

        // indsæt opgave i Databasen
        db.insert(TODO_TABLE, null, cv);
    }

    public void updateStatusOnTask(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv,ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task, String deadline, String priority) {
        ContentValues cv = new ContentValues();
        cv.put(TASKNAME, task);
        cv.put(DEADLINE, deadline);
        cv.put(PRIORITY, priority);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});

    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}
