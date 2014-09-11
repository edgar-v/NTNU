package com.g19.find_my_sheep;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.g19.find_my_sheep.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button login = (Button) findViewById(R.id.b3Login);
		Button reg = (Button) findViewById(R.id.b3Reg);
		
		login.setOnClickListener(new View.OnClickListener() {
    
        	public void onClick(View arg0) {
        		new loginAction().execute();
     
   }
			
  });
		
//		reg.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent myIntent = new Intent(Login.this,
//						RegisterUser.class);
//				startActivity(myIntent);
//				
//			}
//		});
		
	}
	
private class loginAction extends AsyncTask<Void,Void,Integer>{

		

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			TextView display = (TextView) findViewById(R.id.tvLog);
			display.setText(Integer.toString(result));
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
	
	


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

