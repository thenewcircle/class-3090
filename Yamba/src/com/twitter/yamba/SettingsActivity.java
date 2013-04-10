package com.twitter.yamba;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);		
		Log.d("SettingsActivity", "onCreated with bundle: "+savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// Check for inbound messages
		String message = getIntent().getStringExtra("message");
		
		if(message!=null) {
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}

}
