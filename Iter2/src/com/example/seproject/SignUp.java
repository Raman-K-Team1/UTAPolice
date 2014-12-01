package com.example.seproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	Button next,signin;
	private EditText utaid, username, emailid, emename, emenum;
	private ProgressDialog pDialog;
	int flag=0;
	JSONParser jsonParser = new JSONParser();
	private static String url = "http://omega.uta.edu/~pvn4560/signup.php";
	private static final String SUCCESS = "success";
	int success = 0;
	int email_match = 0;
	Bundle extras;
	String username1;
	public static String string_code;
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	final String emailPattern = "[a-zA-Z0-9._-]+@mavs.uta.edu";
	private static final int RANDOM_STRING_LENGTH = 6;
	String s_utaid = null;
	String s_username=null;
	String s_emailid=null;
	String s_emename=null;
	String s_emenum= null;
	String s_password = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		next=(Button)findViewById(R.id.next_btn);	
		next.setOnClickListener(new View.OnClickListener() 
		{		

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), RandomPassword.class);
				startActivity(intent);
			}
		}); 

		next=(Button)findViewById(R.id.next_btn);	
		utaid=(EditText)findViewById(R.id.editText_utaid);
		username=(EditText)findViewById(R.id.editText_name);
		emailid=(EditText)findViewById(R.id.editText_emailid);
		emename=(EditText)findViewById(R.id.editText_emename);
		emenum=(EditText)findViewById(R.id.editText_emenum);

		next.setOnClickListener(new View.OnClickListener() 
		{			

			@Override
			public void onClick(View view) {

				//validating email to match MAV'S EMAIL ID, UTA ID and Emergency contact number should be 10 characters 
				if(emailid.getText().toString().trim().matches(emailPattern) && utaid.length() == 10 && emenum.length()== 10)
				{
					email_match = 1;
					new loginAccess().execute();
				}
				else if (!emailid.getText().toString().trim().matches(emailPattern)) {
					Toast.makeText(getApplicationContext(),"Please enter a valid MAVS E-Mail ID",Toast.LENGTH_SHORT).show();
				}
				else if (utaid.length() < 10) {
					Toast.makeText(getApplicationContext(),"UTA ID should be of 10 characters",Toast.LENGTH_SHORT).show();
				}
				else if (emenum.length() < 10) {
					Toast.makeText(getApplicationContext(),"EMEGENCY CONTACT PERSON's number should be of 10 characters",Toast.LENGTH_SHORT).show();
				}
			}	
		}); 
	}

	class loginAccess extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUp.this);
			pDialog.setMessage("Signing up...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {

			//extracting and adding the data from fields in the form and converting them into NameValue Pairs
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			string_code = getRandomString();
			s_utaid = utaid.getText().toString();
			s_username=username.getText().toString();
			s_emailid=emailid.getText().toString();
			//s_password=password.getText().toString();
			s_emename=emename.getText().toString();
			s_emenum=emenum.getText().toString();

			nameValuePairs.add(new BasicNameValuePair("utaid", s_utaid));
			nameValuePairs.add(new BasicNameValuePair("username", s_username));
			nameValuePairs.add(new BasicNameValuePair("emailid", s_emailid));
			nameValuePairs.add(new BasicNameValuePair("emename", s_emename));
			nameValuePairs.add(new BasicNameValuePair("emenum", s_emenum));
			nameValuePairs.add(new BasicNameValuePair("verifyCode", string_code));
			System.out.println(nameValuePairs);
			//calling the JSONParser class to convert the NameValuePairs to JSOn format
			JSONObject json = jsonParser.createHttpRequest(url,"POST", nameValuePairs);

			try {
				if(json!= null){
					success = json.getInt(SUCCESS);
					if (success == 1) 
					{
						success = 1;
						return "1";
					}
					else
					{
						success = 0;
						return "0";
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			if (pDialog != null)
				pDialog.dismiss();
			Intent i = new Intent(getApplicationContext(),
			RandomPassword.class);
			i.putExtra("emailid",s_emailid);
			i.putExtra("username",s_username);
			startActivity(i);
		}
	}
	private String getRandomString() {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	// Generate the random number from given CHAR_LIST
	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}
}	
