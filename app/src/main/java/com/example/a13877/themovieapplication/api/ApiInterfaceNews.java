package com.example.a13877.themovieapplication.api;

import com.example.a13877.themovieapplication.Model.News;
import com.example.a13877.themovieapplication.util.Constant;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterfaceNews
{
    @GET(Constant.VERSION_NEWS + "/top-headlines")
    Call<News> getNews();

}
