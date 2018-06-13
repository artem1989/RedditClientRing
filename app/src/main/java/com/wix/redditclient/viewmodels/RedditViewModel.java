package com.wix.redditclient.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.wix.redditclient.model.RedditPost;
import com.wix.redditclient.repository.Repository;

import javax.inject.Inject;

public class RedditViewModel extends AndroidViewModel {

    private Repository repository;
    private MediatorLiveData<RedditPost> posts = new MediatorLiveData<>();

    @Inject
    RedditViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public LiveData<RedditPost> fetchPosts(int offset, String after) {
        LiveData<RedditPost> response = repository.fetchReddit(offset, after);
        posts.addSource(response, data -> posts.setValue(data));
        return posts;
    }

    public MediatorLiveData<RedditPost> getPosts() {
        return posts;
    }
}
