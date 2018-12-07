package com.example.a13877.themovieapplication.Model;

import java.util.List;

public class TvShowGenre<T>
{
    private List<T> genres;
    public List<T> getGenres() {
        return genres;
    }

    public void setGenres(List<T> genres) {
        this.genres = genres;
    }



}
