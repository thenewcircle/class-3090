package com.twitter.yamba;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class TimelineFragment extends ListFragment {
	private static final String[] FROM = { StatusContract.Column.USER,
			StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT };
	private static final int[] TO = { R.id.text_user, R.id.test_message, R.id.text_createdAt };
	private SimpleCursorAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Get the data: select * from statuses;
		Cursor cursor = getActivity().getContentResolver().query(
				StatusContract.CONTENT_URI, null, null, null,
				StatusContract.SORT_BY);

		// Create the adapter
		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.row, cursor, FROM, TO, 0);
		
		// Connect adapter to list
		setListAdapter(adapter);
	}

}
