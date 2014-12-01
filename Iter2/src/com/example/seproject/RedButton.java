package com.example.seproject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.telephony.SmsManager;
import android.util.Log;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
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
import android.location.Address;

@SuppressWarnings("deprecation")
public class RedButton extends Activity{

	Button robbedBtn;
	Button kidnappedBtn;
	Button friendBtn;
	Button fireBtn;
	Button miscBtn;
	Button logoutBtn;
	EditText textPhoneNo;
	EditText textSMS;
	LocationManager locationManager;
	LocationListener locationListener;
	Context context;
	TextView txtLat;
	String lat;
	String provider;
	boolean gps_enabled,network_enabled;
	LocationListener listener;
	int flag = 0;
	Context mcontext;
	String coordinates;
	Bundle extras;
	String s_emailid,s_username1;
	public static final String PREFS_NAME = "RedButtonPrefs";
	String number;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_red__button);
		
		//Getting the context of this class
		Context context = getApplicationContext();
		//class used to lock and unlock the keyboard
        KeyguardManager _guard = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        //disable or enable the keyguard
		KeyguardLock _keyguardLock = _guard
                .newKeyguardLock("KeyguardLockWrapper");
        _keyguardLock.disableKeyguard();

        //on clicking the power button, goes the updateService class
        RedButton.this.startService(new Intent(
                RedButton.this, UpdateService.class));
        
		Intent intent = getIntent();
		s_username1 = intent.getStringExtra("s_username");
		robbedBtn = (Button) findViewById(R.id.robbed_btn);
		robbedBtn.setOnClickListener(new OnClickListener() {

			//calling the location listener API which implicitly calls on location_changed
			@Override
			public void onClick(View v) {
				flag = 1;
				listener = new MyAsyncTask();
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3000, listener);
				new MyAsyncTask().execute();
			}
		});

		kidnappedBtn = (Button) findViewById(R.id.kidnapped_btn);
		kidnappedBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 2;
				listener = new MyAsyncTask();
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3000, listener);
				new MyAsyncTask().execute();
			}
		});

		friendBtn = (Button) findViewById(R.id.friend_btn);
		friendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 3;
				listener = new MyAsyncTask();
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3000, listener);
				new MyAsyncTask().execute();
			}
		});

		fireBtn = (Button) findViewById(R.id.fire_btn);
		fireBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 4;
				listener = new MyAsyncTask();
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3000, listener);
				new MyAsyncTask().execute();
			}
		});

		miscBtn = (Button) findViewById(R.id.misc_btn);
		miscBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 5;
				listener = new MyAsyncTask();
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3000, listener);
				new MyAsyncTask().execute();
			}
		});

		logoutBtn = (Button) findViewById(R.id.logout_btn);
		logoutBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent(RedButton.this,
						SignIn.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
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

	public class MyAsyncTask extends AsyncTask<Double, Void, Void> implements LocationListener{
		Geocoder geocoder;
		List<Address> addresses;

		@Override
		protected Void doInBackground(Double... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onLocationChanged(Location location) {
			double latitude;
			double longitude;
            
			//getting the location coordinates
			latitude = location.getLatitude();
			longitude = location.getLongitude();

			try {
				//formatting the coordinates to the formatted address
				geocoder = new Geocoder(RedButton.this, Locale.ENGLISH);
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
				StringBuilder str = new StringBuilder();
				if (Geocoder.isPresent()) {
					//extracting the street, city, state and the country from the formatted address
					Address returnAddress = addresses.get(0);
					String street = returnAddress.getThoroughfare();
					String localityString = returnAddress.getLocality();
					String city = returnAddress.getCountryName();
					String region_code = returnAddress.getCountryCode();
					String zipcode = returnAddress.getPostalCode();
					//appending the coordinates to the Google maps which are sent as a part of the message
					String link = "Click this : " + '\n' + "http://maps.google.com/maps?q=" + latitude + "," + longitude;
					str.append(s_username1 + '\n'+'\n');
					str.append(street + '\n');
					str.append(localityString + '\n');
					str.append(city + '\n' + region_code + '\n');
					str.append(zipcode + '\n');
					str.append(link +'\n');
					sendText(str);
				} else {
					Toast.makeText(getApplicationContext(),
							"geocoder not present", Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("tag", e.getMessage());
			}
		}

		//using SmsManager API for sending messages
		public void sendText(StringBuilder str){
			try {
				number = getString(R.string.Police_Number);
				SmsManager smsManager = SmsManager.getDefault();
				switch (flag) {
				case 1:
					if(flag == 1){
						smsManager.sendTextMessage(number, null, "Help!" + '\n' + '\n' + "I have been robbed!" + '\n' + '\n' + str + '\n' , null, null);
						Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
					}
					else{
						break;
					}

				case 2:
					if(flag == 2){
						smsManager.sendTextMessage(number, null, "Help!" + '\n' + '\n' + "I have been kidnapped!" + '\n' + '\n' + str + '\n', null, null);
						Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
					}
					else{
						break;
					}

				case 3:
					if(flag == 3){
						smsManager.sendTextMessage(number, null, "Help!" + '\n' + '\n' + "My friend needs help!" + '\n' + '\n' + str + '\n', null, null);
						Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
					}
					else{
						break;
					}

				case 4:
					if(flag == 4){
						smsManager.sendTextMessage(number, null, "Help!" + '\n' + '\n' + "Fire!" + '\n' + '\n' + str + '\n', null, null);
						Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
					}
					else{
						break;
					}

				case 5:
					if(flag == 5){
						smsManager.sendTextMessage(number, null, "Non - Emergency help!" + '\n' + '\n' + str + '\n', null, null);
						Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
					}
					else{
						break;
					}

				default:
					break;
				}

			} catch (Exception e) {
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