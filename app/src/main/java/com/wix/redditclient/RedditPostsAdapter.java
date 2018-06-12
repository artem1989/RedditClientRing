package com.wix.redditclient;

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
    private OnItemClickListener listener;

    public void setData(List<RedditChild> children) {
        posts.addAll(children);
    }

    public interface OnItemClickListener {
        void onItemClick(RedditChild item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView title;
        ImageView image;

        ViewHolder(View viewGroup) {
            super(viewGroup);
            this.itemView = viewGroup;
            this.title = viewGroup.findViewById(R.id.title);
            this.image = viewGroup.findViewById(R.id.imageView);
        }

        void bindData(final RedditChild post) {
            itemView.setOnClickListener(v -> listener.onItemClick(post));
            title.setText(post.getData().getTitle());
            Picasso.get().load(post.getData().getThumbnail()).placeholder(R.drawable.placeholder).fit()
                    .tag(itemView.getContext())
                    .into(image);

        }

    }

    RedditPostsAdapter(List<RedditChild> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RedditPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
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
