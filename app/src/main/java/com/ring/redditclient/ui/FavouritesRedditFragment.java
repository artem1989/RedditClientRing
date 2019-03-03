package com.ring.redditclient.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ring.redditclient.R;
import com.ring.redditclient.databinding.FavouritesRedditFragmentBinding;
import com.ring.redditclient.di.VMFactory;
import com.ring.redditclient.viewmodels.FavouritesViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FavouritesRedditFragment extends DaggerFragment implements MainActivity.OnFragmentSelectedListener{

    FavouritesRedditFragmentBinding binding;
    FavouritesViewModel viewModel;
    RedditPostsAdapter adapter;

    @Inject
    VMFactory vmFactory;

    @Inject
    MainNavigator navigator;

    public static FavouritesRedditFragment newInstance() {
        FavouritesRedditFragment fragment = new FavouritesRedditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FavouritesRedditFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity(), vmFactory).get(FavouritesViewModel.class);
        adapter = new RedditPostsAdapter(viewModel.getFavourites().getValue(),  item -> {
            WebViewFragment details = WebViewFragment.newInstance(item);
            navigator.navigateTo(R.id.main_content, details, true);
        });
        binding.favourites.setHasFixedSize(true);
        binding.favourites.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.favourites.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onFragmentSelected() {
        adapter.setData(viewModel.getFavourites().getValue());
        adapter.notifyDataSetChanged();
    }
}
