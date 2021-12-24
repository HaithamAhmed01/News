package com.example.news.api;

import com.example.news.model.NewsResponse;
import com.example.news.model.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {
    @GET("sources")
    Call<SourcesResponse> getNewsSources(@Query("apikey") String apikey);

    @GET("everything")
    Call<NewsResponse> getNewsBySourcesId(@Query("apikey") String apikey,
                                          @Query("sources") String sourcesId);
}
