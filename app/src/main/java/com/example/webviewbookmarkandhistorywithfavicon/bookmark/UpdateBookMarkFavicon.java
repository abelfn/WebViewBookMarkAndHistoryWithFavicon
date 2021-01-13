package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class UpdateBookMarkFavicon extends AsyncTask<Void, Void, Void> {
	private String mUrl;
	private String mOriginalUrl;
	private Bitmap mFavicon;

	public UpdateBookMarkFavicon(String url, String originalUrl, Bitmap favicon) {
		mUrl = url;
		mOriginalUrl = originalUrl;
		mFavicon = favicon;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		BookMarkDBHelper.updateBookMarksFavicon(mUrl, mOriginalUrl, mFavicon);
		return null;
	}

}
