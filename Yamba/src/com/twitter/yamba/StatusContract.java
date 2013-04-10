package com.twitter.yamba;

import android.provider.BaseColumns;

public final class StatusContract {
	private StatusContract() {};
	
	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String RESOURCE = "statuses"; // table or path
	
	final class Column {
		public static final String ID = BaseColumns._ID;
		public static final String CREATED_AT = "yamba_created_at";
		public static final String USER = "yamba_user";
		public static final String MESSAGE = "yamba_message";
	}
}
