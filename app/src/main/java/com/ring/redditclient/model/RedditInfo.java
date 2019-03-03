package com.ring.redditclient.model;

import java.util.List;

public class RedditInfo {

    private String modhash;
    private int dist;
    private List<RedditChild> children;
    private String before;
    private String after;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public List<RedditChild> getChildren() {
        return children;
    }

    public void setChildren(List<RedditChild> children) {
        this.children = children;
    }
}
