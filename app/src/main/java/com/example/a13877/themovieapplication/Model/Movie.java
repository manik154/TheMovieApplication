package com.example.a13877.themovieapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Movie {

    @SerializedName("total_results")
    private int totalResult;
    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieData> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieData> getResults() {
        return results;
    }

    public void setResults(List<MovieData> results) {
        this.results = results;
    }

    public Movie() {
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
