package com.wix.redditclient.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.wix.redditclient.viewmodels.RedditViewModel;

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
    abstract ViewModelProvider.Factory bindsViewModelFactory(VMFactory factory);

}
