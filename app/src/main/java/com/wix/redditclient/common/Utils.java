package com.wix.redditclient.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.wix.redditclient.model.RedditChild;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public static List<RedditChild> filter(List<RedditChild> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<RedditChild> filteredModelList = new ArrayList<>();
        for (RedditChild model : models) {
            final String text = model.getData().getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public static final Comparator<RedditChild> COMPARATOR = new SortedListAdapter.ComparatorBuilder<RedditChild>()
            .setOrderForModel(RedditChild.class, (a, b) -> a.getData().getTitle().compareTo(b.getData().getTitle()))
            .build();

}
