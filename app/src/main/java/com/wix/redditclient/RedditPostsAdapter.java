package com.wix.redditclient;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wix.redditclient.model.RedditChild;

import java.util.List;


public class RedditPostsAdapter extends RecyclerView.Adapter<RedditPostsAdapter.ViewHolder> {

    private List<RedditChild> posts;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        ViewHolder(View viewGroup) {
            super(viewGroup);
            this.title = viewGroup.findViewById(R.id.title);
            this.image = viewGroup.findViewById(R.id.imageView);
        }

        void bindData(final RedditChild post) {
            title.setText(post.getData().getTitle());
            Picasso.get().load(post.getData().getThumbnail())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit()
                    .tag(context)
                    .into(image);
        }
    }

    RedditPostsAdapter(List<RedditChild> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public RedditPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
