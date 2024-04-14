package com.example.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Adapter.ToDoAdapter;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView activeTasksRecyclerView;
    private RecyclerView finishedTasksRecycleView;
    private ToDoAdapter tasksAdapter;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;


    private Button addNewTaskButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        // De forskellige knapper på siden
        addNewTaskButton = findViewById(R.id.createNewTaskButton);
        activeTasksRecyclerView = findViewById(R.id.activeTasksRecyclerView);
        activeTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,this);
        activeTasksRecyclerView.setAdapter(tasksAdapter);

        taskList = db.getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context , AddNewTask.class);
                context.startActivity(intent);
            }
        });




    }
}