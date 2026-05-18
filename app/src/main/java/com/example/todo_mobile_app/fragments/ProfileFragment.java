package com.example.todo_mobile_app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todo_mobile_app.R;
import com.example.todo_mobile_app.api.ApiService;
import com.example.todo_mobile_app.api.RetrofitClient;
import com.example.todo_mobile_app.models.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextView usernameText, emailText, genderText, birthText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        usernameText = view.findViewById(R.id.usernameText);
        genderText = view.findViewById(R.id.genderText);
        birthText = view.findViewById(R.id.birthText);

        loadProfile();

        return view;
    }

    private void loadProfile() {

        SharedPreferences sp = requireContext()
                .getSharedPreferences("app", Context.MODE_PRIVATE);

        String token = sp.getString("token", "");

        ApiService api = RetrofitClient.getClient().create(ApiService.class);

        api.getProfile("Token " + token).enqueue(new Callback<UserProfile>() {

            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {

                    UserProfile user = response.body();

                    usernameText.setText(user.getUsername());
                    genderText.setText(user.getGender());
                    birthText.setText(user.getBirthDate());


                } else {
                    usernameText.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                usernameText.setText("Network error");
            }
        });
    }
}