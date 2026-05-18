package com.example.todo_mobile_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.todo_mobile_app.activities.LoginActivity;
import com.example.todo_mobile_app.fragments.AboutFragment;
import com.example.todo_mobile_app.fragments.ProfileFragment;
import com.example.todo_mobile_app.fragments.TasksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        loadFragment(new TasksFragment());

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_tasks) {
                loadFragment(new TasksFragment());

            } else if (id == R.id.nav_profile) {
                loadFragment(new ProfileFragment());

            } else if (id == R.id.nav_about) {
                loadFragment(new AboutFragment());

            } else if (id == R.id.nav_logout) {

                logoutUser();
            }

            return true;
        });
    }

    private void logoutUser() {

        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        sp.edit().clear().apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}