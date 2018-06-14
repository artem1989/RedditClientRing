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
        ArrayList<RedditChild> items = favourites.getValue();
        if(items.contains(item)) {
            items.remove(item);
        } else {
            items.add(item);
        }
        favourites.setValue(items);
    }

    public LiveData<ArrayList<RedditChild>> getFavourites() {
        return favourites;
    }
}
