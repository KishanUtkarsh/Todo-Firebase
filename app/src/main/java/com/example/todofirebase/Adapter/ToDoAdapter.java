package com.example.todofirebase.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todofirebase.AddNewTask;
import com.example.todofirebase.MainActivity;
import com.example.todofirebase.Model.ToDoModel;
import com.example.todofirebase.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>  {

    private List<ToDoModel> todoList;
    private MainActivity activity;
    private FirebaseFirestore firestoreDB;

    public ToDoAdapter(MainActivity mainActivity, List<ToDoModel> todoList){

        this.todoList = todoList;
        activity = mainActivity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.each_task,parent,false);
        firestoreDB = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);

    }

    public Context getContext(){
        return activity;
    }

    public void deleteTask(int position){
        ToDoModel toDoModel = todoList.get(position);
        firestoreDB.collection("task").document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateTask(int position){
        ToDoModel toDoModel = todoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task" , toDoModel.getTask());
        bundle.putString("due" , toDoModel.getDueDate());
        bundle.putString("id" , toDoModel.TaskId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ToDoModel toDoModel = todoList.get(position);

        holder.mCheckBox.setText(toDoModel.getTask());
        holder.mDueDateTv.setText("Due On " + toDoModel.getDueDate());
        holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firestoreDB.collection("task").document(toDoModel.TaskId).update("status" , 1);
                }else{
                    firestoreDB.collection("task").document(toDoModel.TaskId).update("status" , 0);
                }
            }
        });


    }
    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.dueDateTv);
            mCheckBox = itemView.findViewById(R.id.mCheckBox);

        }
    }
}
