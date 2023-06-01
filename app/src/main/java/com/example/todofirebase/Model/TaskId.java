package com.example.todofirebase.Model;

import com.google.firebase.firestore.Exclude;

import org.jetbrains.annotations.NotNull;

public class TaskId {

    @Exclude
    public String TaskId;

    public <T extends TaskId> T withId(@NotNull final String id){
        this.TaskId = id;

        return (T) this;

    }
}
