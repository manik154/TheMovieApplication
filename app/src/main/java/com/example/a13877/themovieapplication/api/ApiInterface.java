package com.example.a13877.themovieapplication.api;

import com.example.a13877.themovieapplication.Model.GetSimilar;
import com.example.a13877.themovieapplication.Model.Movie;
import com.example.a13877.themovieapplication.Model.MovieDetails;
import com.example.a13877.themovieapplication.Model.Review;
import com.example.a13877.themovieapplication.Model.Season;
import com.example.a13877.themovieapplication.Model.TvSeason;
import com.example.a13877.themovieapplication.Model.TvShow;
import com.example.a13877.themovieapplication.Model.VideoTrailers;
import com.example.a13877.themovieapplication.util.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    @GET(Constant.MOVIE_PATH + "/popular")
    Call<Movie> popularMovies(
            @Query("page") int page);

    @GET(Constant.MOVIE_PATH + "/top_rated")
    Call<Movie> topRatedMovies(@Query("page") int page);

    @GET(Constant.MOVIE_PATH + "/upcoming")
    Call<Movie> upcomingMovies(@Query("page") int page);

    @GET(Constant.TV_PATH + "/popular")
    Call<Movie> popularTvShows(
            @Query("page") int page);

    @GET(Constant.TV_PATH + "/top_rated")
    Call<Movie> topRatedTvShows(@Query("page") int page);

    @GET(Constant.TV_PATH + "/on_the_air")
    Call<Movie> onAirTvShows(@Query("page") int page);

    //https://api.themoviedb.org/3/tv/60735?api_key=95ffbd804001d42bb7eb88c69bf5a9cd&language=en-US
    @GET(Constant.TV_PATH + "/{tv_id}")
    Call<TvShow> tvDetail(@Path("tv_id") int tvId);

    @GET(Constant.TV_PATH + "/{tv_id}")
    Call<TvSeason> tvDetail2(@Path("tv_id") int tvId);

    @GET(Constant.TV_PATH + "/{tv_id}" + "/season" + "/{season_number}")
    Call<Season> seasonDetail(@Path("tv_id") int tvId, @Path("season_number") int season_number);

    @GET(Constant.MOVIE_PATH + "/{movie_id}")
    Call<MovieDetails> movieDetail(@Path("movie_id") int movieId);

    //https://api.themoviedb.org/3/movie/335983/reviews?api_key=95ffbd804001d42bb7eb88c69bf5a9cd&language=en-US&page=1
    @GET(Constant.MOVIE_PATH + "/{movie_id}" + Constant.REVIEWS)
    Call<Review> reviewsAbout(@Path("movie_id") int movieId);

    //https://api.themoviedb.org/3/movie/{movie_id}/images?api_key=<<api_key>>&language=en-US
    @GET(Constant.MOVIE_PATH + "/{movie_id}" + "/similar")
    Call<GetSimilar> similarMovies(@Path("movie_id") int movieId);

    //https://api.themoviedb.org/3/tv/{tv_id}/similar?api_key=<<api_key>>&language=en-US&page=1
    @GET(Constant.TV_PATH + "/{tv_id}" + "/similar")
    Call<GetSimilar> similarTvShows(@Path("tv_id") int tvId);

    //https://api.themoviedb.org/3/tv/{tv_id}/reviews?api_key=<<api_key>>&language=en-US&page=1
    @GET(Constant.TV_PATH + "/{tv_id}" + Constant.REVIEWS)
    Call<Review> getTvShowReview(@Path("tv_id") int tvId);

    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US

    @GET(Constant.MOVIE_PATH + "/{movie_id}" + Constant.VIDEOS)
    Call<VideoTrailers> getVideoTrailers(@Path("movie_id") int movieId);


    //https://api.themoviedb.org/3/genre/movie/list?api_key=95ffbd804001d42bb7eb88c69bf5a9cd&language=en-US
    @GET(Constant.GENRE_PATH + "/list")
    Call<TvShow> getGenreList();

    @GET(Constant.GENRE_PATH2 + "/list")
    Call<TvShow> getTvGenreList();


}
