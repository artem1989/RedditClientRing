package com.ring.redditclient.model;

public class RedditPost {
   private String kind;
   private RedditInfo data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RedditInfo getData() {
        return data;
    }

    public void setData(RedditInfo data) {
        this.data = data;
    }
}
