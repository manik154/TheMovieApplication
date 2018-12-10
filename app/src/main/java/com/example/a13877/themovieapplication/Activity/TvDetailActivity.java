package com.example.a13877.themovieapplication.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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


import com.example.a13877.themovieapplication.Adapter.GetSeasonListAdapter;
import com.example.a13877.themovieapplication.Adapter.ReviewListAdapter;
import com.example.a13877.themovieapplication.Model.Review;
import com.example.a13877.themovieapplication.Model.TvSeason;
import com.example.a13877.themovieapplication.Model.TvShow;
import com.example.a13877.themovieapplication.Model.TvShowGenre;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.example.a13877.themovieapplication.util.AppBarStateChangeListener;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDetailActivity extends AppCompatActivity {


    private ApiService apiService;
    private ImageView image;
    private FloatingActionButton floatingActionButton;
    private TextView homepage;
    private TextView name;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewSeasonList;
    private AppBarLayout appBarLayout;
    private ReviewListAdapter reviewListAdapter;
    private GetSeasonListAdapter getSeasonListAdapter;
    private ImageView posterpath;
    private TextView overview;
    private TextView runtime;
    private TextView voteAverage;
    private TextView no_of_epiosodes_seasons;
    private TextView genre;
    private int id;
    private TvShow tvShow;
    private TextView viewSeason;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private MenuItem item;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_televieion_shows);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        apiService = new ApiService();
        recyclerView = findViewById(R.id.recyclerViewReviewList);
        recyclerViewSeasonList = findViewById(R.id.recyclerSeasonList);
        appBarLayout = findViewById(R.id.app_bar_layout);
        getSeasonListAdapter = new GetSeasonListAdapter(getApplicationContext());
        image = findViewById(R.id.image);
        name = findViewById(R.id.titleShow);
        runtime = findViewById(R.id.showruntime);
        overview = findViewById(R.id.filmAbout);
        voteAverage = findViewById(R.id.voteAverage);
        no_of_epiosodes_seasons = findViewById(R.id.total_episode_seasons);
        homepage = findViewById(R.id.homepage);
        genre = findViewById(R.id.genre);
        floatingActionButton = findViewById(R.id.fab);
        posterpath = findViewById(R.id.posterPath);
        progressDialog = new ProgressDialog(TvDetailActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {


                if ((state.name()).equals("COLLAPSED"))
                {
                    floatingActionButton.hide();
                    item.setVisible(true);

                } else
                {
                    try {
                        item.setVisible(false);
                    } catch (Exception e) {

                    }
                }


            }
        });
        id = getIntent().getExtras().getInt("key");
        if (id != 0) {
            loadSpecificTvShowContent(id);
            loadTvshowReviewContent(id);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SimilarTvShow.class);
                intent.putExtra("IdSimilar", id);
                startActivity(intent);
            }
        });
    }

    private void loadSeasonListContent(int id) {
        apiService.getTvSeasonContent(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                TvSeason tvSeason = (TvSeason) response.body();

                if (tvSeason != null) {
                    recyclerViewSeasonList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewSeasonList.setHasFixedSize(true);
                    getSeasonListAdapter.addAll(tvSeason.getSeasons());
                    recyclerViewSeasonList.setAdapter(getSeasonListAdapter);
                } else {
                    Toast.makeText(TvDetailActivity.this, "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(TvDetailActivity.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TvDetailActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadSpecificTvShowContent(final int id) {
        loadSeasonListContent(id);
        apiService.getTvShowContent(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                tvShow = (TvShow) response.body();

                if (tvShow != null) {

                    Picasso.with(getApplicationContext())
                            .load(ApiService.IMG_URL + tvShow.getPoster_path())
                            .into(image);
                  /*  Picasso.with(getApplicationContext())
                            .load(ApiService.IMG_URL + tvShow.getPoster_path())
                            .into(posterpath);
*/
                    StringBuilder s = new StringBuilder();
                    for (int i = 0; i < tvShow.getGenres().size(); i++) {
                        s.append(tvShow.getGenres().get(i).getGenreType()).append(" ").append(",").append(" ");
                    }

                    genre.setText(s.toString());
                    name.setText(tvShow.getName());

                    if (tvShow.getRuntime().size() == 0) {
                        runtime.setText("RunTime:- " + "Not Available");

                    } else {
                        runtime.setText("RunTime:- " + String.valueOf(tvShow.getRuntime().get(0)) + " mins");

                    }

                    overview.setText(tvShow.getOverview());
                    voteAverage.setText("Average Rating:- " + String.valueOf(tvShow.getVote_average()));
                    no_of_epiosodes_seasons.setText("Total episodes:- " + "S " + tvShow.getNumber_of_seasons()
                            + " " + "E " + tvShow.getNumber_of_episodes());
                    if (tvShow.getHomepage() == null) {
                        homepage.setText("Not Available");
                    } else {
                        homepage.setText(tvShow.getHomepage());
                    }
                } else {
                    Toast.makeText(TvDetailActivity.this, "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(TvDetailActivity.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TvDetailActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadTvshowReviewContent(int id) {
        apiService.getTvShowReview(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Review review = (Review) response.body();

                if (review != null) {
                    reviewListAdapter = new ReviewListAdapter(getApplicationContext());
                    reviewListAdapter.addAll(review.getResults());

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(false);
                    progressDialog.dismiss();
                    recyclerView.setAdapter(reviewListAdapter);

                } else {
                    Toast.makeText(TvDetailActivity.this, "No Data!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(TvDetailActivity.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TvDetailActivity.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_share, menu);

        item = menu.findItem(R.id.similar);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "You Clicked Share Option", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.similar) {
            Intent intent = new Intent(TvDetailActivity.this, SimilarMovieslist.class);
            intent.putExtra("IdSimilar", id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
