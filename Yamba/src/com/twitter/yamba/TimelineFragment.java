package com.twitter.yamba;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimelineFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_timeline, null);
		TextView textOutput = (TextView) view.findViewById(R.id.text_output);

		// Get the data: select * from statuses;
		Cursor cursor = getActivity().getContentResolver().query(
				StatusContract.CONTENT_URI, null, null, null,
				StatusContract.SORT_BY);

		// Print it out
		final int USER_INDEX = cursor
				.getColumnIndex(StatusContract.Column.USER);
		final int MESSAGE_INDEX = cursor
				.getColumnIndex(StatusContract.Column.MESSAGE);
		while (cursor.moveToNext()) {
			String status = String.format("%s: %s",
					cursor.getString(USER_INDEX),
					cursor.getString(MESSAGE_INDEX));
			textOutput.append("\n"+status);
		}

		return view;
	}

}
