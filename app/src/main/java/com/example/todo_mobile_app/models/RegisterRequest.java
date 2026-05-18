package com.example.todo_mobile_app.models;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String gender;
    private String birth_date;

    public RegisterRequest(
            String username,
            String email,
            String password,
            String gender,
            String birth_date
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birth_date = birth_date;
    }
}