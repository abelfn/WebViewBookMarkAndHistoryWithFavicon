package com.example.webviewbookmarkandhistorywithfavicon.history;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.webviewbookmarkandhistorywithfavicon.R;

import static com.example.webviewbookmarkandhistorywithfavicon.history.History.clear_all_history;
import static com.example.webviewbookmarkandhistorywithfavicon.history.History.historyList;
import static com.example.webviewbookmarkandhistorywithfavicon.history.History.empty_h;
import static com.example.webviewbookmarkandhistorywithfavicon.history.History.hListView;
import static com.example.webviewbookmarkandhistorywithfavicon.history.History.historyAdapter;

public class HistoryCustomDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    TextView txt_dia;

    public HistoryCustomDialog(Activity a) {
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
        txt_dia.setText("Delete all history?");
        yes = findViewById(R.id.btn_yes);
        no =  findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                HistoryDBHelper.deleteAllHistory();
                historyList.clear();
                historyAdapter.notifyDataSetChanged();

                if(hListView.getAdapter().isEmpty()){
                    clear_all_history.setEnabled(false);
                    clear_all_history.setTextColor(Color.parseColor("#EBE9E9"));
                    hListView.setVisibility(View.GONE);
                    empty_h.setVisibility(View.VISIBLE);
                }else{
                    clear_all_history.setEnabled(true);
                    clear_all_history.setTextColor(Color.parseColor("#FF0000"));
                    hListView.setVisibility(View.VISIBLE);
                    empty_h.setVisibility(View.GONE);

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