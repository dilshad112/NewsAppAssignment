package com.example.newsappassignment.utils;

import com.example.newsappassignment.model.Newsdata;
import com.example.newsappassignment.model.Newsdata2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    // base url for fatching news articles
    String BASE_URL = "https://newsapi.org/";

    // get news articles
    @GET("v2/top-headlines?country=us&apiKey=33875d68adf947db843f78c476a27200")
    Call<Newsdata2> getNews();
}
