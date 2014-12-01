package com.example.seproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	//extracting the NameValuePairs from SignUp.java and converting them into JSON format
	public JSONObject createHttpRequest(String url, String method,List<NameValuePair> nameValuePairs) {

		try {
			//execute this if the method called is a POST
			if(method == "POST")
			{			
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

			}
			//execute this if the method called is a GET
			else if(method == "GET")
			{
				DefaultHttpClient client = new DefaultHttpClient();
				String s_param = URLEncodedUtils.format(nameValuePairs, "utf-8");
				url += "?" + s_param;
				HttpGet get = new HttpGet(url);

				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder s_builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				s_builder.append(line + "\n");
			}
			is.close();
			json = s_builder.toString();
		} 
		catch (Exception e)  {
			Log.e("Buffer Error", "Cannot convert the result " + e.toString());
		}
		Log.d("JSON PARSER", json);
		try {
			//converting to JSON format
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Cannot parse the data " + e.toString());
		}

		return jObj;
	}
}
