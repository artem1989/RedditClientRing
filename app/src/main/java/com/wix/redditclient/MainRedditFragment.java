package com.wix.redditclient;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.wix.redditclient.databinding.MainRedditFragmentBinding;
import com.wix.redditclient.di.VMFactory;

import com.wix.redditclient.model.RedditChild;
import com.wix.redditclient.model.RedditPost;
import com.wix.redditclient.viewmodels.RedditViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainRedditFragment extends DaggerFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    MainRedditFragmentBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainRedditFragmentBinding.inflate(inflater, container, false);
        RedditViewModel viewModel = ViewModelProviders.of(this, vmFactory).get(RedditViewModel.class);
        viewModel.fetchPosts().observe(this, this::updatePosts);
        return binding.getRoot();
    }

    private void updatePosts(RedditPost redditPost) {
        List<RedditChild> children = redditPost.getData().getChildren();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(new RedditPostsAdapter(children, this::onItemClick));
    }

    public void onItemClick(RedditChild item) {
        WebViewFragment details = WebViewFragment.newInstance(item.getData().getUrl());
        navigator.navigateTo(R.id.container, details, true);
//        WebAlertDialog dialog = new WebAlertDialog(getActivity());
//        dialog.initWithURL(item.getData().getUrl());
    }
}
