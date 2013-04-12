package com.twitter.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
	private static final String TAG = NotificationReceiver.class
			.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		int newStatusCount = intent.getIntExtra("newStatusCount", -1);
		Toast.makeText(context, "You got new " + newStatusCount + " statuses!",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "onReceived new statuses: " + newStatusCount);
	}
}
