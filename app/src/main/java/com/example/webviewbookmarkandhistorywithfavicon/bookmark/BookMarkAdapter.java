package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.webviewbookmarkandhistorywithfavicon.BookMarkHistoryItems;
import com.example.webviewbookmarkandhistorywithfavicon.R;

import java.util.ArrayList;

public class BookMarkAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<BookMarkHistoryItems> bookMarkList = new ArrayList<BookMarkHistoryItems>();
	LayoutInflater inflater;

	public BookMarkAdapter(Context c, ArrayList<BookMarkHistoryItems> bookMarkList) {
		mContext = c;
		this.bookMarkList = bookMarkList;
	}

	@Override
	public int getCount() {
		return bookMarkList.size();

	}

	@Override
	public Object getItem(int position) {
		return bookMarkList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;

		 View result;

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


		if(bookMarkList.get(position).getFavicon() == null){
			viewHolder.favicon.setImageResource(R.drawable.ic_action_web_site);
			viewHolder.favicon.setColorFilter(mContext.getResources().getColor(
					R.color.text_tertiary));
		}else {
            viewHolder.favicon.setImageBitmap(bookMarkList.get(position).getFavicon());
		}

		viewHolder.title.setText(bookMarkList.get(position).getTitle());
		viewHolder.url.setText(bookMarkList.get(position).getUrl());

		return result;
	}


	private static class ViewHolder {
		ImageView favicon;
		TextView title;
		TextView url;

	}

}
