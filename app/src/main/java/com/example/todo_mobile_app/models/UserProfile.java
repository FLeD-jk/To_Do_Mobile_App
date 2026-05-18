package com.example.todo_mobile_app.models;

import com.google.gson.annotations.SerializedName;

public class UserProfile {

    private int id;

    @SerializedName("user")
    private String username;

    private String gender;

    @SerializedName("birth_date")
    private String birthDate;

    public String getUsername() { return username; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
}