package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

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
import com.example.webviewbookmarkandhistorywithfavicon.BookmarkHistoryActivity;
import com.example.webviewbookmarkandhistorywithfavicon.R;

import java.util.ArrayList;

import static com.example.webviewbookmarkandhistorywithfavicon.MainActivity.myWebView;

public class BookMark extends Fragment {
    public static ListView bListView;
    public static TextView clear_all_bookmark,empty_b;
    public BookMarkDBHelper DbHelper;
    public static ArrayList<BookMarkHistoryItems> bookMarkList = new ArrayList<BookMarkHistoryItems>();
    public static BookMarkAdapter bookMarkAdapter;

    public BookMark() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

        bListView = rootView.findViewById(R.id.blv);

        empty_b = rootView.findViewById(R.id.empty_b);
        clear_all_bookmark = rootView.findViewById(R.id.clear_all_bookmark);

        DbHelper = new BookMarkDBHelper(getActivity());
        DbHelper.open();
        bookMarkList = DbHelper.retriveAllBookMarks();

        bookMarkAdapter = new BookMarkAdapter(getActivity(), bookMarkList);

        bListView.setAdapter(bookMarkAdapter);

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


        clear_all_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookMarkCustomDialog cdd = new BookMarkCustomDialog(getActivity());
                cdd.show();

            }
        });


        bListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getActivity().finish();
                myWebView.loadUrl(bookMarkList.get(position).getUrl());

            }
        });


        bListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete")
                        .setMessage("Delete this bookmark?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // yes pressed
                                BookMarkDBHelper.deleteSingleBookMarks(bookMarkList.get(i).getUrl());
                                bookMarkList.remove(i);
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
