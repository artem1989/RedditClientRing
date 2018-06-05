package com.wix.redditclient.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.wix.redditclient.model.RedditPost;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton
public class Repository {

    private static final String TAG = Repository.class.getSimpleName();
    private static final String API_KEY = "api_key";
    private static final String APPEND_TO_RESPONSE = "append_to_response";
    private static final String VIDEOS = "videos";

    private RedditApi api;

    @Inject
    Repository(Retrofit retrofit) {
        api = retrofit.create(RedditApi.class);
    }

    public LiveData<RedditPost> fetchReddit() {
        Call<RedditPost> call = api.getTopPosts();
        MutableLiveData<RedditPost> data = new MutableLiveData<>();

        call.enqueue(new Callback<RedditPost>() {
            @Override
            public void onResponse(Call<RedditPost> call, Response<RedditPost> response) {
                if (response.body() != null)
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<RedditPost> call, Throwable t) {
                Log.e(TAG, "Network error occured! " + t.getMessage());
            }
        });

        return data;
    }

}
