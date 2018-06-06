package com.wix.redditclient.model;

public class RedditChild {
    private String kind;
    private ChildInfo data;

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
}
