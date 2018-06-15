package com.wix.redditclient.di;

import com.wix.redditclient.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@SuppressWarnings("unused")
@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

}
