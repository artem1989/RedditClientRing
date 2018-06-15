package com.wix.redditclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;
import com.wix.redditclient.databinding.ListItemBinding;
import com.wix.redditclient.model.RedditChild;

import java.util.Comparator;
import java.util.List;


public class RedditPostsAdapter extends SortedListAdapter<RedditChild> {

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

    public interface Listener {
        void onChildClicked(RedditChild model);
    }

    private final Listener mListener;

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
}
