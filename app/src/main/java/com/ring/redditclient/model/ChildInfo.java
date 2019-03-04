package com.ring.redditclient.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildInfo implements Parcelable {

    private String title;
    private String author;
    private String num_comments;
    private String thumbnail;
    private double created_utc;
    private String url;

    protected ChildInfo(Parcel in) {
        title = in.readString();
        thumbnail = in.readString();
        url = in.readString();
    }

    public static final Creator<ChildInfo> CREATOR = new Creator<ChildInfo>() {
        @Override
        public ChildInfo createFromParcel(Parcel in) {
            return new ChildInfo(in);
        }

        @Override
        public ChildInfo[] newArray(int size) {
            return new ChildInfo[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(String num_comments) {
        this.num_comments = num_comments;
    }

    public double getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(double created_utc) {
        this.created_utc = created_utc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(num_comments);
        dest.writeString(thumbnail);
        dest.writeString(url);
        dest.writeDouble(created_utc);
    }
}
