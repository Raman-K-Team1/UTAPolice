package com.example.seproject;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPassword extends ActionBarActivity {
	Button submitBtn;
	Bundle extras;
	String s_emailid=null;
	String s_username=null;
	String s_newPassword=null;
	String s_confirmPassword=null;
	private EditText newPassword, confirmPassword;
	JSONParser jsonParser = new JSONParser();
	private static final String SUCCESS = "success";
	int success = 0;
	private ProgressDialog pDialog;
	private static String url = "http://omega.uta.edu/~pvn4560/Password.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_password);
		extras = getIntent().getExtras();
		if (extras != null) {
			s_emailid = extras.getString("emailid");
			s_username = extras.getString("username");

		}

		submitBtn=(Button)findViewById(R.id.submitbtn);
        newPassword=(EditText)findViewById(R.id.editText_password);
		confirmPassword=(EditText)findViewById(R.id.editText_confirm_password);

		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//validating if the new password and the confirm password are the same and also checks if the password field is more than 6 characters
			if(newPassword.getText().toString().trim().matches(confirmPassword.getText().toString().trim())&& newPassword.length() >= 6)
			{
				new newPassword().execute();
			}
			//gives a message if the new password and the confirm password does not match
			else if (!newPassword.getText().toString().trim().matches(confirmPassword.getText().toString().trim()))
			{
				Toast.makeText(getApplicationContext(),"NewPassword and the confirm Password should be the same",Toast.LENGTH_SHORT).show();
			}
			//gives a message if the password filed is less than 6 characters
			else if (newPassword.length() < 6) {
				Toast.makeText(getApplicationContext(),"Password should be more than 6 characters",Toast.LENGTH_SHORT).show();
			}
		}
	}); 
}

class newPassword extends AsyncTask<String, String, String> {

	protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewPassword.this);
			pDialog.setMessage("Connecting...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
        //converts the entered new password and confirm password to name value pair and stores in the database 
		protected String doInBackground(String... arg0) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			s_newPassword=newPassword.getText().toString();
			s_confirmPassword=confirmPassword.getText().toString();
			nameValuePairs.add(new BasicNameValuePair("emailid", s_emailid));
			nameValuePairs.add(new BasicNameValuePair("newPassword", s_newPassword));
			System.out.println(nameValuePairs);
			JSONObject json = jsonParser.createHttpRequest(url,"POST", nameValuePairs);
			try {
				if(json!= null){
					success = json.getInt(SUCCESS);
					if (success == 1) 
					{
						return "1";
					}
					else
					{
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
            //on success, goes to the Sign in page to login 
			Intent i = new Intent(getApplicationContext(),SignIn.class);
			i.putExtra("emailid",s_emailid);
			i.putExtra("username", s_username);
			startActivity(i);
		} 
    }
}



