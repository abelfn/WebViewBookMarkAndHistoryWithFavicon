package com.example.webviewbookmarkandhistorywithfavicon.history;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.webviewbookmarkandhistorywithfavicon.BookMarkHistoryItems;
import com.example.webviewbookmarkandhistorywithfavicon.Utility;

public class HistoryDBHelper {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String URl = "url";
	public static final String FAVICON = "photo";

	private DatabaseHelper mDbHelper;
	private static SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "HistoryDB.db";
	private static final int DATABASE_VERSION = 1;

	private static final String HISTORY_TABLE = "history";

	private static final String CREATE_HISTORY_TABLE = "create table "
			+ HISTORY_TABLE + " (" + ID
			+ " integer primary key autoincrement, " + FAVICON
			+ " blob, " + TITLE + " text , "
			+ URl + " integer unique);";

	private final Context mCtx;

	ArrayList<BookMarkHistoryItems> historyList = new ArrayList<BookMarkHistoryItems>();

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_HISTORY_TABLE);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
			onCreate(db);
		}
	}


	public HistoryDBHelper(Context ctx) {
		mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);
	}

	public HistoryDBHelper open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public static void insertHistory(BookMarkHistoryItems histories) {
		ContentValues cv = new ContentValues();
		cv.put(FAVICON, Utility.getBytes(histories.getFavicon()));
		cv.put(TITLE, histories.getTitle());
		cv.put(URl, histories.getUrl());
		mDb.insert(HISTORY_TABLE, null, cv);
	}

	private static final String UPDATE_FAVICON_WHERE_PATTERN_1 = URl + " = %s OR " + URl + " = %s";
	private static final String UPDATE_FAVICON_WHERE_PATTERN_2 = URl + " = %s";

	public static void updateHistoryFavicon(String url, String originalUrl, Bitmap favicon) {
		if ((url != null) &&
				(favicon != null)
				) {
			String whereClause;

			if ((originalUrl != null) &&
					!url.equals(originalUrl)) {
				url = DatabaseUtils.sqlEscapeString(url);
				originalUrl = DatabaseUtils.sqlEscapeString(originalUrl);

				whereClause = String.format(UPDATE_FAVICON_WHERE_PATTERN_1, url, originalUrl);
			} else {
				url = DatabaseUtils.sqlEscapeString(url);
				whereClause = String.format(UPDATE_FAVICON_WHERE_PATTERN_2, url);
			}

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			favicon.compress(Bitmap.CompressFormat.PNG, 100, os);

			ContentValues values = new ContentValues();
			values.put(FAVICON, os.toByteArray());

			try {
				mDb.update(HISTORY_TABLE, values, whereClause, null);
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("History", "Unable to update favicon: " + e.getMessage());
			}
		}
	}



	// To get first History
	public BookMarkHistoryItems retriveEmpDetails() throws SQLException {
		Cursor cur = mDb.query(true, HISTORY_TABLE, new String[] { FAVICON,
				TITLE, URl }, null, null, null, null, null, null);
		if (cur.moveToFirst()) {
			byte[] blob = cur.getBlob(cur.getColumnIndex(FAVICON));
			String title = cur.getString(cur.getColumnIndex(TITLE));
			String url = cur.getString(cur.getColumnIndex(URl));
			cur.close();
			return new BookMarkHistoryItems(Utility.getFavicon(blob), title, url);
		}

		cur.close();
		return null;
	}

	// To get list of History
	public ArrayList<BookMarkHistoryItems> retriveAllHistory() throws SQLException {
		Cursor cur = mDb.query(true, HISTORY_TABLE, new String[] { FAVICON,
				TITLE, URl }, null, null, null, null, ID + " DESC", null);
		if (cur.moveToFirst()) {
			do {
				byte[] blob = cur.getBlob(cur.getColumnIndex(FAVICON));
				String title = cur.getString(cur.getColumnIndex(TITLE));
				String url = cur.getString(cur.getColumnIndex(URl));
				historyList
						.add(new BookMarkHistoryItems(Utility.getFavicon(blob), title, url));
			} while (cur.moveToNext());
		}
		return historyList;
	}



	public static void deleteAllHistory(){

		try{
			mDb.execSQL( "DELETE FROM "+HISTORY_TABLE );

		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static void deleteSingleHistory(String url) {
		try {

			mDb.execSQL( "DELETE FROM "+HISTORY_TABLE + " WHERE "+URl +"= '" + url + "'");
		}catch (Exception e){
			e.printStackTrace();

		}

	}

}
