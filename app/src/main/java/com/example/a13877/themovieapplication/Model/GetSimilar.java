package com.example.a13877.themovieapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetSimilar {

    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalpages;
    @SerializedName("total_results")
    private int totalresults;
    @SerializedName("results")
    private List<GetSimilarmovies> results;

    public List<GetSimilarmovies> getResults() {
        return results;
    }

    public void setResults(List<GetSimilarmovies> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public int getTotalresults() {
        return totalresults;
    }

    public void setTotalresults(int totalresults) {
        this.totalresults = totalresults;
    }
}
