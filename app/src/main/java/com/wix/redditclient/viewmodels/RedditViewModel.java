package com.wix.redditclient.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
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
        posts.addSource(repository.fetchReddit(offset, after), data -> posts.setValue(data));
        return posts;
    }

    public LiveData<RedditPost> getPosts() {
        return posts;
    }
}
