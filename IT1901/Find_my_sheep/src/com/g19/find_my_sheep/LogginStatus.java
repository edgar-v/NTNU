package com.g19.find_my_sheep;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

//Denne klassen tar vare på brukernavnet i appen slik at brukeren ikke trenger logge på mer enn en gang. 
public class LogginStatus {
	
	
	static final String PREF_NAME= "name";
	static final String PREF_EMAIL= "email";
	static final String PREF_USER_NAME= "username";
	static final String PREF_PASSWORD= "password";
	static final String PREF_TLF= "tlf";
	
	
	

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    
    
    public static void setName(Context ctx, String name)
    {
    	Editor editor = getSharedPreferences(ctx).edit();
    	editor.putString(PREF_NAME, name);
    	editor.commit();
    }
    
    public static String getName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_NAME, "");
    }
    
    public static void setEmail(Context ctx, String email)
    {
    	Editor editor = getSharedPreferences(ctx).edit();
    	editor.putString(PREF_EMAIL, email);
    	editor.commit();
    }
    
    public static String getEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }
    
    public static void setTlf(Context ctx, String tlf)
    {
    	Editor editor = getSharedPreferences(ctx).edit();
    	editor.putString(PREF_TLF, tlf);
    	editor.commit();
    }
    
    public static String getTlf(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TLF, "");
    }
    
    public static void setPass(Context ctx, String pass) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASSWORD, pass);
        editor.commit();
    }

    public static String getPass(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
    }
    
    
    
    
    
    
    
}



