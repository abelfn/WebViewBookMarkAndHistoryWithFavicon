package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

import android.graphics.Bitmap;

public class BookMarkInsertSetterGetter {
    private static Bitmap favicon;
    private static String title;
    private static String url;

    public BookMarkInsertSetterGetter(Bitmap favicon, String title, String url) {
        this.favicon = favicon;
        this.title = title;
        this.url = url;
    }

    public static Bitmap getFavicon() {
        return favicon;
    }

    public static String getTitle() {
        return title;
    }

    public static String getUrl() {
        return url;
    }

}

