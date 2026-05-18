package com.example.todo_mobile_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_mobile_app.R;
import com.example.todo_mobile_app.api.ApiService;
import com.example.todo_mobile_app.api.RetrofitClient;
import com.example.todo_mobile_app.models.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, gender, birthdate;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.usernameInput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        gender = findViewById(R.id.genderInput);
        birthdate = findViewById(R.id.birthDateInput);

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.register(new RegisterRequest(
                username.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                gender.getText().toString(),
                birthdate.getText().toString()
        )).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(RegisterActivity.this,
                            "Registered",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}