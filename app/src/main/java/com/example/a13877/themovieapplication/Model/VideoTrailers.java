package com.example.a13877.themovieapplication.Model;

import java.util.List;

public class VideoTrailers
{
    private int id;

    private List<VideoTrailerContent> results;

    public List<VideoTrailerContent> getResults() {
        return results;
    }

    public void setResults(List<VideoTrailerContent> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
