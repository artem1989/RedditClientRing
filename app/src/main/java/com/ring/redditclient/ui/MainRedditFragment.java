package com.ring.redditclient.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ring.redditclient.R;
import com.ring.redditclient.databinding.MainRedditFragmentBinding;
import com.ring.redditclient.di.VMFactory;
import com.ring.redditclient.model.RedditChild;
import com.ring.redditclient.model.RedditPost;
import com.ring.redditclient.viewmodels.RedditViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainRedditFragment extends DaggerFragment implements SearchView.OnQueryTextListener{

    private static final int OFFSET = 10;
    MainRedditFragmentBinding binding;

    private RedditPostsAdapter adapter;
    private RedditViewModel viewModel;

    @Inject
    VMFactory vmFactory;

    @Inject
    MainNavigator navigator;

    public static MainRedditFragment newInstance() {
        MainRedditFragment fragment = new MainRedditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainRedditFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this, vmFactory).get(RedditViewModel.class);
        LinearLayoutManager layoutManager = new WrapLinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        adapter = new RedditPostsAdapter(new ArrayList<>(), this::onItemClick);
        binding.recyclerView.setAdapter(adapter);
        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView recyclerView) {
                viewModel.fetchPosts(OFFSET, viewModel.getPost().getValue().getData().getAfter());
            }
        };
        binding.recyclerView.addOnScrollListener(listener);
        viewModel.getPost().observe(this, this::updateRecyclerView);
        viewModel.fetchPosts(OFFSET, null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void updateRecyclerView(RedditPost post) {
        int curSize = adapter.getItemCount();
        adapter.addData(post.getData().getChildren());
        binding.recyclerView.post(() -> adapter.notifyItemRangeInserted(curSize, adapter.getItemCount() - 1));
    }

    public void onItemClick(RedditChild item) {
        ImageViewFragment details = ImageViewFragment.newInstance(item);
        navigator.navigateTo(R.id.container, details, true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.getFilter().filter(query);
        return false;
    }
}