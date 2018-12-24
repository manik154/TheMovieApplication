package com.example.a13877.themovieapplication.api;

import android.util.Log;

import com.example.a13877.themovieapplication.util.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceNews
{

    private ApiInterfaceNews apiInterfaceNews;

    //https://newsapi.org/v2/top-headlines?sources=entertainment-weekly&apiKey=741d4a06cc35476395aea4a2d8b294ac

    public static final String BASE_URL_NEWS = "https://newsapi.org";


    public ApiServiceNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_NEWS)
                .client(builder())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterfaceNews = retrofit.create(ApiInterfaceNews.class);
    }

    private OkHttpClient builder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.connectTimeout(20, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
        okHttpClient.readTimeout(90, TimeUnit.SECONDS);


        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("sources", Constant.SOURCES)
                        .addQueryParameter("apiKey", Constant.API_KEY_NEWS)
                        .build();
Log.v("hope",""+url);
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public void getNews(Callback callback) {
        apiInterfaceNews.getNews().enqueue(callback);
    }

}
