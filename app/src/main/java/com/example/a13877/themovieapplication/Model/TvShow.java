package com.example.a13877.themovieapplication.Model;

import java.util.List;

public class TvShow
{
    private String name;
    private String homepage;
    private int number_of_episodes;
    private int number_of_seasons;
    private String overview;
    private double vote_average;
    private String poster_path;
    private int id;

    private List<TvGenreTypes> genres;
    public List<TvGenreTypes> getGenres() {
        return genres;
    }

    public void setGenres(List<TvGenreTypes> genres) {
        this.genres = genres;
    }
    private List<Integer> episode_run_time;

    public List<Integer> getRuntime() {
        return episode_run_time;
    }

    public void setRuntime(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class TvGenreTypes
    {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGenreType() {
            return name;
        }

        public void setGenreType(String name) {
            this.name = name;
        }
    }

}
