package com.wix.redditclient.repository;

import com.wix.redditclient.model.RedditPost;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MovieApi {

    String API_KEY = "api_key";
    String MOVIE_ID = "movie_id";

    @GET("movie/popular")
    Call<List<RedditPost>> getTopPosts(@Query(API_KEY) String apiKey);

}
