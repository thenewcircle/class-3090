package com.twitter.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/*
 *  API examples:
 *  content://com.twitter.yamba/statuses		-> returns all the statuses
 *  content://com.twitter.yamba/statuses/47	-> returns status with id=47
 *  
 *  Not supported:
 *  content://com.twitter.yamba/statuses/search/beaver
 *  content://com.twitter.yamba/users/beaver
 *  content://com.twitter.ads/partners
 *  content://com.twitter.ads/partners/47
 *  content://com.twitter.internal/timesheets
 *  
 */
public class StatusProvider extends ContentProvider {
	public static final String TAG = "StatusProvider";
	private DbHelper dbHelper;

	private static final UriMatcher matcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		matcher.addURI(StatusContract.AUTHORITY, "statuses",
				StatusContract.STATUSES);
		matcher.addURI(StatusContract.AUTHORITY, "statuses/#",
				StatusContract.STATUSES_ID);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return (dbHelper != null) ? true : false;
	}

	// Returns the MIME type of the uri
	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case StatusContract.STATUSES:
			return StatusContract.TYPE_STATUSES;
		case StatusContract.STATUSES_ID:
			return StatusContract.TYPE_STATUS;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Assert
		if (matcher.match(uri) != StatusContract.STATUSES) {
			throw new IllegalArgumentException("Path not allowed: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		long id = db.insertWithOnConflict(StatusContract.RESOURCE, null,
				values, SQLiteDatabase.CONFLICT_IGNORE);
		if (id != -1) {
			Log.d(TAG,
					"inserted id: "
							+ values.getAsLong(StatusContract.Column.ID));
		}
		return ContentUris.withAppendedId(uri,
				values.getAsLong(StatusContract.Column.ID));
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String where;

		// Match
		switch (matcher.match(uri)) {
		case StatusContract.STATUSES:
			where = selection;
			break;
		case StatusContract.STATUSES_ID:
			long id = ContentUris.parseId(uri);
			where = String.format(" %s=%d ", StatusContract.Column.ID, id);
			if (!TextUtils.isEmpty(selection))
				where = "(" + selection + ") and " + where;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// Get the DB
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// update from db
		int records = db.update(StatusContract.RESOURCE, values, where,
				selectionArgs);

		Log.d(TAG, "deleted records: " + records);

		return records;
	}

	// DELETE from statuses WHERE _id = 47;
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String where;

		// Match
		switch (matcher.match(uri)) {
		case StatusContract.STATUSES:
			where = selection;
			break;
		case StatusContract.STATUSES_ID:
			long id = ContentUris.parseId(uri);
			where = String.format(" %s=%d ", StatusContract.Column.ID, id);
			if (!TextUtils.isEmpty(selection))
				where = "(" + selection + ") and " + where;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// Get the DB
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// delete from db
		int records = db.delete(StatusContract.RESOURCE, where, selectionArgs);

		Log.d(TAG, "deleted records: " + records);

		return records;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
