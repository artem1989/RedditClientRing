package com.ring.redditclient.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ring.redditclient.model.RedditPost;
import com.ring.redditclient.repository.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RedditViewModel extends AndroidViewModel {

    private static final String TAG = RedditViewModel.class.getSimpleName();

    private Repository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<RedditPost> posts = new MutableLiveData<>();

    @Inject
    RedditViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchPosts(int offset, String after) {
        compositeDisposable.add(repository.fetchReddit(offset, after)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts::postValue,
                        throwable -> Log.e(TAG, throwable.getMessage())));
    }

    public LiveData<RedditPost> getPost() {
        return posts;
    }
}
