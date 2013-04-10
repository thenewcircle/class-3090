package com.twitter.yamba;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment {
	private EditText statusText;
	private Button updateButton;
	private TextView textCount;
	private int defaultTextColor;
	private PostToTwitterTask postToTwitterTask;
	private YambaClient cloud;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup root,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_status, null);

		statusText = (EditText) view.findViewById(R.id.status_text);
		updateButton = (Button) view.findViewById(R.id.status_button_update);
		updateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String status = statusText.getText().toString();
				postToTwitterTask = new PostToTwitterTask();
				postToTwitterTask.execute(status);
			}
		});
		textCount = (TextView) view.findViewById(R.id.text_count);
		defaultTextColor = textCount.getTextColors().getDefaultColor();
		statusText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int count = Integer
						.parseInt(getString(R.string.status_text_counter))
						- s.length();
				textCount.setText(Integer.toString(count));
				if (count < 50) {
					textCount.setTextColor(Color.RED);
				} else {
					textCount.setTextColor(defaultTextColor);
				}
			}
		});

		return view;
	}

	class PostToTwitterTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		// Executed on the UI thread
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "Posting to the cloud",
					"Please wait...");
		}

		// Executed on a separate thread
		@Override
		protected String doInBackground(String... params) {
			try {
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
			dialog.dismiss();
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}
	};
}
