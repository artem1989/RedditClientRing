package com.ring.redditclient.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ring.redditclient.viewmodels.FavouritesViewModel;
import com.ring.redditclient.viewmodels.RedditViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RedditViewModel.class)
    abstract ViewModel bindsReddit(RedditViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel.class)
    abstract ViewModel bindsFavourites(FavouritesViewModel model);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(VMFactory factory);

}
