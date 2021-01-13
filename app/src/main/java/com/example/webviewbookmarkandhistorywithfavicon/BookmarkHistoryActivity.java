package com.example.webviewbookmarkandhistorywithfavicon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class BookmarkHistoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    PagerAdapter pagerAdapter;
    TabLayout tabLayout;
    TabItem bookmarkTabItem;
    TabItem historyTabItem;
    ViewPager viewPager;
    LinearLayout close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_history);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        tabLayout = findViewById(R.id.tabLayout);
        bookmarkTabItem = findViewById(R.id.bookmarkTabItem);
        historyTabItem = findViewById(R.id.historyTabItem);
        close = findViewById(R.id.close);

        viewPager = findViewById(R.id.pager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount()); // call the pager class
        viewPager.setAdapter(pagerAdapter); // set adapter for pager




        // do something when the tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition()); // set current tab position

                if(tab.getPosition() == 1){
//                    Toast.makeText(MainActivityy.this, "BookMark Tab Selected.", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MainActivityy.this, "History Tab Selected.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookmarkHistoryActivity.this.finish();
            }
        });
    }


}

