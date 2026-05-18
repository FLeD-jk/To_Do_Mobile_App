package com.example.todo_mobile_app.fragments;

import java.util.List;

import com.example.todo_mobile_app.R;
import com.example.todo_mobile_app.api.ApiService;
import com.example.todo_mobile_app.api.RetrofitClient;
import com.example.todo_mobile_app.models.Task;
import com.example.todo_mobile_app.models.TaskRequest;
import com.example.todo_mobile_app.activities.TaskAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;

public class TasksFragment extends Fragment {

    RecyclerView recyclerView;
    EditText taskInput;
    Button addTaskButton;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.activity_tasks, container, false);

        recyclerView = view.findViewById(R.id.tasksRecyclerView);
        taskInput = view.findViewById(R.id.taskInput);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTasks();

        addTaskButton.setOnClickListener(v -> createTask());

        return view;
    }

    private void loadTasks() {

        SharedPreferences prefs =
                requireActivity().getSharedPreferences("app", Context.MODE_PRIVATE);

        String token = prefs.getString("token", "");

        ApiService api = RetrofitClient.getClient().create(ApiService.class);

        api.getTasks("Token " + token).enqueue(new Callback<List<Task>>() {

            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    TaskAdapter adapter = new TaskAdapter(getContext(), response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {}
        });
    }

    private void createTask() {

        String title = taskInput.getText().toString().trim();

        if (title.isEmpty()) return;

        TaskRequest task = new TaskRequest(
                title,
                "",
                false
        );

        SharedPreferences prefs =
                requireActivity().getSharedPreferences("app", Context.MODE_PRIVATE);

        String token = prefs.getString("token", "");

        ApiService api = RetrofitClient.getClient().create(ApiService.class);

        api.createTask("Token " + token, task)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        loadTasks();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
    }
}