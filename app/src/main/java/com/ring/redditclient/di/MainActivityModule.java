package com.ring.redditclient.di;

import com.ring.redditclient.ui.FavouritesRedditFragment;
import com.ring.redditclient.ui.MainRedditFragment;
import com.ring.redditclient.ui.WebViewFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
@SuppressWarnings("unused")
public abstract class MainActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainRedditFragment providesMainRedditFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract WebViewFragment providesWebViewFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FavouritesRedditFragment providesFavouritesFragment();
}
