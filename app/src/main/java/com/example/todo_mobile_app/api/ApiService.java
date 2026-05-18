package com.example.todo_mobile_app.api;
import com.example.todo_mobile_app.models.RegisterRequest;
import com.example.todo_mobile_app.models.LoginRequest;
import com.example.todo_mobile_app.models.LoginResponse;
import com.example.todo_mobile_app.models.TaskRequest;
import com.example.todo_mobile_app.models.UserProfile;
import com.example.todo_mobile_app.models.AboutResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import com.example.todo_mobile_app.models.Task;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
public interface ApiService {

    @POST("login/")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("register/")
    Call<Void> register(@Body RegisterRequest request);

    @GET("tasks/")
    Call<List<Task>> getTasks(
            @Header("Authorization") String token
    );

    @POST("tasks/")
    Call<Void> createTask(
            @Header("Authorization") String token,
            @Body TaskRequest task
    );

    @PUT("tasks/{id}/")
    Call<Task> updateTask(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body TaskRequest request
    );

    @GET("profiles/")
    Call<UserProfile> getProfile(
            @Header("Authorization") String token
    );

    @GET("about/")
    Call<AboutResponse> getAbout();

    @DELETE("tasks/{id}/")
    Call<Void> deleteTask(
            @Path("id") int id,
            @Header("Authorization") String token
    );
}