
package com.example.webviewbookmarkandhistorywithfavicon.history;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class UpdateHistoryFavicon extends AsyncTask<Void, Void, Void> {
	private String mUrl;
	private String mOriginalUrl;
	private Bitmap mFavicon;
	
	public UpdateHistoryFavicon(String url, String originalUrl, Bitmap favicon) {
		mUrl = url;
		mOriginalUrl = originalUrl;
		mFavicon = favicon;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		HistoryDBHelper.updateHistoryFavicon(mUrl, mOriginalUrl, mFavicon);
		return null;
	}

}
