package com.example.todo_mobile_app.models;

import com.google.gson.annotations.SerializedName;

public class Task {

    private int id;

    private String title;

    private String description;

    @SerializedName("is_completed")
    private boolean completed;

    @SerializedName("created_at")
    private String createdAt;

    private int user;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getUser() {
        return user;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}