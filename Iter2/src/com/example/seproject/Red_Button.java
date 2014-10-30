package com.example.seproject;

import android.telephony.SmsManager;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Red_Button extends Activity{

	Button buttonSend;
	EditText textPhoneNo;
	EditText textSMS;
	LocationManager locationManager;
	LocationListener locationListener;
	Context context;
	TextView txtLat;
	String lat;
	String provider;
	String latitude,longitude; 
	boolean gps_enabled,network_enabled;
	LocationListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_red__button);
		

		buttonSend = (Button) findViewById(R.id.help_btn);
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener = new MyAsyncTask();
				//calling the location service
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				//requesting location updates after 3.2 miles and 50 minutes
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3000, listener);
				new MyAsyncTask().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Adds items to the action bar
		getMenuInflater().inflate(R.menu.red__button, menu);
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
    //the Async Task is implementing the location listener
	public class MyAsyncTask extends AsyncTask<Void, Void, Void> implements LocationListener{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;

		}
		
		@Override
		public void onLocationChanged(Location location) {
			//get the latitude and the longitude
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			String coordinates = "Latitude = " + latitude + "and Longitude = " + longitude;
			System.out.println(coordinates);
			try {
				//SmsManager API used for sending messages
				SmsManager smsManager = SmsManager.getDefault();
				//use it with the following parameters: destination address(string),source address, message, PendingIntent sentIntent, PendingIntent deliveyIntent
				smsManager.sendTextMessage("+18178233972", null, "Help!!!" + coordinates, null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent!",
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				//sending messages failed
				Toast.makeText(getApplicationContext(),
						"SMS faild, please try again later!",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}
	}
}