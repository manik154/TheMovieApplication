package com.example.a13877.themovieapplication.Model;

import java.util.List;

public class VideoTrailers<T> extends
{
private int id;
private List<T> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
