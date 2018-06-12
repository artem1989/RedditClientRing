package com.wix.redditclient.repository;

import com.wix.redditclient.model.RedditPost;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditApi {

    @GET("todayilearned/new.json")
    Call<RedditPost> getTopPosts(@Query("limit") int limit, @Query("after") String after);

}
