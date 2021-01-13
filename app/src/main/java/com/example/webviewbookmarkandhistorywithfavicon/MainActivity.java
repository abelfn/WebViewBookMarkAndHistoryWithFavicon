package com.example.webviewbookmarkandhistorywithfavicon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMarkDBHelper;
import com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMarkInsertSetterGetter;
import com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMarkUpdateSetterGetter;
import com.example.webviewbookmarkandhistorywithfavicon.bookmark.UpdateBookMarkFavicon;
import com.example.webviewbookmarkandhistorywithfavicon.history.HistoryDBHelper;
import com.example.webviewbookmarkandhistorywithfavicon.history.UpdateHistoryFavicon;


public class MainActivity extends AppCompatActivity {

    public static WebView myWebView;
    EditText et;
    Button go,add_to_bookmarks,bookmark_and_history;
    Toolbar toolbar;
    HistoryDBHelper dbHelper;
    BookMarkDBHelper dbHelperb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et = findViewById(R.id.et);
        go = findViewById(R.id.go);

        add_to_bookmarks = findViewById(R.id.add_to_bookmarks);
        bookmark_and_history = findViewById(R.id.bookmark_and_history);

        dbHelper = new HistoryDBHelper(this);
        dbHelperb = new BookMarkDBHelper(this);

        myWebView = (WebView) findViewById(R.id.webView);

        myWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                new BookMarkInsertSetterGetter(null,view.getTitle(),view.getUrl());

                dbHelper.open();
                dbHelper.insertHistory(new BookMarkHistoryItems(null, view.getTitle(), view.getUrl()));

            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {

                UpdateHistoryFavicon task = new UpdateHistoryFavicon(view.getUrl(), view.getOriginalUrl(), icon);
                task.execute();

                new BookMarkUpdateSetterGetter(view.getUrl(),view.getOriginalUrl(),icon);

            }

        });


        myWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://www.google.com");

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWebView.loadUrl(et.getText().toString());
            }
        });


        add_to_bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelperb.open();
                dbHelperb.insertBookMarks(new BookMarkHistoryItems(BookMarkInsertSetterGetter.getFavicon(), BookMarkInsertSetterGetter.getTitle(), BookMarkInsertSetterGetter.getUrl()));

                UpdateBookMarkFavicon task = new UpdateBookMarkFavicon(BookMarkUpdateSetterGetter.getUrl(),BookMarkUpdateSetterGetter.getOriginalUrl(), BookMarkUpdateSetterGetter.getFavicon());
                task.execute();
                Toast.makeText(MainActivity.this,"Bookmark inserted",Toast.LENGTH_SHORT);

            }
        });

        bookmark_and_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BookmarkHistoryActivity.class);
                startActivity(i);

            }
        });



    }


}

