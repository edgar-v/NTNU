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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**Klasse som registrerer sauer*/
public class RegisterSheep extends Activity {
	
	/**
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_sheep);
		
		ActionBar actionBar = getActionBar();
		//actionBar.hide();
//	    actionBar.setHomeButtonEnabled(true);
		
		Button regSheep = (Button) findViewById(R.id.b3sheepReg);
		
		regSheep.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				new RegSheepAction().execute();
			}
		});
		
	}


private class RegSheepAction extends AsyncTask<Void,Void,Integer>{

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		TextView display = (TextView) findViewById(R.id.tvsheepLog);
		display.setText(Integer.toString(result));
		
		
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	Intent intent = new Intent(RegisterSheep.this, SheepMenu.class);
            	startActivity(intent);
            	finish();
            }
        }, 3000);
	}

	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return reg();
		
	}
	
	/**
	 * Metode som legger informasjonen om en sau inn i databasen
	 * @return response.getStatusLine().getStatusCode();	returnerer melding fra databasen
	 */
	
	private int reg(){
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://129.241.126.66/cgi-bin/regsheep.py");
	 
	    final EditText name = (EditText) findViewById(R.id.etSheepName);
	    final EditText age = (EditText) findViewById(R.id.etSheepAge);
	    final EditText weight = (EditText) findViewById(R.id.etSheepWeight);
	    final EditText health = (EditText) findViewById(R.id.etSheepHealth);
	    
	    

	    String username = LogginStatus.getUserName(RegisterSheep.this);
	    String nameString = name.getText().toString();
	    String ageString = age.getText().toString();
	    String weightString = weight.getText().toString();
	    String healthString = health.getText().toString();
	    
	    try {
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		    nameValuePairs.add(new BasicNameValuePair("username", username));
		    nameValuePairs.add(new BasicNameValuePair("name", nameString));
		    nameValuePairs.add(new BasicNameValuePair("age", ageString));
		    nameValuePairs.add(new BasicNameValuePair("weight", weightString));
		    nameValuePairs.add(new BasicNameValuePair("health", healthString));
		    
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
	    
	}
}