package com.example.a13877.themovieapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13877.themovieapplication.Adapter.GetSimilarMoviesAdapter;
import com.example.a13877.themovieapplication.Model.GetSimilar;
import com.example.a13877.themovieapplication.Model.GetSimilarmovies;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.example.a13877.themovieapplication.util.EndlessRecyclerOnScrollListener;

import org.w3c.dom.Text;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimilarMovieslist extends AppCompatActivity implements GetSimilarMoviesAdapter.OnImageClickedListener {
    private RecyclerView recyclerViewSimilar;
    private GetSimilarMoviesAdapter similarListAdapter;
    private int id;
    private Toolbar toolbar;
    private int page = 1;
    private int limit = 20;
    private GridLayoutManager gridLayoutManager;
    private ApiService apiService;
    private GetSimilarmovies getSimilarmovies;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView none;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_movies_layout);
        toolbar=findViewById(R.id.toolbar);

        gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
none=findViewById(R.id.none);
        swipeRefreshLayout=findViewById(R.id.refresh);
        recyclerViewSimilar = findViewById(R.id.recyclerViewSimiaList);

        apiService = new ApiService();

        toolbar.setTitle("Similar Movies");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        similarListAdapter = new GetSimilarMoviesAdapter(getApplicationContext());
        similarListAdapter.setImageClickedListener(this);

        id = getIntent().getExtras().getInt("IdSimilar");

        removeScroll();
        addScroll();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(id);
            }
        });

        if (id != 0) {
            loadSimilarMovies(id);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                loadSimilarMovies(id);
            }
        };

        recyclerViewSimilar.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void removeScroll() {
        recyclerViewSimilar.removeOnScrollListener(endlessRecyclerOnScrollListener);
    }
    public void refresh(int id) {
        if (similarListAdapter != null) {
            similarListAdapter.clear();
        }
        page = 1;
        limit = 20;
        removeScroll();
        addScroll();
        loadSimilarMovies(id);

    }
    private void loadSimilarMovies(final int id) {

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

        apiService.getSimilarMovies(id, new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                GetSimilar getSimilar = (GetSimilar) response.body();

                if (getSimilar != null)
                {
                    similarListAdapter.addAll(getSimilar.getResults());

                    recyclerViewSimilar.setLayoutManager(gridLayoutManager);
                    recyclerViewSimilar.setHasFixedSize(false);
                    recyclerViewSimilar.setAdapter(similarListAdapter);

                } else {
                    none.setVisibility(View.VISIBLE);
                }

                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(SimilarMovieslist.this, "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SimilarMovieslist.this, "Connection Error!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onItemClick(View v, int position)
    {
      getSimilarmovies=similarListAdapter.getItem(position);
        Intent intent=new Intent(SimilarMovieslist.this,MovieDetailActivity.class);
        intent.putExtra("key",getSimilarmovies.getId());
        startActivity(intent);
    }
}
