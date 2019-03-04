package com.ring.redditclient.di;

import com.ring.redditclient.ui.ImageViewFragment;
import com.ring.redditclient.ui.MainRedditFragment;

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
    abstract ImageViewFragment providesWebViewFragment();
}
