package com.example.todofirebase.Model;

public class ToDoModel extends TaskId{

    private String task , dueDate;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getStatus() {
        return status;
    }
}
