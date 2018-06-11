package com.wix.redditclient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wix.redditclient.databinding.WebViewFragmentBinding;
import com.wix.redditclient.model.DecorationInfo;

import dagger.android.support.DaggerFragment;

import static com.wix.redditclient.common.Utils.isNetworkAvailable;

public class WebViewFragment extends DaggerFragment {

    private static final String ARG_URL = "url";

    private WebViewFragmentBinding binding;
    private OnDecorateToolbarlistener listener;

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnDecorateToolbarlistener) {
            listener = (OnDecorateToolbarlistener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WebViewFragmentBinding.inflate(inflater, container, false);
        binding.details.getSettings().setJavaScriptEnabled(true);
        binding.details.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressBar.setVisibility(View.GONE);
                binding.webReload.setVisibility(View.GONE);
                boolean isNetworkAvailable = isNetworkAvailable(getContext());
                if(!isNetworkAvailable){
                    binding.webReload.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                binding.webReload.setVisibility(View.VISIBLE);
            }

        });
        binding.details.loadUrl(getArguments().getString(ARG_URL));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        DecorationInfo info = new DecorationInfo();
        info.setShowBackArrow(true);
        info.setShowTitle(true);
        info.setShowTabs(false);
        listener.decorate(info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface OnDecorateToolbarlistener {
        void decorate(DecorationInfo info);
    }
}
