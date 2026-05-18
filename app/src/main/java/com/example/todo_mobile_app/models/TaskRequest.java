package com.example.todo_mobile_app.models;

import com.google.gson.annotations.SerializedName;

public class TaskRequest {

    private String title;

    private String description;

    @SerializedName("is_completed")
    private boolean isCompleted;

    public TaskRequest(
            String title,
            String description,
            boolean isCompleted
    ) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }
}