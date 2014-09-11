package com.g19.find_my_sheep;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserMenu extends Activity{
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.user_menu);
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    
	    Button edit = (Button) findViewById(R.id.b6edituser);
	    
	    edit.setOnClickListener(new View.OnClickListener() {
	    	
	    	public void onClick(View arg0) {
	    		Intent intent = new Intent(UserMenu.this, EditUser.class);
				startActivity(intent);
	    	}
	    });
	    
	    final TextView username = (TextView) findViewById(R.id.etUser);
		username.setText(LogginStatus.getUserName(UserMenu.this));
		final TextView name = (TextView) findViewById(R.id.etName);
		name.setText(LogginStatus.getName(UserMenu.this));
		final TextView email = (TextView) findViewById(R.id.etEmail);
		email.setText(LogginStatus.getEmail(UserMenu.this));
		final TextView password = (TextView) findViewById(R.id.etPassword);
		password.setText("*********");
		final TextView telephone = (TextView) findViewById(R.id.etTlf);
		telephone.setText(LogginStatus.getTlf(UserMenu.this));
	    
	    
		
	}
}