package com.example.seproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RandomPassword extends Activity 
{
	Button submitBtn;
	Button resendBtn;
	EditText verifyCode;
	String entered_code, sent_code;
	Bundle extras;
	String s_emailid, s_username;
	JSONParser jsonParser = new JSONParser();
	int success = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_password);
		extras = getIntent().getExtras();
		if (extras != null) {
			s_emailid = extras.getString("emailid");
			s_username = extras.getString("s_username");

		}

		submitBtn = (Button)findViewById(R.id.submit_btn);
		verifyCode = (EditText)findViewById(R.id.editText_oneTime);
		sent_code = SignUp.string_code;
		
		submitBtn.setOnClickListener(new View.OnClickListener() 
		{
		
			@Override
			public void onClick(View v) 
			{
				entered_code = verifyCode.getText().toString().trim();
				//checking the code entered with code sent by email for verification, if equal goes to the next screen
				if(entered_code.equals(sent_code))
				{
					Toast t = Toast.makeText(RandomPassword.this,"Registration Successful", Toast.LENGTH_SHORT);
					t.show();
					Intent i = new Intent(getApplicationContext(), NewPassword.class);
					i.putExtra("emailid",s_emailid);
					i.putExtra("username", s_username);
					startActivity(i);
				}
				//if incorrect, a toast appears
				else{
					Toast t = Toast.makeText(RandomPassword.this,"Incorrect password! Please try again!", Toast.LENGTH_LONG);
					t.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch_page, menu);
		return true;
	}

	@Override
	public void onBackPressed()
	{ 
		//do nothing
		finish();
	}
}

