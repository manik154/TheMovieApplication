package com.example.a13877.themovieapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResults<T>
{

    @SerializedName("results")
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
