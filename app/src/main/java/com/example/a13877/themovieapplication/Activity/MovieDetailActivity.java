package com.example.a13877.themovieapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a13877.themovieapplication.Adapter.GetTrialersListAdapter;
import com.example.a13877.themovieapplication.Adapter.ReviewListAdapter;
import com.example.a13877.themovieapplication.Model.MovieDetails;
import com.example.a13877.themovieapplication.Model.Review;
import com.example.a13877.themovieapplication.Model.VideoTrailers;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.example.a13877.themovieapplication.util.Constant;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ApiService apiService;
    private TextView textviewTitle;
    private TextView textViewReleaseDates;
    private TextView textViewRating;
    private TextView textViewOverView;
    private ImageView imagePoster;
    private TextView runtime;
    private TextView voteAverage;
    private TextView taglline;
    private TextView homepage;
    private RecyclerView recyclerViewReview;
    private ReviewListAdapter reviewListAdapter;
    private GetTrialersListAdapter getTrialersListAdapter;

    private RecyclerView recyclerViewTrailerList;
    private FloatingActionButton floatingActionButton;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        textviewTitle = findViewById(R.id.titleFilm);
        textViewReleaseDates = findViewById(R.id.releaseDate);
        textViewOverView = findViewById(R.id.filmAbout);
        imagePoster = findViewById(R.id.image);
        runtime = findViewById(R.id.Movieruntime);
        voteAverage = findViewById(R.id.voteAverage);
        taglline = findViewById(R.id.tagline);
        homepage = (TextView) findViewById(R.id.homepage);

        floatingActionButton = findViewById(R.id.fab);
        recyclerViewReview = findViewById(R.id.recyclerViewReviewList);
        recyclerViewTrailerList = findViewById(R.id.recyclerViewTrailerList);

        apiService = new ApiService();
        id = getIntent().getExtras().getInt("key");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, SimilarMovieslist.class);
                intent.putExtra("IdSimilar", id);
                startActivity(intent);
            }
        });

        if (id != 0)
            loadMovieDetails(id);
        loadReviewDetails(id);

    }


    private void loadReviewDetails(final int id) {

        apiService.getMovieReviews(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Review review = (Review) response.body();

                if (review != null) {
                    reviewListAdapter = new ReviewListAdapter(getApplicationContext());
                    reviewListAdapter.addAll(review.getResults());

                    recyclerViewReview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerViewReview.setHasFixedSize(false);
                    recyclerViewReview.setAdapter(reviewListAdapter);

                } else {
                    Toast.makeText(MovieDetailActivity.this, "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(MovieDetailActivity.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadMovieTrialers(int id) {
        apiService.getVideoTrailers(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                VideoTrailers videoTrailers = (VideoTrailers) response.body();
                if (videoTrailers != null) {
                    Log.v("hope",""+videoTrailers.getResults());
                    getTrialersListAdapter=new GetTrialersListAdapter(getApplicationContext());
                    recyclerViewTrailerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerViewTrailerList.setHasFixedSize(true);
                    getTrialersListAdapter.addAll(videoTrailers.getResults());

                    recyclerViewTrailerList.setAdapter(getTrialersListAdapter);

                } else {
                    Toast.makeText(MovieDetailActivity.this, "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(MovieDetailActivity.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadMovieDetails(int id) {
        loadMovieTrialers(id);

        apiService.getMovieDetail(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                MovieDetails movieDetail = (MovieDetails) response.body();
                if (movieDetail != null) {

                    Toast.makeText(MovieDetailActivity.this, "" + movieDetail.getId(), Toast.LENGTH_SHORT).show();

                    textviewTitle.setText(movieDetail.getOriginalTitle());
                    textViewReleaseDates.setText("Release Date:- " + movieDetail.getReleaseDate());
                    textViewOverView.setText(movieDetail.getOverview());

                    runtime.setText("RunTime:- " + String.valueOf(movieDetail.getRuntime()) + " minutes");
                    voteAverage.setText("Average Rating:- " + String.valueOf(movieDetail.getVoteAverage()));
                    taglline.setText(String.valueOf(movieDetail.getTagline()));

                    Picasso.with(MovieDetailActivity.this).load(Constant.IMG_URL + movieDetail.getPosterPath())
                            .placeholder(R.drawable.kitten).into(imagePoster);
                    if (movieDetail.getHomepage() == null) {
                        homepage.setText("Not Available");
                    } else {
                        homepage.setText(movieDetail.getHomepage());
                    }

                } else {
                    Toast.makeText(MovieDetailActivity.this, "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(MovieDetailActivity.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "You Clicked Share Option", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
