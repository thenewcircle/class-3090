package com.twitter.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
	}

	// UI thread
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreated");
	}

	// Worker thread
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onStarted");

		// Get the timeline
		try {
			YambaClient cloud = YambaUtils.getCloud(this);
			List<Status> timeline = cloud.getTimeline(20);
			for (Status status : timeline) {
				Log.d(TAG,
						String.format("%s: %s", status.getUser(),
								status.getMessage()));
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to get the timeline", e);
			e.printStackTrace();
		}
	}

	// UI thread
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}

}
