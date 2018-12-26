package com.example.a13877.themovieapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {
    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalpages;
    @SerializedName("total_results")
    private int totalresults;


    @SerializedName("results")
    private List<ReviewResultTypes> results;

    public List<ReviewResultTypes> getResults() {
        return results;
    }

    public void setResults(List<ReviewResultTypes> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public class ReviewResultTypes
    {
        private String author;
        private String content;
        private String url;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
