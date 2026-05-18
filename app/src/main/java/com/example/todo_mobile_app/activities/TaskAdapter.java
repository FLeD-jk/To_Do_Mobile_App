package com.example.todo_mobile_app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_mobile_app.R;
import com.example.todo_mobile_app.api.ApiService;
import com.example.todo_mobile_app.api.RetrofitClient;
import com.example.todo_mobile_app.models.Task;
import com.example.todo_mobile_app.models.TaskRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = tasks.get(position);

        holder.taskTitle.setText(task.getTitle());

        holder.checkBox.setChecked(task.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences sp = context.getSharedPreferences(
                    "app",
                    Context.MODE_PRIVATE
            );

            String token = sp.getString("token", "");

            ApiService api = RetrofitClient
                    .getClient()
                    .create(ApiService.class);

            TaskRequest request =
                    new TaskRequest(
                            task.getTitle(),
                            task.getDescription(),
                            isChecked
                    );

            api.updateTask(
                    task.getId(),
                    "Token " + token,
                    request
            ).enqueue(new Callback<Task>() {

                @Override
                public void onResponse(Call<Task> call, Response<Task> response) {

                    if (response.isSuccessful()) {

                        task.setCompleted(isChecked);

                    } else {

                        Toast.makeText(
                                context,
                                "Update failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t) {

                    Toast.makeText(
                            context,
                            t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });

        holder.deleteButton.setOnClickListener(v -> {

            SharedPreferences sp = context.getSharedPreferences(
                    "app",
                    Context.MODE_PRIVATE
            );

            String token = sp.getString("token", "");

            ApiService api = RetrofitClient
                    .getClient()
                    .create(ApiService.class);

            api.deleteTask(
                    task.getId(),
                    "Token " + token
            ).enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.isSuccessful()) {

                        tasks.remove(position);

                        notifyItemRemoved(position);

                        Toast.makeText(
                                context,
                                "Task deleted",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                context,
                                "Delete failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    Toast.makeText(
                            context,
                            t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        CheckBox checkBox;
        Button deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.taskTitle);
            checkBox = itemView.findViewById(R.id.taskCheckbox);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}