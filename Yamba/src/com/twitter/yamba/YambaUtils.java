package com.twitter.yamba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.marakana.android.yamba.clientlib.YambaClient;

public class YambaUtils {

	public static YambaClient getCloud(Context context) {
		// Get the username and password
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");

		// Test if username&password are set
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			String message = "Please set your username&password";
			context.startActivity(new Intent(context, SettingsActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(
							"message", message));
			return null;
		}
		return new YambaClient(username, password);
	}
}
