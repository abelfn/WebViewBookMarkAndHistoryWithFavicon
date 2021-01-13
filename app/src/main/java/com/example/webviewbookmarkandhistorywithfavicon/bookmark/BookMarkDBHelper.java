package com.example.webviewbookmarkandhistorywithfavicon.bookmark;

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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BookMarkDBHelper {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String URL = "url";
	public static final String FAVICON = "favicon";

	private DatabaseHelper mDbHelper;
	private static SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "BookMarkDB.db";
	private static final int DATABASE_VERSION = 1;

	private static final String BOOKMARKS_TABLE = "Bookmarks";

	private static final String CREATE_BOOKMARKS_TABLE = "create table "
			+ BOOKMARKS_TABLE + " (" + ID
			+ " integer primary key autoincrement, " + FAVICON
			+ " blob, " + TITLE + " text , "
			+ URL + " integer unique);";

	private final Context mCtx;

	ArrayList<BookMarkHistoryItems> bookMarkList = new ArrayList<BookMarkHistoryItems>();

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_BOOKMARKS_TABLE);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + BOOKMARKS_TABLE);
			onCreate(db);
		}
	}


	public BookMarkDBHelper(Context ctx) {
		mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);
	}


	public BookMarkDBHelper open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}


	public static void insertBookMarks(BookMarkHistoryItems bookMarks) {
		ContentValues cv = new ContentValues();
		cv.put(FAVICON, Utility.getBytes(bookMarks.getFavicon()));
		cv.put(TITLE, bookMarks.getTitle());
		cv.put(URL, bookMarks.getUrl());
		mDb.insert(BOOKMARKS_TABLE, null, cv);
	}


	private static final String UPDATE_FAVICON_WHERE_PATTERN_1 = URL + " = %s OR " + URL + " = %s";
	private static final String UPDATE_FAVICON_WHERE_PATTERN_2 = URL + " = %s";

	public static void updateBookMarksFavicon(String url, String originalUrl, Bitmap favicon) {
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
				mDb.update(BOOKMARKS_TABLE, values, whereClause, null);
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("Bookmarks", "Unable to update favicon: " + e.getMessage());
			}
		}
	}



	// To get first bookMarks
	public BookMarkHistoryItems retriveBookMarks() throws SQLException {
		Cursor cur = mDb.query(true, BOOKMARKS_TABLE, new String[] { FAVICON,
				TITLE, URL }, null, null, null, null, null, null);
		if (cur.moveToFirst()) {
			byte[] blob = cur.getBlob(cur.getColumnIndex(FAVICON));
			String title = cur.getString(cur.getColumnIndex(TITLE));
			String url = cur.getString(cur.getColumnIndex(URL));
			cur.close();
			return new BookMarkHistoryItems(Utility.getFavicon(blob), title, url);
		}

		cur.close();
		return null;
	}

	// To get list of bookMarks
	public ArrayList<BookMarkHistoryItems> retriveAllBookMarks() throws SQLException {
		Cursor cur = mDb.query(true, BOOKMARKS_TABLE, new String[] { FAVICON,
				TITLE, URL }, null, null, null, null, ID + " DESC", null);
		if (cur.moveToFirst()) {
			do {
				byte[] blob = cur.getBlob(cur.getColumnIndex(FAVICON));
				String title = cur.getString(cur.getColumnIndex(TITLE));
				String url = cur.getString(cur.getColumnIndex(URL));
				bookMarkList
						.add(new BookMarkHistoryItems(Utility.getFavicon(blob), title, url));
			} while (cur.moveToNext());
		}
		return bookMarkList;
	}


	public static void deleteAllBookMarks(){

		try{
			mDb.execSQL( "DELETE FROM "+BOOKMARKS_TABLE );

		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static void deleteSingleBookMarks(String url) {
		try {

			mDb.execSQL( "DELETE FROM "+BOOKMARKS_TABLE + " WHERE "+URL +"= '" + url + "'");
		}catch (Exception e){
			e.printStackTrace();

		}

	}

	public boolean checkif(String url){
		String qurey="Select * from " + BOOKMARKS_TABLE + " WHERE " + URL + " = '" + url + "'";
		Cursor cursor = mDb.rawQuery(qurey,null);
		if(cursor.getCount()<=0){
			cursor.close();
			return false;
		}
		cursor.close();
		return true;

	}
}
