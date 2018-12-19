package com.example.a13877.themovieapplication.Model;

import java.util.List;

public class Season
{
    private String name;
    private String air_date;
    private int season_number;
    private String poster_path;
    private List<EpisodeDetails> episodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    public List<EpisodeDetails> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeDetails> episodes) {
        this.episodes = episodes;
    }
}
