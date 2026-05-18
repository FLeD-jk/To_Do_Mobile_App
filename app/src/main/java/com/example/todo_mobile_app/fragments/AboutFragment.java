package com.example.todo_mobile_app.fragments;

import com.example.todo_mobile_app.R;
import com.example.todo_mobile_app.api.ApiClient;
import com.example.todo_mobile_app.api.ApiService;
import com.example.todo_mobile_app.models.AboutResponse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutFragment extends Fragment {

    private TextView aboutName, aboutDescription;
    private ImageView aboutLogo;
    private ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.activity_about, container, false);

        aboutName = view.findViewById(R.id.aboutName);
        aboutDescription = view.findViewById(R.id.aboutDescription);
        aboutLogo = view.findViewById(R.id.aboutLogo);
        progress = view.findViewById(R.id.progress);

        loadAbout();

        return view;
    }

    private void loadAbout() {

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        progress.setVisibility(View.VISIBLE);

        Call<AboutResponse> call = apiService.getAbout();

        call.enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {

                progress.setVisibility(View.GONE);

                Log.d("ABOUT", "CODE: " + response.code());

                if (response.isSuccessful() && response.body() != null) {

                    AboutResponse data = response.body();

                    aboutName.setText(data.getName());
                    aboutDescription.setText(data.getDescription());

                    aboutName.setVisibility(View.VISIBLE);
                    aboutDescription.setVisibility(View.VISIBLE);
                    aboutLogo.setVisibility(View.VISIBLE);

                    loadImage(data.getLogo(), aboutLogo);
                }
            }

            @Override
            public void onFailure(Call<AboutResponse> call, Throwable t) {

                progress.setVisibility(View.GONE);
                Log.e("ABOUT", "ERROR", t);
            }
        });
    }

    private void loadImage(String url, ImageView imageView) {

        new Thread(() -> {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                requireActivity().runOnUiThread(() ->
                        imageView.setImageBitmap(bitmap)
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}