package com.example.a13877.themovieapplication.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a13877.themovieapplication.Activity.AboutApplication;
import com.example.a13877.themovieapplication.Activity.MovieDetailActivity;
import com.example.a13877.themovieapplication.Adapter.MovieListAdapter;
import com.example.a13877.themovieapplication.Model.Movie;
import com.example.a13877.themovieapplication.Model.MovieData;
import com.example.a13877.themovieapplication.Model.MovieDetails;
import com.example.a13877.themovieapplication.R;
import com.example.a13877.themovieapplication.api.ApiService;
import com.example.a13877.themovieapplication.util.Constant;
import com.example.a13877.themovieapplication.util.EndlessRecyclerOnScrollListener;
import com.example.a13877.themovieapplication.util.GridMarginDecoration;
import com.leinardi.android.speeddial.FabWithLabelView;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesFragment extends Fragment implements MovieListAdapter.OnMovieItemSelectedListener {
    SpeedDialView mSpeedDialView;
    private RecyclerView recyclerViewMovieList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieListAdapter movieListAdapter;

    private GridLayoutManager gridLayoutManager;
    private int page = 1;
    private int limit = 20;
    int count = 0;
    private ApiService apiService;
    public MovieData movieData;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_menu_1, container, false);
        recyclerViewMovieList = view.findViewById(R.id.recyclerViewMovieList);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Movies" + " (" + "Most Popular" + ")");
        setHasOptionsMenu(true);
        movieListAdapter = new MovieListAdapter(getContext());

        recyclerViewMovieList.setLayoutManager(gridLayoutManager);
      //  recyclerViewMovieList.addItemDecoration(new GridMarginDecoration(getContext(), 1, 1, 1, 1));
        recyclerViewMovieList.setHasFixedSize(true);

        movieListAdapter.setOnMovieItemSelectedListener(this);

        recyclerViewMovieList.setAdapter(movieListAdapter);
        removeScroll();
        addScroll();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        loadData();
        initSpeedDial(view, savedInstanceState == null);
    }

    public void scrollToTop() {
        recyclerViewMovieList.smoothScrollToPosition(0);
    }

    public void refresh() {
        if (movieListAdapter != null) {
            movieListAdapter.clear();
        }
        page = 1;
        limit = 20;
        removeScroll();
        addScroll();
        loadData();

    }

    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                loadData();
            }
        };

        recyclerViewMovieList.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void removeScroll() {
        recyclerViewMovieList.removeOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void loadData() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

        apiService = new ApiService();
        if ((count == 1) || (count == 0)) {
            apiService.getPopularMovies(page, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    Movie movie = (Movie) response.body();
                    Log.v("result", "" + response.body());
                    if (movie != null) {
                        if (movieListAdapter != null) {
                            movieListAdapter.addAll(movie.getResults());
                            Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
                    }

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                    }

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else if (count == 2) {
            apiService.getUpComingMovies(page, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    Movie movie = (Movie) response.body();

                    if (movie != null) {
                        if (movieListAdapter != null) {
                            movieListAdapter.addAll(movie.getResults());
                            Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
                    }

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                    }

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else if (count == 3) {
            apiService.getTopRatedMovies(page, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    Movie movie = (Movie) response.body();

                    if (movie != null) {
                        if (movieListAdapter != null) {
                            movieListAdapter.addAll(movie.getResults());
                            Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
                    }

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                    }

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                }
            });
        }

    }

    private void initSpeedDial(View view, boolean addActionItems) {

        mSpeedDialView = view.findViewById(R.id.speedDial);

        if (addActionItems) {

            Drawable drawable = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_launcher_foreground);
            FabWithLabelView fabWithLabelView = mSpeedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id
                    .fab_top_rated, drawable)
                    .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getActivity().getTheme()))
                    .setLabel("Top Rated")
                    .setLabelColor(Color.WHITE)
                    .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark,
                            getActivity().getTheme()))
                    .create());


            if (fabWithLabelView != null) {
                fabWithLabelView.setSpeedDialActionItem(fabWithLabelView.getSpeedDialActionItemBuilder()
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary,
                                getActivity().getTheme()))
                        .create());
            }
            mSpeedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id
                    .fab_upcoming_movie, drawable)
                    .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getActivity().getTheme()))
                    .setLabel("Upcoming Movies")
                    .setLabelColor(Color.WHITE)
                    .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark,
                            getActivity().getTheme()))
                    .create());

            if (fabWithLabelView != null) {
                fabWithLabelView.setSpeedDialActionItem(fabWithLabelView.getSpeedDialActionItemBuilder()
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary,
                                getActivity().getTheme()))
                        .create());
            }
            mSpeedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id
                    .fab_most_popular, drawable)
                    .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getActivity().getTheme()))
                    .setLabel("Most Popular")
                    .setLabelColor(Color.WHITE)
                    .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark,
                            getActivity().getTheme()))
                    .create());

            if (fabWithLabelView != null) {
                fabWithLabelView.setSpeedDialActionItem(fabWithLabelView.getSpeedDialActionItemBuilder()
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary,
                                getActivity().getTheme()))
                        .create());
            }
            mSpeedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
                @Override
                public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                    switch (speedDialActionItem.getId()) {
                        case R.id.fab_most_popular:
                            count = 1;
                            getActivity().setTitle("Movies" + " (" + "Most Popular" + ")");
                            refresh();
                            Toast.makeText(getActivity(), "" + count, Toast.LENGTH_SHORT).show();
                            return false; // true to keep the Speed Dial open

                        case R.id.fab_upcoming_movie:
                            count = 2;
                            getActivity().setTitle("Movies" + " (" + "Upcoming Movies" + ")");
                            refresh();
                            Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                            return false; // true to keep the Speed Dial open

                        case R.id.fab_top_rated:
                            count = 3;
                            getActivity().setTitle("Movies" + " (" + "Top Rated" + ")");
                            refresh();
                            Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                            return false; // true to keep the Speed Dial open

                        default:
                            return false;
                    }
                }
            });
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_overflow, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scrollTop:
                scrollToTop();
                break;
            case R.id.refreshPage:

                refresh();

                break;

            case R.id.AboutApplication:
                Intent intent = new Intent(getActivity(), AboutApplication.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(View v, int position) {

        movieData=movieListAdapter.getItem(position);

        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("key",movieData.getId());
        startActivity(intent);
    }
}
