package com.ring.redditclient.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ring.redditclient.R;
import com.ring.redditclient.model.RedditChild;

import java.util.ArrayList;
import java.util.List;


public class RedditPostsAdapter extends RecyclerView.Adapter<RedditPostsAdapter.ViewHolder> implements Filterable {

    private List<RedditChild> posts;
    private List<RedditChild> filteredPosts;
    private OnItemClickListener listener;

    RedditPostsAdapter(List<RedditChild> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.filteredPosts = posts;
        this.listener = listener;
    }

    public void addData(List<RedditChild> children) {
        posts.addAll(children);
    }

    public void setData(List<RedditChild> children) {
        posts = children;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                final List<RedditChild> filteredModelList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredPosts = posts;
                } else {
                    for (RedditChild model : posts) {
                        final String text = model.getData().getTitle().toLowerCase();
                        if (text.contains(query.toLowerCase())) {
                            filteredModelList.add(model);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filteredModelList.size();
                results.values = filteredModelList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredPosts = (List<RedditChild>) results.values;
                notifyDataSetChanged();
            }
        };
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

    public interface OnItemClickListener {
        void onItemClick(RedditChild item);
    }

    @NonNull
    @Override
    public RedditPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(filteredPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredPosts.size();
    }
}
