package com.example.webviewbookmarkandhistorywithfavicon;

import android.graphics.Bitmap;

public class BookMarkHistoryItems {
    private Bitmap favicon;
    private String title;
    private String url;

    public BookMarkHistoryItems(Bitmap favicon, String title, String url) {
        this.favicon = favicon;
        this.title = title;
        this.url = url;
    }

    public Bitmap getFavicon() {
        return favicon;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}
