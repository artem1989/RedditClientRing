package com.wix.redditclient.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.wix.redditclient.databinding.ListItemBinding;
import com.wix.redditclient.model.RedditChild;

import java.util.Comparator;


public class RedditPostsAdapter extends SortedListAdapter<RedditChild> {

    private final Listener mListener;

    class ViewHolder extends SortedListAdapter.ViewHolder<RedditChild> {

        private final ListItemBinding mBinding;

        ViewHolder(ListItemBinding binding, RedditPostsAdapter.Listener listener) {
            super(binding.getRoot());
            binding.setListener(listener);
            mBinding = binding;
        }

        @Override
        protected void performBind(@NonNull RedditChild item) {
            mBinding.setModel(item);
        }
    }

    RedditPostsAdapter(Context context, Comparator<RedditChild> comparator, Listener listener) {
        super(context, RedditChild.class, comparator);
        mListener = listener;
    }

    @NonNull
    @Override
    protected SortedListAdapter.ViewHolder<? extends RedditChild> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ListItemBinding binding = ListItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, mListener);
    }

    public interface Listener {
        void onChildClicked(RedditChild model);
    }
}
