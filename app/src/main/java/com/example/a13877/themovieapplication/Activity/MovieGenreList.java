package com.example.a13877.themovieapplication.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.example.a13877.themovieapplication.Adapter.GenreListAdapter;
import com.example.a13877.themovieapplication.Adapter.GetTrialersListAdapter;
import com.example.a13877.themovieapplication.Model.Movie;
import com.example.a13877.themovieapplication.Model.TvShow;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieGenreList extends AppCompatActivity {
    private ApiService apiService;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private GenreListAdapter genreListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_movies_genre_list);
        apiService = new ApiService();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Boolean value = getIntent().getBooleanExtra("isMovie", false);
        recyclerView = findViewById(R.id.recyclerViewGenreList);
        genreListAdapter = new GenreListAdapter(getApplicationContext());
        if (value) {
            getSupportActionBar().setTitle("Movie Genres");
            loadMovieGenre();
        } else {
            getSupportActionBar().setTitle("TvShow Genres");
            loadTvGenre();
        }
        setSupportActionBar(toolbar);
    }

    private void loadTvGenre() {
        apiService.getTvGenreList(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                TvShow tvShowGenre = (TvShow) response.body();
                if (tvShowGenre != null) {

                    if (genreListAdapter != null) {
                        genreListAdapter.addAll(tvShowGenre.getGenres());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(genreListAdapter);

                        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getApplicationContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void loadMovieGenre() {
        apiService.getMovieGenreList(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                TvShow tvShowGenre = (TvShow) response.body();
                Log.v("result", "" + response.body());
                if (tvShowGenre != null) {

                    Toast.makeText(MovieGenreList.this, "1", Toast.LENGTH_SHORT).show();
                    if (genreListAdapter != null) {
                        genreListAdapter.addAll(tvShowGenre.getGenres());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(genreListAdapter);

                        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getApplicationContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
