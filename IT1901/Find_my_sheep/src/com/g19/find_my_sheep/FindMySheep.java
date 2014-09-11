package com.g19.find_my_sheep;

import android.app.Application;

public class FindMySheep extends Application {
	
	private String username;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
