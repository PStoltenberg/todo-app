package com.example.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistapp.Adapter.ToDoAdapter;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.Utils.DatabaseHandler;

public class EditTask extends AppCompatActivity {


    private EditText taskNameText;
    private EditText deadlineText;
    private EditText priorityText;;
    private Button editTaskButton;
    private Button cancelButton;

    private ToDoAdapter adapter;
    private DatabaseHandler db;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        getSupportActionBar().hide();


        taskNameText = findViewById(R.id.editTaskText);
        priorityText = findViewById(R.id.editTaskPriority);
        deadlineText = findViewById(R.id.editTaskDeadline);
        editTaskButton = findViewById(R.id.editTaskButton);

        db = new DatabaseHandler(this);
        db.openDatabase();



        Bundle itemBundle = getIntent().getExtras();

        int id = itemBundle.getInt("id");
        String taskNameValue = itemBundle.getString("name");
        String deadlineValue = itemBundle.getString("deadline");
        String priorityValue = itemBundle.getString("priority");

        taskNameText.setText(taskNameValue);
        deadlineText.setText(deadlineValue);
        priorityText.setText(priorityValue);

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ToDoModel task = new ToDoModel();
                task.setId(id);
                task.setName(taskNameText.getText().toString());
                task.setDeadline(deadlineText.getText().toString());
                task.setPriority(priorityText.getText().toString());
                task.setStatus(0);

                db.updateTask(task.getId(), task.getName(), task.getDeadline(), task.getPriority());

                startActivity(new Intent(EditTask.this, MainActivity.class));

            }
        });

        cancelButton = findViewById(R.id.cancelEditTaskButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditTask.this, MainActivity.class));
                Toast.makeText(EditTask.this, "Opgaven blev ikke ændret", Toast.LENGTH_LONG).show();

            }
        });
    }
}