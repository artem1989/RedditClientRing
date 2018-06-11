package com.wix.redditclient.model;

public class DecorationInfo {

    private boolean showBackArrow;
    private boolean showTabs;
    private boolean showTitle;

    public boolean isShowBackArrow() {
        return showBackArrow;
    }

    public void setShowBackArrow(boolean showBackArrow) {
        this.showBackArrow = showBackArrow;
    }

    public boolean isShowTabs() {
        return showTabs;
    }

    public void setShowTabs(boolean showTabs) {
        this.showTabs = showTabs;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }
}
