package com.wix.redditclient.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wix.redditclient.R;

import javax.inject.Inject;

public class MainNavigator {

    private FragmentManager fragmentManager;

    @Inject
    MainNavigator(MainActivity activity) {
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    void navigateTo(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) transaction.addToBackStack(null);
        transaction
                .setCustomAnimations(R.anim.fade_in_medium, R.anim.fade_out)
                .replace(containerId, fragment)
                .commit();
    }

}
