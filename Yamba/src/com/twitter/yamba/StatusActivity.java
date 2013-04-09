package com.twitter.yamba;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity implements OnClickListener {
	private EditText statusText;
	private Button updateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		statusText = (EditText) findViewById(R.id.status_text);
		updateButton = (Button) findViewById(R.id.status_button_update);
		updateButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String status = statusText.getText().toString();
		postToTwitterTask.execute(status);
	}

	AsyncTask<String, Void, String> postToTwitterTask = new AsyncTask<String, Void, String>() {

		// Executed on a separate thread
		@Override
		protected String doInBackground(String... params) {
			try {
				YambaClient cloud = new YambaClient("student", "password");
					cloud.postStatus(params[0]);
				return "Successfully posted!";
			} catch (YambaClientException e) {
				Log.e("Yamba", "onClick", e);
				e.printStackTrace();
				return "Failed to post";
			}
		}
		
		// Executed on the UI thread
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}

	};

}
