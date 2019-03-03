package com.ring.redditclient.common;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class Utils {

    public static Fragment getTopmostVisibleFragment(@NonNull FragmentManager manager) {
        Fragment shownFragment = null;
        for (Fragment fragment : manager.getFragments()) {
            if (fragment != null && fragment.isVisible())
                shownFragment = fragment;
        }
        return shownFragment;
    }

}
