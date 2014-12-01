package com.example.seproject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LaunchPage extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch_page);

		Button signUpBtn = (Button) findViewById(R.id.signupbtn);
		Button signInBtn = (Button) findViewById(R.id.signinbtn);

		signUpBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//On clicking SIGNUP button, goes to the SignUp page
				Intent intent = new Intent(LaunchPage.this, SignUp.class);
				startActivity(intent);
			}
		});

		signInBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//On Clicking the SIGNIN button goes to the SignIn Page
				Intent i = new Intent(LaunchPage.this, SignIn.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Adds items to the action bar
		getMenuInflater().inflate(R.menu.launch_page, menu);
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
