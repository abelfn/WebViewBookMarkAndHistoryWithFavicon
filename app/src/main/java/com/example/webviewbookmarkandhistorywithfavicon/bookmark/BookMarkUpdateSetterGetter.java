package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

import android.graphics.Bitmap;

public class BookMarkUpdateSetterGetter {
    private static Bitmap favicon;
    private static String originalUrl;
    private static String url;

    public BookMarkUpdateSetterGetter(String url, String originalUrl, Bitmap favicon ) {
        this.url = url;
        this.originalUrl = originalUrl;
        this.favicon = favicon;
    }

    public static Bitmap getFavicon() {
        return favicon;
    }

    public static String getOriginalUrl() {
        return originalUrl;
    }

    public static String getUrl() {
        return url;
    }

}

