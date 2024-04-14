package com.example.todolistapp.Adapter;

import static android.content.ClipData.newIntent;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.AddNewTask;
import com.example.todolistapp.EditTask;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.R;
import com.example.todolistapp.Utils.DatabaseHandler;

import org.w3c.dom.Text;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> toDoList;
    private MainActivity mainActivity;
    private DatabaseHandler db;

    // constructor
    public ToDoAdapter(DatabaseHandler db, MainActivity mainActivity){

        this.db = db;
        this.mainActivity = mainActivity;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.todoNameText.setText(item.getName());
        holder.todoPriorityText.setText(item.getPriority());
        holder.todoDeadlineText.setText(item.getDeadline());

        holder.todoCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    db.updateStatusOnTask(item.getId(),1);
                    holder.todoNameText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                }
                else{
                    db.updateStatusOnTask(item.getId(),0);
                    holder.todoNameText.setPaintFlags(0);
                }
            }
        });


        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToDoModel item = toDoList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("name", item.getName());
                bundle.putString("deadline", item.getDeadline());
                bundle.putString("priority", item.getPriority());


                Context context = v.getContext();
                Intent intent = new Intent(context , EditTask.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Slet opgave");
                builder.setMessage("Er du sikker på du vil slette denne opgaven?");
                builder.setPositiveButton("Slet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    // hvad gør den her??? - find ud af det.
    private boolean toBoolean(int n){
        return n!=0;
    }

    public int getItemCount(){
        return toDoList.size();
    }

    public void setTasks(List<ToDoModel> toDoList ){
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext(){
        return mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox todoCheckBox;
        TextView todoNameText;
        TextView todoPriorityText;
        TextView todoDeadlineText;
        Button editButton;
        Button deleteButton;


        ViewHolder(View view){
            super(view);
            todoCheckBox = view.findViewById(R.id.todoCheckBox);
            todoNameText = view.findViewById(R.id.todoNameText);
            todoPriorityText = view.findViewById(R.id.todoPriorityText);
            todoDeadlineText = view.findViewById(R.id.todoDeadlineText);
            editButton = view.findViewById((R.id.todoEditButton));
            deleteButton = view.findViewById(R.id.todoDeleteButton);


        }
    }
}
