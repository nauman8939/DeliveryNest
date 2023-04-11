package com.example.deliverynest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private static final String PREF_NAME = "SessionManager";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final int PRIVATE_MODE = 0;
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phonenumber";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_DATE ="date";

    private final Context context;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }
    public void createLoginSession(String fullname, String username, String email, String phoneNo, String password,String date) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);

        editor.putString(KEY_FULLNAME, fullname);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNo);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE,date);


        editor.commit();

    }
    public HashMap<String, String> getUsersDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();


        userData.put(KEY_FULLNAME, preferences.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, preferences.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, preferences.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE,preferences.getString(KEY_DATE,null));

        return userData;
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        //Clear the shared preferences
        editor.clear();
        editor.apply();

        //Start the login activity
        Intent i = new Intent(context, LoginScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
