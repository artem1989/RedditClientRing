package com.ring.redditclient.repository;

import com.ring.redditclient.model.RedditPost;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

@Singleton
public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private RedditApi api;

    @Inject
    Repository(Retrofit retrofit) {
        api = retrofit.create(RedditApi.class);
    }

    public Observable<RedditPost> fetchReddit(int postsLimit, String after) {
        return api.getTopPosts(postsLimit, after);
    }

}
