package com.wix.redditclient.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildInfo implements Parcelable {

    private String title;
    private String thumbnail;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(url);
    }
}
