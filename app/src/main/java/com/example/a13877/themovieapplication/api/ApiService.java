package com.example.a13877.themovieapplication.api;

import android.util.Log;

import com.example.a13877.themovieapplication.util.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    private ApiInterface apiInterface;

    public static final String BASE_URL = Constant.BASE_URL;
    public static final String IMG_URL = Constant.IMG_URL;

    public ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    private OkHttpClient builder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.connectTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        okHttpClient.readTimeout(90, TimeUnit.SECONDS);


        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", Constant.API_KEY)
                        .addQueryParameter("language", Constant.LANG_EN)
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public void getPopularMovies(int page, Callback callback) {
        apiInterface.popularMovies(page).enqueue(callback);
    }
    public void getTopRatedMovies(int page,Callback callback) {
        apiInterface.topRatedMovies(page).enqueue(callback);
    }
    public void getUpComingMovies(int page,Callback callback) {
        apiInterface.upcomingMovies(page).enqueue(callback);
    }
    public void getPopularTvShows(int page,Callback callback)
    {
        apiInterface.popularTvShows(page).enqueue(callback);
    }
    public void getTopRatedTvShows(int page,Callback callback) {
        apiInterface.topRatedTvShows(page).enqueue(callback);
    }
    public void getOnAirTvShows(int page,Callback callback) {
        apiInterface.onAirTvShows(page).enqueue(callback);
    }
    public void getMovieDetail(int movieId, Callback callback) {
        apiInterface.movieDetail(movieId).enqueue(callback);
    }

    public void getMovieReviews(int movieId, Callback callback)
    {
        apiInterface.reviewsAbout(movieId).enqueue(callback);
    }

    public void getSimilarMovies(int movieId, Callback callback)
    {
        apiInterface.similarMovies(movieId).enqueue(callback);
    }
    public void getTvShowContent(int tvId,Callback callback) {
        apiInterface.tvDetail(tvId).enqueue(callback);
    }
    public void getTvSeasonContent(int tvId,Callback callback) {
        apiInterface.tvDetail2(tvId).enqueue(callback);
    }

    public void getseasonDetail(int tvId,int season_number,Callback callback) {
        apiInterface.seasonDetail(tvId,season_number).enqueue(callback);
    }

    public void getSimilarTvShows(int tvId,Callback callback) {
        apiInterface.similarTvShows(tvId).enqueue(callback);
    }
    public void getTvShowReview(int tvId,Callback callback) {
        apiInterface.getTvShowReview(tvId).enqueue(callback);
    }

    public void getVideoTrailers(int movieId,Callback callback) {
        apiInterface.getVideoTrailers(movieId).enqueue(callback);
    }
    public void getMovieGenreList(Callback callback) {
        apiInterface.getGenreList().enqueue(callback);
    }
    public void getTvGenreList(Callback callback) {
        apiInterface.getTvGenreList().enqueue(callback);
    }
}