package com.twitter.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity {
	private EditText statusText;
	private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        statusText = (EditText) findViewById(R.id.status_text);
        updateButton = (Button) findViewById(R.id.status_button_update);
    }


}
