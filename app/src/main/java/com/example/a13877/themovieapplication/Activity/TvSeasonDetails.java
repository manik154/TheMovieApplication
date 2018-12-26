package com.example.a13877.themovieapplication.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13877.themovieapplication.Adapter.GetSeasonDetailAdapter;
import com.example.a13877.themovieapplication.Model.Season;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvSeasonDetails extends AppCompatActivity {
    private TextView titleText;
    private ImageView imageView;
    private TextView airDate;

    //  private TextView seasonNumber;
    // private TextView seasonName;
    private TextView genre;
    private TextView noOfEpisodes;

    private int count = 0;
    private int id;
    private String title;
    private int position;
    private RecyclerView recyclerViewSeasonDetails;
    private int no_of_episodes;
    private Toolbar toolbar;
    private Season season;
    private RelativeLayout relativeLayout;
    private GetSeasonDetailAdapter getSeasonDetailAdapter;
    private AppBarLayout appBarLayout;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_season_details);

        imageView = findViewById(R.id.image);
        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        titleText = findViewById(R.id.titleShow);
        //seasonNumber = findViewById(R.id.seasonNumber);
        // seasonName = findViewById(R.id.seasonName);
        noOfEpisodes = findViewById(R.id.no_of_episode);
        recyclerViewSeasonDetails = findViewById(R.id.recyclerViewSeasonList);
        relativeLayout = findViewById(R.id.relative2);
        genre = findViewById(R.id.genre);
        airDate = findViewById(R.id.airdate);

        getSeasonDetailAdapter = new GetSeasonDetailAdapter(getApplicationContext());
        recyclerViewSeasonDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSeasonDetails.setHasFixedSize(true);


        id = getIntent().getExtras().getInt("Id");
        position = getIntent().getExtras().getInt("position");
        no_of_episodes = getIntent().getExtras().getInt("totalEpisodes");
        title = getIntent().getExtras().getString("name");

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.v("hope", "" + no_of_episodes);

        apiService = new ApiService();
        getSeasonDetail(id, position);
    }

    private void getSeasonDetail(int id, int position) {

        apiService.getseasonDetail(id, position, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                season = (Season) response.body();

                if (season != null) {

                    getSeasonDetailAdapter.addAll(season.getEpisodes());
                    recyclerViewSeasonDetails.setAdapter(getSeasonDetailAdapter);

                    titleText.setText(title + " (" + season.getName() + ")");
                    noOfEpisodes.setText("Total Episodes:- " + String.valueOf(no_of_episodes));
                    airDate.setText("AirDate:- " + season.getAir_date());



                    Glide.with(getApplicationContext())
                            .load(ApiService.IMG_URL + season.getPoster_path())
                            .into(imageView);

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

}
