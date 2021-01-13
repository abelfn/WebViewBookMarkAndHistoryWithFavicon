package com.example.webviewbookmarkandhistorywithfavicon.history;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.webviewbookmarkandhistorywithfavicon.BookMarkHistoryItems;
import com.example.webviewbookmarkandhistorywithfavicon.R;

public class HistoryAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<BookMarkHistoryItems> historyList = new ArrayList<BookMarkHistoryItems>();
	LayoutInflater inflater;

	public HistoryAdapter(Context c, ArrayList<BookMarkHistoryItems> historyList) {
		mContext = c;
		this.historyList = historyList;
	}

	@Override
	public int getCount() {
		return historyList.size();

	}

	@Override
	public Object getItem(int position) {
		return historyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;

		 View result = null;

		convertView = null;
		result = convertView;
		if (convertView == null) {

			viewHolder = new ViewHolder();

			if(inflater==null){

				Context context = parent.getContext();
				inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			result = inflater.inflate(R.layout.custom_row_item, parent, false);

			viewHolder.title = (TextView) result.findViewById(R.id.title);
			viewHolder.url = (TextView) result.findViewById(R.id.url);
			viewHolder.favicon = (ImageView) result.findViewById(R.id.favicon);

			result.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) result.getTag();
		}


		if(historyList.get(position).getFavicon() == null){
			viewHolder.favicon.setImageResource(R.drawable.ic_action_web_site);
			viewHolder.favicon.setColorFilter(mContext.getResources().getColor(
					R.color.text_tertiary));
		}else {
            viewHolder.favicon.setImageBitmap(historyList.get(position).getFavicon());
		}

		viewHolder.title.setText(historyList.get(position).getTitle());
		viewHolder.url.setText(historyList.get(position).getUrl());

		return result;
	}


	private static class ViewHolder {
		ImageView favicon;
		TextView title;
		TextView url;

	}

}
