package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.webviewbookmarkandhistorywithfavicon.R;

import static com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMark.bListView;
import static com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMark.clear_all_bookmark;
import static com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMark.bookMarkList;
import static com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMark.empty_b;
import static com.example.webviewbookmarkandhistorywithfavicon.bookmark.BookMark.bookMarkAdapter;

public class BookMarkCustomDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    TextView txt_dia;

    public BookMarkCustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        txt_dia = findViewById(R.id.txt_dia);
        txt_dia.setText("Delete all bookmark?");
        yes = findViewById(R.id.btn_yes);
        no =  findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                BookMarkDBHelper.deleteAllBookMarks();
                bookMarkList.clear();
                bookMarkAdapter.notifyDataSetChanged();

                if(bListView.getAdapter().isEmpty()){
                    clear_all_bookmark.setEnabled(false);
                    clear_all_bookmark.setTextColor(Color.parseColor("#EBE9E9"));
                    bListView.setVisibility(View.GONE);
                    empty_b.setVisibility(View.VISIBLE);
                }else{
                    clear_all_bookmark.setEnabled(true);
                    clear_all_bookmark.setTextColor(Color.parseColor("#FF0000"));
                    bListView.setVisibility(View.VISIBLE);
                    empty_b.setVisibility(View.GONE);

                }
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}