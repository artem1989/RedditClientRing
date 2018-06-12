package com.wix.redditclient;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wix.redditclient.databinding.MainRedditFragmentBinding;
import com.wix.redditclient.di.VMFactory;
import com.wix.redditclient.model.DecorationInfo;
import com.wix.redditclient.model.RedditChild;
import com.wix.redditclient.model.RedditPost;
import com.wix.redditclient.viewmodels.RedditViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainRedditFragment extends DaggerFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    MainRedditFragmentBinding binding;

    private WebViewFragment.OnDecorateToolbarlistener listener;
    private RedditPostsAdapter adapter;
    private RedditViewModel viewModel;

    @Inject
    VMFactory vmFactory;

    @Inject
    MainNavigator navigator;

    public static MainRedditFragment newInstance(int sectionNumber) {
        MainRedditFragment fragment = new MainRedditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WebViewFragment.OnDecorateToolbarlistener) {
            listener = (WebViewFragment.OnDecorateToolbarlistener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainRedditFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this, vmFactory).get(RedditViewModel.class);
        viewModel.fetchPosts(25, null).observe(this, this::updatePosts);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        DecorationInfo info = new DecorationInfo();
        info.setShowBackArrow(false);
        info.setShowTitle(true);
        info.setShowTabs(true);
        listener.decorate(info);
    }

    private void updatePosts(RedditPost redditPost) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        adapter = new RedditPostsAdapter(redditPost.getData().getChildren(), this::onItemClick);
        binding.recyclerView.setAdapter(adapter);
        if (viewModel.getPosts().hasObservers()) {
            viewModel.getPosts().removeObservers(MainRedditFragment.this);
        }
        viewModel.getPosts().observe(this, this::updateRecyclerView);
        binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.fetchPosts(25, viewModel.getPosts().getValue().getData().getAfter());
            }
        });
    }

    private void updateRecyclerView(RedditPost post) {
        int curSize = adapter.getItemCount();
        adapter.setData(post.getData().getChildren());
        binding.recyclerView.post(() -> adapter.notifyItemRangeInserted(curSize, adapter.getItemCount() - 1));
    }

    public void onItemClick(RedditChild item) {
        WebViewFragment details = WebViewFragment.newInstance(item.getData().getUrl());
        navigator.navigateTo(R.id.main_content, details, true);
    }
}
