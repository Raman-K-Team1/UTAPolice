package com.example.seproject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Password extends ActionBarActivity {
	Button button_login = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);

		button_login = (Button) findViewById(R.id.submit_btn2);
		button_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                //On clicking the SUBMIT BUTTON goes to the Red_Button page after authenticating the user
				Intent intent = new Intent(Password.this, Red_Button.class);
				startActivity(intent);
			}
		});
	}       


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Adds items to the action bar
		getMenuInflater().inflate(R.menu.password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// to handle the items selected on the action bar on clicks
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
