package com.wix.redditclient.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.wix.redditclient.model.RedditChild;

import java.util.ArrayList;

import javax.inject.Inject;

public class FavouritesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<RedditChild>> favourites = new MutableLiveData<>();

    @Inject
    FavouritesViewModel() {
        favourites.setValue(new ArrayList<>());
    }

    public void addToFavourites(RedditChild item) {
        favourites.getValue().add(item);
    }

    public void removeFromFavourites(RedditChild item) {
        favourites.getValue().remove(item);
    }

    public LiveData<ArrayList<RedditChild>> getFavourites() {
        return favourites;
    }
}
