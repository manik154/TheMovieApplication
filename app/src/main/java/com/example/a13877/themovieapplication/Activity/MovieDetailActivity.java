package com.example.a13877.themovieapplication.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13877.themovieapplication.Adapter.GetTrialersListAdapter;
import com.example.a13877.themovieapplication.Adapter.ReviewListAdapter;
import com.example.a13877.themovieapplication.Model.MovieDetails;
import com.example.a13877.themovieapplication.Model.Review;
import com.example.a13877.themovieapplication.Model.VideoTrailers;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.example.a13877.themovieapplication.util.AppBarStateChangeListener;
import com.example.a13877.themovieapplication.util.Constant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements GetTrialersListAdapter.OnTrailerItemSelectedListener {

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
    private AppBarLayout appBarLayout;
    private ProgressDialog progressDialog;
    private TextView homepage;
    private RecyclerView recyclerViewReview;
    private ReviewListAdapter reviewListAdapter;
    private GetTrialersListAdapter getTrialersListAdapter;
    private RecyclerView recyclerViewTrailerList;
    private FloatingActionButton floatingActionButton;
    private int id;
    private Bitmap bitmap;
    private MenuItem item;
    private LinearLayoutManager linearLayoutManager;
    private MovieDetails movieDetail;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        textviewTitle = findViewById(R.id.titleFilm);
        textViewReleaseDates = findViewById(R.id.releaseDate);
        textViewOverView = findViewById(R.id.filmAbout);
        imagePoster = findViewById(R.id.image);
        runtime = findViewById(R.id.Movieruntime);
        voteAverage = findViewById(R.id.voteAverage);
        taglline = findViewById(R.id.tagline);
        homepage = (TextView) findViewById(R.id.homepage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show();
        progressDialog.setCancelable(false);

        getTrialersListAdapter = new GetTrialersListAdapter(getApplicationContext());
        getTrialersListAdapter.setOnTrailerItemSelectedListener(this);
        floatingActionButton = findViewById(R.id.fab);
linearLayoutManager=new LinearLayoutManager(getApplicationContext());
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

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(movieDetail.getOriginalTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {


                if ((state.name()).equals("COLLAPSED")) {
                    floatingActionButton.hide();
                    item.setVisible(true);

                } else {
                    try {
                        item.setVisible(false);
                    } catch (Exception e) {

                    }
                }


            }
        });

        if (id != 0)
            loadMovieDetails(id);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    private void loadReviewDetails(final int id) {

        apiService.getMovieReviews(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Review review = (Review) response.body();

                if (review != null) {
                    reviewListAdapter = new ReviewListAdapter(getApplicationContext());
                    reviewListAdapter.addAll(review.getResults());

                    recyclerViewReview.setLayoutManager(linearLayoutManager);
                    recyclerViewReview.setHasFixedSize(false);
                    recyclerViewReview.addItemDecoration(new DividerItemDecoration(getApplicationContext(),linearLayoutManager.getOrientation()));
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
                    recyclerViewTrailerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerViewTrailerList.setHasFixedSize(true);
                    getTrialersListAdapter.addAll(videoTrailers.getResults());
                    progressDialog.dismiss();
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

        loadReviewDetails(id);
        loadMovieTrialers(id);

        apiService.getMovieDetail(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                movieDetail = (MovieDetails) response.body();
                if (movieDetail != null) {

                    textviewTitle.setText(movieDetail.getOriginalTitle());
                    textViewReleaseDates.setText("Release Date:- " + movieDetail.getReleaseDate());
                    textViewOverView.setText(movieDetail.getOverview());

                    runtime.setText("RunTime:- " + String.valueOf(movieDetail.getRuntime()) + " minutes");
                    voteAverage.setText("Average Rating:- " + String.valueOf(movieDetail.getVoteAverage()));
                    taglline.setText(String.valueOf(movieDetail.getTagline()));


                    Picasso.with(MovieDetailActivity.this).load(Constant.IMG_URL + movieDetail.getPosterPath()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            assert imagePoster != null;
                            imagePoster.setImageBitmap(bitmap);
                            Palette.from(bitmap)
                                    .generate(new Palette.PaletteAsyncListener() {
                                        @Override
                                        public void onGenerated(Palette palette) {


                                            Palette.Swatch textSwatch = palette.getDominantSwatch();

                                            if (textSwatch == null) {
                                                Toast.makeText(MovieDetailActivity.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            appBarLayout.setBackgroundColor(textSwatch.getRgb());
/*
                                            titleColorText.setTextColor(textSwatch.getTitleTextColor());
                                            bodyColorText.setTextColor(textSwatch.getBodyTextColor());*/
                                        }
                                    });
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
              /*       bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.kitten);
                    Palette.from(bitmap)
                            .generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                    if (textSwatch == null) {
                                        Toast.makeText(MovieDetailActivity.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    appBarLayout.setBackgroundColor(textSwatch.getRgb());
                                }
                            });*/


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

        item = menu.findItem(R.id.similar);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Application Name")
                    .setText("http://play.google.com/store/apps/details?id=" + this.getPackageName())
                    .startChooser();
        }
        if (item.getItemId() == R.id.similar) {
            Intent intent = new Intent(MovieDetailActivity.this, SimilarMovieslist.class);
            intent.putExtra("IdSimilar", id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(View v, int position) {
        VideoTrailers.VideoTrailerContent videoTrailerContent;
        videoTrailerContent = getTrialersListAdapter.getItem(position);
        String key = videoTrailerContent.getKey();

        if (videoTrailerContent.getSite().equals("YouTube")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));
            Log.i("Video", "Video Playing....");
        }

    }
}
