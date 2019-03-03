package com.ring.redditclient;

import com.ring.redditclient.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class RedditClientApplication extends DaggerApplication{
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector(){
        return DaggerAppComponent.builder().application(this).build();
    }
}
