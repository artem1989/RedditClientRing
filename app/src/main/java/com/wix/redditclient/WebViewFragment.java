package com.wix.redditclient;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wix.redditclient.databinding.WebViewFragmentBinding;
import com.wix.redditclient.di.VMFactory;
import com.wix.redditclient.model.DecorationInfo;
import com.wix.redditclient.model.RedditChild;
import com.wix.redditclient.viewmodels.FavouritesViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WebViewFragment extends DaggerFragment implements View.OnClickListener {

    private static final String REDDIT_POST = "post";

    private WebViewFragmentBinding binding;
    private OnDecorateToolbarlistener listener;
    private FavouritesViewModel viewModel;

    @Inject
    VMFactory vmFactory;

    public static WebViewFragment newInstance(RedditChild child) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(REDDIT_POST, child);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDecorateToolbarlistener) {
            listener = (OnDecorateToolbarlistener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WebViewFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity(), vmFactory).get(FavouritesViewModel.class);
        binding.add.setOnClickListener(this);
        viewModel.getFavourites().observe(getActivity(), this::updateButton);
        binding.details.getSettings().setJavaScriptEnabled(true);
        binding.details.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binding.details.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        RedditChild item = getArguments().getParcelable(REDDIT_POST);
        binding.details.loadUrl(item.getData().getUrl());
        return binding.getRoot();
    }

    private void updateButton(ArrayList<RedditChild> children) {
        boolean isFavourite = children.contains(getArguments().getParcelable(REDDIT_POST));
        binding.add.setText(isFavourite ? R.string.remove_from_favourites : R.string.save_to_favourites);
    }

    @Override
    public void onResume() {
        super.onResume();
        DecorationInfo info = new DecorationInfo();
        info.setShowBackArrow(true);
        info.setShowTitle(true);
        listener.decorate(info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                viewModel.addToFavourites(getArguments().getParcelable(REDDIT_POST));
            default:
                break;
        }
    }

    public interface OnDecorateToolbarlistener {
        void decorate(DecorationInfo info);
    }
}
