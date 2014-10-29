package com.example.seproject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Activity {

	HttpResponse response;
	public static final String PREFS_NAME = "LoginPrefs";
	ProgressDialog dialog = null;
	EditText edittext_name = null;
	EditText edittext_password = null;
	Button button_login = null;
	HttpPost httppost;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	TextView textview_password = null;
	BufferedReader bufferedReader = null;
	CheckBox c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		edittext_name = (EditText) findViewById(R.id.editText_name);
		edittext_password = (EditText) findViewById(R.id.editText_password);
		button_login = (Button) findViewById(R.id.submitbtn);
		button_login.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {

				dialog = ProgressDialog.show(SignIn.this, "", 
						"Validating user...", true);
				new Thread(new Runnable() {
					public void run() {
						login();                          
					}
				}).start(); 
            }       
        });
	}

	void login(){
		try{            

			httpclient=new DefaultHttpClient();
			//Mention the URL of path where the PHP file is mounted on the server
			httppost= new HttpPost("http://129.107.232.75/test/login.php"); 
			//adding data from the SignIn form and converting them into NameValue Pairs
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username",edittext_name.getText().toString().trim()));  
			nameValuePairs.add(new BasicNameValuePair("password",edittext_password.getText().toString().trim())); 
			System.out.println(nameValuePairs);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Executing the HTTP Post Request
            response=httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response1 = httpclient.execute(httppost, responseHandler);
			System.out.println("Response : " + response); 
			runOnUiThread(new Runnable() {
				public void run() {                    
				     dialog.dismiss();
				}
			});
		    
			//if the user credentials are found in the database
            if(response1.equalsIgnoreCase("User Found")){
                 runOnUiThread(new Runnable() {
					 public void run() {
                        //the user has successfully logged in
						Toast.makeText(SignIn.this,"Login Success", Toast.LENGTH_SHORT).show();
					}
				 });
                 //when user has successfully logged in, go to Red_Button page
				 startActivity(new Intent(SignIn.this, Red_Button.class));
			}else{
				showAlert();
			}
		}catch(Exception e){
			e.printStackTrace();
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	//function to show Alert messages
	public void showAlert(){
        SignIn.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")  
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                           }
                       });                     
                AlertDialog alert = builder.create();
                alert.show();               
            }
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRestart() {
		super.onRestart();
		startActivity(new Intent(this, Red_Button.class));
	}
	
	public void onBackPressed()
	{
		finish();
	}
}