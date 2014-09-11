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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditUser extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edituser);
		Button reg = (Button) findViewById(R.id.b6edituser);
		
		final TextView username = (TextView) findViewById(R.id.etUser);
		username.setText(LogginStatus.getUserName(EditUser.this));
		
		EditText name = (EditText) findViewById(R.id.etName);
		name.setText(LogginStatus.getName(EditUser.this));
		
		EditText email = (EditText) findViewById(R.id.etEmail);
		email.setText(LogginStatus.getEmail(EditUser.this));
		EditText telephone = (EditText) findViewById(R.id.etTlf);
		
		telephone.setText(LogginStatus.getTlf(EditUser.this));
		
		reg.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				new editAction().execute();
			}
			
		});
	}
	
private class editAction extends AsyncTask<Void,Void,Integer>{
	
	
	
	
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		TextView display = (TextView) findViewById(R.id.tvLog);
		display.setText(Integer.toString(result));
		
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	//Intent intent = new Intent(EditUser.this, MainActivity.class);
            	//startActivity(intent);
            	finish();
            }
        }, 3000);
	}


	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return edit();
	}
	
	
	
	private int edit(){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://129.241.126.66/cgi-bin/edituser.py");
		
		EditText name = (EditText) findViewById(R.id.etName);
	    EditText email = (EditText) findViewById(R.id.etEmail);
	    final TextView username = (TextView) findViewById(R.id.etUser);
	    EditText password = (EditText) findViewById(R.id.etPassword);
	    EditText telephone = (EditText) findViewById(R.id.etTlf);
	    EditText oldPassword = (EditText) findViewById(R.id.etOldPass);

	    String nameString = name.getText().toString();
	    String emailString = email.getText().toString();
	    String usernameString = username.getText().toString();
	    String passwordString = password.getText().toString();
	    String telephoneString = telephone.getText().toString();
	    String oldPasswordString = oldPassword.getText().toString();
	   
	    try {
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
		    nameValuePairs.add(new BasicNameValuePair("name", nameString));
		    nameValuePairs.add(new BasicNameValuePair("email", emailString));
		    nameValuePairs.add(new BasicNameValuePair("username", usernameString));
		    nameValuePairs.add(new BasicNameValuePair("password", passwordString));
		    nameValuePairs.add(new BasicNameValuePair("tlf", telephoneString));
		    nameValuePairs.add(new BasicNameValuePair("oldpassword", oldPasswordString));
		    
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 
		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost);
		    if(response.getStatusLine().getStatusCode() == 200){
		    	LogginStatus.setName(EditUser.this, nameString);
		    	LogginStatus.setEmail(EditUser.this, emailString);
		    	LogginStatus.setTlf(EditUser.this, telephoneString);
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
	    
	}
}

