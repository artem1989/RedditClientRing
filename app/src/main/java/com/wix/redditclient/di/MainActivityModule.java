package com.wix.redditclient.di;

import com.wix.redditclient.ui.FavouritesRedditFragment;
import com.wix.redditclient.ui.MainRedditFragment;
import com.wix.redditclient.ui.WebViewFragment;

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
