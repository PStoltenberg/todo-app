package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.Utils.DatabaseHandler;

public class AddNewTask extends AppCompatActivity {


    private EditText taskNameText;
    private EditText deadlineText;
    private EditText priorityText;;
    private Button createNewTaskButton;
    private Button cancelButton;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        getSupportActionBar().hide();

        taskNameText = findViewById(R.id.newTaskText);
        deadlineText = findViewById(R.id.newTaskDeadline);
        priorityText = findViewById(R.id.newTaskPriority);
        createNewTaskButton = findViewById(R.id.addNewTaskButton);
        cancelButton = findViewById(R.id.cancelNewTaskButton);

        db = new DatabaseHandler(this);
        db.openDatabase();

        createNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ToDoModel task = new ToDoModel();
                task.setName(taskNameText.getText().toString());
                task.setDeadline(deadlineText.getText().toString());
                task.setPriority(priorityText.getText().toString());
                task.setStatus(0);

                db.InsertTask(task);

                startActivity(new Intent(AddNewTask.this, MainActivity.class));
                //Toast.makeText(AddNewTask.this, "Opgave oprettet", Toast.LENGTH_LONG).show();

            }
        });

        cancelButton = findViewById(R.id.cancelNewTaskButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskNameText.getText().clear();
                deadlineText.getText().clear();
                priorityText.getText().clear();
                startActivity(new Intent(AddNewTask.this, MainActivity.class));
            }
        });

    }



}