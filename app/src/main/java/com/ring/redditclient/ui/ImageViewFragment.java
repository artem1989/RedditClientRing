package com.ring.redditclient.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ring.redditclient.R;
import com.ring.redditclient.databinding.ImageViewFragmentBinding;
import com.ring.redditclient.di.VMFactory;
import com.ring.redditclient.model.RedditChild;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ImageViewFragment extends DaggerFragment implements View.OnClickListener {

    private static final String REDDIT_POST = "post";

    private ImageViewFragmentBinding binding;
    private RedditChild redditPost;

    @Inject
    VMFactory vmFactory;

    public static ImageViewFragment newInstance(RedditChild child) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(REDDIT_POST, child);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ImageViewFragmentBinding.inflate(inflater, container, false);
        binding.add.setOnClickListener(this);
        binding.add.setText(R.string.save_to_favourites);
        redditPost = getArguments().getParcelable(REDDIT_POST);
        Picasso.get().load(redditPost.getData().getThumbnail()).into(binding.details);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Picasso.get()
                        .load(redditPost.getData().getThumbnail())
                        .into(new ImageLoader(redditPost.getData().getTitle() + ".jpg" , binding.details));
            default:
                break;
        }
    }

}
