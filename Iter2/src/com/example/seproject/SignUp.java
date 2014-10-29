package com.example.seproject;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
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
	private static String url = "http://129.107.232.75/test/signup.php";
	private static final String SUCCESS = "success"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
	
	
		next=(Button)findViewById(R.id.next_btn);	
        next.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), Password.class);
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
				
		        //Validating all fields		
				if(utaid.length()!=10)
                {
                    Toast.makeText(SignUp.this,"Enter the valid UTAID", Toast.LENGTH_LONG).show();
                    return;
                }
                if(username.length()==0)
                {                
                    Toast.makeText(SignUp.this,"Enter the username", Toast.LENGTH_LONG).show();
                    return;
                }
                 if(emailid.length()==0)
                {                
                    Toast.makeText(SignUp.this,"Enter the email id", Toast.LENGTH_LONG).show();
                    return;
                } 
                 if(emename.length()==0)
                {                
                    Toast.makeText(SignUp.this,"Enter the emergency contact person name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(emenum.length()!=10)
                {                
                    Toast.makeText(SignUp.this,"Enter the valid emergency contact person number", Toast.LENGTH_LONG).show();
                    return;
                }
		    new loginAccess().execute();
			}	
	 }); 
}

class loginAccess extends AsyncTask<String, String, String> {

	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(SignUp.this);
		pDialog.setMessage("Sign in...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	@Override
	protected String doInBackground(String... arg0) {
		
		//extracting and adding the data from fields in the form and converting them into NameValue Pairs
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		String s_utaid = utaid.getText().toString();
		String s_username=username.getText().toString();
		String s_emailid=emailid.getText().toString();
		String s_emename=emename.getText().toString();
		String s_emenum=emenum.getText().toString();
		
		nameValuePairs.add(new BasicNameValuePair("utaid", s_utaid));
		nameValuePairs.add(new BasicNameValuePair("username", s_username));
		nameValuePairs.add(new BasicNameValuePair("emailid", s_emailid));
		nameValuePairs.add(new BasicNameValuePair("emename", s_emename));
		nameValuePairs.add(new BasicNameValuePair("emenum", s_emenum));
		
		//calling the JSONParser class to convert the NameValuePairs to JSOn format
		JSONObject json = jsonParser.createHttpRequest(url,"POST", nameValuePairs);
		System.out.println(json);
		Log.d("Create Response", json.toString());
		
		try {
			int success = json.getInt(SUCCESS);
			if (success == 1) 
			 {
			  flag=0;	
			  //if the user has successfully registered,go to the Password page
			  Intent i = new Intent(getApplicationContext(),Password.class);

			  startActivity(i);
			  finish();
			 }
			 else
			 {
				flag=1;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String file_url) {
	
	}
  }	
}