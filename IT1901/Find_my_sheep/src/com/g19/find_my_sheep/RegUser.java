package com.g19.find_my_sheep;
 
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegUser extends Activity {

        TextView tv;
 
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_reg_user);
 
                Button reg = (Button) findViewById(R.id.b3Reg);
                tv = (TextView) findViewById(R.id.tvLog);
 
                reg.setOnClickListener(new View.OnClickListener() {
 
                        public void onClick(View arg0) {
                                new regAction().execute();
                        }
 
                });
 
        }
 
        private class regAction extends AsyncTask<Void,Void,String>{
 
                @Override
                protected void onPostExecute(String result) {
                        // TODO Auto-generated method stub
                        super.onPostExecute(result);
 
                        TextView display = (TextView) findViewById(R.id.tvLog);
                        //display.setText(Integer.toString(result));
                        if(result.equals("correct")){
                                new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                Intent intent = new Intent(RegUser.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                        }
                                }, 3000);
                        } else {
                                display.setText(result);
                        }
                }
 
                @Override
                protected String doInBackground(Void... params) {
                        // TODO Auto-generated method stub
                        return reg();
 
                }
               
                public String validate(String inputName, String inputEmail, String inputUserName, String inputPassword, String inputPhone){
                	
                	if (inputName.isEmpty() || inputEmail.isEmpty() || inputUserName.isEmpty() || inputPassword.isEmpty() || inputPhone.isEmpty()){
                		return  "Ikke alle feltene ble utfult";
                	}
                	if (!inputName.matches("^[a-zA-Z]+$")){ 
                		return "Feil i Navnet";	
                	}
                	
                	if (!inputEmail.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                		return "Feil i emailen!";
                	}
                	
                	if (!inputUserName.matches("^[a-zA-Z0-9]+$")){
                		return "Feil i brukernavnet";
                	}
                	
                	if (!inputPassword.matches("^[a-zA-Z0-9]+$")){
                		return "Feil i Passordet";
                	}
                	
                	if (!inputPhone.matches("^[0-9]+$")){
                		return "Feil i telefonnummeret";
                	}
                		
                	return "riktig";
                }


                private String reg(){
 
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://129.241.126.66/cgi-bin/adduser.py");
 
                        final EditText name = (EditText) findViewById(R.id.etName);
                        final EditText email = (EditText) findViewById(R.id.etEmail);
                        final EditText username = (EditText) findViewById(R.id.etUser);
                        final EditText password = (EditText) findViewById(R.id.etPassword);
                        final EditText telephone = (EditText) findViewById(R.id.etTlf);
 
                        String nameString = name.getText().toString();
                        String emailString = email.getText().toString();
                        String usernameString = username.getText().toString();
                        String passwordString = password.getText().toString();
                        String telephoneString = telephone.getText().toString();
                        
                        if(!validate(nameString, emailString, usernameString, passwordString, telephoneString).equals("riktig")) 
                        	{
                        	validate(nameString, emailString, usernameString, passwordString, telephoneString); 
                        	return (validate(nameString, emailString, usernameString, passwordString, telephoneString));
                        	};
                        
 
                        try {
                                // Add your data
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                                nameValuePairs.add(new BasicNameValuePair("name", nameString));
                                nameValuePairs.add(new BasicNameValuePair("email", emailString));
                                nameValuePairs.add(new BasicNameValuePair("username", usernameString));
                                nameValuePairs.add(new BasicNameValuePair("password", passwordString));
                                nameValuePairs.add(new BasicNameValuePair("tlf", telephoneString));
 
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 
                                // Execute HTTP Post Request
                                HttpResponse response = httpclient.execute(httppost);

                                return "correct"; //response.getStatusLine().getStatusCode();
 
 
                        } catch (ClientProtocolException e) {
                                // TODO Auto-generated catch block
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                        } catch(Exception e){
                                e.printStackTrace();
                        }
                        return "wrong";
                }
 
        }
}
