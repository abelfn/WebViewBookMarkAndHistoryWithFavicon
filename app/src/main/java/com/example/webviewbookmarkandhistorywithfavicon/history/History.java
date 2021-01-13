package com.example.webviewbookmarkandhistorywithfavicon.history;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.webviewbookmarkandhistorywithfavicon.BookMarkHistoryItems;
import com.example.webviewbookmarkandhistorywithfavicon.R;

import java.util.ArrayList;

import static com.example.webviewbookmarkandhistorywithfavicon.MainActivity.myWebView;

public class History extends Fragment {
    public static ListView hListView;
    public static TextView clear_all_history,empty_h;
    public HistoryDBHelper dbHelper;
    public static ArrayList<BookMarkHistoryItems> historyList = new ArrayList<BookMarkHistoryItems>();
   public static HistoryAdapter historyAdapter;

    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        hListView = rootView.findViewById(R.id.hlv);

        clear_all_history = rootView.findViewById(R.id.clear_all_history);
        empty_h = rootView.findViewById(R.id.empty_h);

        dbHelper = new HistoryDBHelper(getActivity());
        dbHelper.open();
        historyList = dbHelper.retriveAllHistory();

        historyAdapter = new HistoryAdapter(getActivity(), historyList);
        hListView.setAdapter(new HistoryAdapter(getActivity(), historyList));
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

        clear_all_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryCustomDialog cdd = new HistoryCustomDialog(getActivity());
                cdd.show();
            }
        });

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getActivity().finish();
                myWebView.loadUrl(historyList.get(position).getUrl());

            }
        });


        hListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete")
                        .setMessage("Delete this history?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // yes pressed
                                HistoryDBHelper.deleteSingleHistory(historyList.get(i).getUrl());
                                historyList.remove(i);
                                historyAdapter.notifyDataSetChanged();

                                historyAdapter = new HistoryAdapter(getActivity(), historyList);
                                hListView.setAdapter(historyAdapter);

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

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // no pressed
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true;
            }
        });

        return rootView;
    }

}
