package com.example.a13877.themovieapplication.Model;

import java.util.List;

public class News
{
    private String status;
    private int totalResults;
    private List<NewsList> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsList> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsList> articles) {
        this.articles = articles;
    }
}
