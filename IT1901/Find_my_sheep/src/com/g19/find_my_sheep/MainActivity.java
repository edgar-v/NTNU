package com.g19.find_my_sheep;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.g19.find_my_sheep.R;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(LogginStatus.getUserName(MainActivity.this).length() != 0)
		{
			map();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button login = (Button) findViewById(R.id.b3Login);
		Button reg = (Button) findViewById(R.id.b3Reg);
		Button reset = (Button) findViewById(R.id.b3Recover);

		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				new loginAction().execute();
			}	
		});

		reg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, RegUser.class);
				startActivity(intent);
				finish();
			}
		});

		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, RecoverPassword.class);
				startActivity(intent);
				finish();
			}
		});


		reg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, RegUser.class);
				startActivity(intent);
				finish();

			}
		});

	}

	private class loginAction extends AsyncTask<Void,Void,Integer>{

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			TextView display = (TextView) findViewById(R.id.tvLog);
			display.setText(Integer.toString(result));

			if(result == 200){
				map();
			}
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return login();
		}
	}

	private int login(){
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://129.241.126.66/cgi-bin/checkuser.py");

		final EditText user = (EditText) findViewById(R.id.etUser);
		final EditText pass = (EditText) findViewById(R.id.etPassword);

		String username = user.getText().toString();
		String password = pass.getText().toString();


		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));



			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);


			//Om responsen fra server er 200 (OK), lagres brukernavnet i appen, til senere bruk. 
			if(response.getStatusLine().getStatusCode() == 200){
				String [] userInfoArray;
				LogginStatus.setUserName(this, username);

				HttpPost userinfoResponse = new HttpPost("http://129.241.126.66/cgi-bin/getuserinfo.py");

				try{
					List<NameValuePair> sendUserName = new ArrayList<NameValuePair>(1);
					sendUserName.add(new BasicNameValuePair("username", username));
					userinfoResponse.setEntity(new UrlEncodedFormEntity(sendUserName));

					HttpResponse response2 = httpclient.execute(userinfoResponse);
					String userInfoString = getResponseBody(response2);

					userInfoArray = userInfoString.trim().split(",");
					LogginStatus.setName(this, userInfoArray[1]);
					LogginStatus.setEmail(this, userInfoArray[2]);
					LogginStatus.setTlf(this, userInfoArray[3]);

				}catch (Exception e){
					e.printStackTrace();
				}

				DatabaseSuperpower source = new DatabaseSuperpower(MainActivity.this);
				source.open();
				LogginStatus.setUserName(this, username);
				String[] sheepArray;

				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://129.241.126.66/cgi-bin/getsheepinfo.py");

				try {
					// Add your data
					List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(1);
					valuePairs.add(new BasicNameValuePair("username", username));

					post.setEntity(new UrlEncodedFormEntity(valuePairs));

					HttpResponse resp = client.execute(post);
					String responseBody = getResponseBody(resp);
					responseBody = responseBody.substring(2,responseBody.length()-2);
					sheepArray = responseBody.trim().split(",");


					int counter = 0;
					while(counter < sheepArray.length){
						ContentValues values = new ContentValues();
						//10 fordi det er antall elementer i databasen
						for(int i = 0; i < 10; i++){
							sheepArray[counter] = sheepArray[counter].trim();
							switch(i){
							case 0:
								sheepArray[counter] = sheepArray[counter].substring(1);
								values.put("id", Integer.parseInt(sheepArray[counter]));
								break;
							case 1:
								values.put("longitude", Double.parseDouble(sheepArray[counter]));
								break;
							case 2:
								values.put("latitude", Double.parseDouble(sheepArray[counter]));
								break;
							case 3:
								sheepArray[counter] = sheepArray[counter].substring(1,sheepArray[counter].length()-1);
								values.put("name", sheepArray[counter]);
								break;
							case 4:
								values.put("age", Integer.parseInt(sheepArray[counter]));
								break;
							case 5:
								values.put("weight", Integer.parseInt(sheepArray[counter]));
								break;
							case 6:
								values.put("alive", Boolean.parseBoolean(sheepArray[counter]));
								break;
							case 7:
								values.put("alarm", Boolean.parseBoolean(sheepArray[counter]));
								break;
							case 9:
								if(sheepArray[counter].equals("None")||sheepArray[counter].equalsIgnoreCase("'None'"))
									sheepArray[counter] = sheepArray[counter].substring(0,sheepArray[counter].length()-1);
								else if(sheepArray[counter] != null)
									sheepArray[counter] = sheepArray[counter].substring(1,sheepArray[counter].length()-2);
								values.put("health", sheepArray[counter]);
								break;
							}
							counter++;
						}
						source.startUp(values);
					}

					source.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return response.getStatusLine().getStatusCode();


		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}


	//Denne metoden kj�rer map aktiviteten og avslutter MainActivity
	public void map(){
		Intent intent = new Intent(this, Map.class);
		startActivity(intent);
		finish();

	}

	public static String getResponseBody(HttpResponse response) {

		String response_text = null;
		HttpEntity entity = null;
		try {
			entity = response.getEntity();
			response_text = _getResponseBody(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e1) {
				}
			}
		}
		return response_text;
	}

	public static String _getResponseBody(final HttpEntity entity) throws IOException, ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		InputStream instream = entity.getContent();

		if (instream == null) return "";

		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					"HTTP entity too large to be buffered in memory");
		}

		String charset = getContentCharSet(entity);

		if (charset == null) charset = HTTP.DEFAULT_CONTENT_CHARSET;

		Reader reader = new InputStreamReader(instream, charset);
		StringBuilder buffer = new StringBuilder();

		try {
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}

		return buffer.toString();
	}


	public static String getContentCharSet(final HttpEntity entity) throws ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		String charset = null;

		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}
		return charset;
	}
}


