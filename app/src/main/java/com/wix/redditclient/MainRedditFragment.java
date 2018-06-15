package com.wix.redditclient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.wix.redditclient.databinding.MainRedditFragmentBinding;
import com.wix.redditclient.di.VMFactory;
import com.wix.redditclient.model.DecorationInfo;
import com.wix.redditclient.model.RedditChild;
import com.wix.redditclient.model.RedditPost;
import com.wix.redditclient.viewmodels.RedditViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.wix.redditclient.common.Utils.COMPARATOR;
import static com.wix.redditclient.common.Utils.filter;

public class MainRedditFragment extends DaggerFragment implements SortedListAdapter.Callback, SearchView.OnQueryTextListener {

    private static final int OFFSET = 25;

    MainRedditFragmentBinding binding;
    private Animator mAnimator;

    private WebViewFragment.OnDecorateToolbarlistener listener;
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
        viewModel.fetchPosts(OFFSET, null).observe(this, this::initPosts);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
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

    private void initPosts(RedditPost redditPost) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        adapter = new RedditPostsAdapter(getActivity(), COMPARATOR, item -> {
            WebViewFragment details = WebViewFragment.newInstance(item);
            navigator.navigateTo(R.id.main_content, details, true);
        });
        adapter.addCallback(this);
        binding.recyclerView.setAdapter(adapter);
        adapter.edit().replaceAll(redditPost.getData().getChildren()).commit();
        if (viewModel.getPosts().hasObservers()) {
            viewModel.getPosts().removeObservers(MainRedditFragment.this);
        }
        viewModel.getPosts().observe(this, this::updateRecyclerView);
        binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.fetchPosts(OFFSET, viewModel.getPosts().getValue().getData().getAfter());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu,inflater);
    }

    private void updateRecyclerView(RedditPost post) {
        int curSize = adapter.getItemCount();
        adapter.edit().add(post.getData().getChildren()).commit();
        binding.recyclerView.post(() -> adapter.notifyItemRangeInserted(curSize,adapter.getItemCount() - 1));
    }

    @Override
    public void onEditStarted() {
        if (binding.editProgressBar.getVisibility() != View.VISIBLE) {
            binding.editProgressBar.setVisibility(View.VISIBLE);
            binding.editProgressBar.setAlpha(0.0f);
        }

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(binding.editProgressBar, View.ALPHA, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

        binding.recyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        binding.recyclerView.scrollToPosition(0);
        binding.recyclerView.animate().alpha(1.0f);

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(binding.editProgressBar, View.ALPHA, 0.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    binding.editProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mAnimator.start();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<RedditChild> filteredModelList = filter(viewModel.getPosts().getValue().getData().getChildren(), query);
        adapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        return true;
    }
}
