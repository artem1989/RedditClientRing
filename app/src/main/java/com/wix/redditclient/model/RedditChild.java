package com.wix.redditclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

public class RedditChild implements Parcelable, SortedListAdapter.ViewModel {

    private String kind;
    private ChildInfo data;

    protected RedditChild(Parcel in) {
        kind = in.readString();
        data = in.readParcelable(ChildInfo.class.getClassLoader());
    }

    public static final Creator<RedditChild> CREATOR = new Creator<RedditChild>() {
        @Override
        public RedditChild createFromParcel(Parcel in) {
            return new RedditChild(in);
        }

        @Override
        public RedditChild[] newArray(int size) {
            return new RedditChild[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ChildInfo getData() {
        return data;
    }

    public void setData(ChildInfo data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        if (data != null) {
            data.writeToParcel(dest, flags);
        }
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
        if (item instanceof RedditChild) {
            final RedditChild child = (RedditChild) item;
            return child.getKind() != null && child.getKind().equals(kind);
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if (item instanceof RedditChild) {
            final RedditChild other = (RedditChild) item;
            return other.getData().getTitle().equals(data.getTitle());
        }
        return false;
    }
}
