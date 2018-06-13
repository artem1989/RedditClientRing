package com.wix.redditclient.di;

import com.wix.redditclient.FavouritesRedditFragment;
import com.wix.redditclient.MainRedditFragment;
import com.wix.redditclient.WebViewFragment;

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
