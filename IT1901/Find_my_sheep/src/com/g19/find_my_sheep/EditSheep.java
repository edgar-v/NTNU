package com.g19.find_my_sheep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditSheep extends Activity {

	DatabaseSuperpower source;
	ContentValues values;
	EditText age;
	EditText weight;
	EditText health;
	int id;
	/**
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_sheep);

		Bundle b = getIntent().getExtras();
		id = b.getInt("id");

		source = new DatabaseSuperpower(EditSheep.this);
		source.open();
		
		values = source.getSheepInfoFromDatabase(id);
		
		final Button editSheep = (Button) findViewById(R.id.b3editSheep);
		final Button delete = (Button) findViewById(R.id.b3deleteSheep);
		final Button showLog = (Button) findViewById(R.id.b3Sheeplog);
		
		final TextView name = (TextView) findViewById(R.id.showSheepName);
		name.setText((CharSequence) values.get("name"));
		age = (EditText) findViewById(R.id.etSheepAge);
		age.setText((CharSequence) values.get("age"));
		weight = (EditText) findViewById(R.id.etSheepWeight);
		weight.setText((CharSequence) values.get("weight"));
		health = (EditText) findViewById(R.id.etSheepHealth);
		health.setText((CharSequence) values.get("health"));
		final TextView statusView = (TextView) findViewById(R.id.tvsheepAlive);
		if(values.getAsInteger("alive") == 1)
			statusView.setText("Alive");
		else
			statusView.setText("Deceased");

		/*showLog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});*/
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				source.delete(id);
			}
		});
		
		editSheep.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				age.setFocusable(true);
				age.setFocusableInTouchMode(true);
				weight.setFocusable(true);
				weight.setFocusableInTouchMode(true);
				health.setFocusable(true);
				health.setFocusableInTouchMode(true);
				delete.setVisibility(4);
				showLog.setVisibility(4);
				editSheep.setText(R.string.b3editSheep);
				editSheep.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						new EditSheepAction().execute();
						age.setFocusable(false);
						weight.setFocusable(false);
						health.setFocusable(false);
						delete.setVisibility(0);
						showLog.setVisibility(0);
						editSheep.setText(R.string.editSheep);
					}
				});
			}
		});

	}
	
	  @Override
	  protected void onResume() {
	    source.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    source.close();
	    super.onPause();
	  }
	  
	  
	  


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}





	private class EditSheepAction extends AsyncTask<Void,Void,Integer>{

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			if(result == 200){
				Toast.makeText(EditSheep.this, "Saving..", Toast.LENGTH_SHORT).show();
			}
			/*new Handler().postDelayed(new Runnable() {
				@SuppressLint("NewApi")
				@Override
				public void run() {
					Intent intent = getParentActivityIntent();
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				    source.close();
					finish();
				}
			}, 0);*/
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return edit();

		}

		/**
		 * Metode som legger informasjonen om en sau inn i databasen
		 * @return response.getStatusLine().getStatusCode();	returnerer melding fra databasen
		 */

		private int edit(){

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://129.241.126.66/cgi-bin/editsheep.py");

			String ageString = age.getText().toString();
			String weightString = weight.getText().toString();
			String healthString = health.getText().toString();
			String idString = Integer.toString(id);

			try {
				ContentValues newData = new ContentValues();
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
				nameValuePairs.add(new BasicNameValuePair("sheepid", idString));
				nameValuePairs.add(new BasicNameValuePair("age", ageString));
				nameValuePairs.add(new BasicNameValuePair("weight", weightString));
				nameValuePairs.add(new BasicNameValuePair("comment", healthString));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				if(response.getStatusLine().getStatusCode() == 200){
					newData.put("age",ageString);
					newData.put("weight", weightString);
					newData.put("health", healthString);

					source.sheepUpdate(newData,id);
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