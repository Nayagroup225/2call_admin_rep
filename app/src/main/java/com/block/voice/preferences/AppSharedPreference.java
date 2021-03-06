package com.block.voice.preferences;

import android.content.Context;
import android.content.SharedPreferences;


public class AppSharedPreference {
    private static final String DB_NAME = "VoiceCall";
    private static AppSharedPreference appSharedPreference;
    private static SharedPreferences preferences;

    private AppSharedPreference() {
    }

    public static AppSharedPreference getInstance(Context context) {
        if (appSharedPreference == null) {
            appSharedPreference = new AppSharedPreference();
            preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        }
        return appSharedPreference;
    }

    public String getCallId() {
        return preferences.getString("call_id", "");
    }

    public void setCallId(String callId) {
        preferences.edit().putString("call_id", callId).apply();
    }

    public String getDeviceToken() {
        return preferences.getString("deviceToken", "");
    }

    public void setDeviceToken(String deviceToken) {
        preferences.edit().putString("deviceToken", deviceToken).apply();
    }

    public boolean getAccountRemeberd (){
        return preferences.getBoolean("is_remembered", false);
    }

    public void setAccount(String id, String password){
        preferences.edit().putBoolean("is_remembered", true).apply();
        preferences.edit().putString("id", id).apply();
        preferences.edit().putString("password", password).apply();
    }

    public String getAccountId(){
        return preferences.getString("id", "");
    }

    public String getAccountPassword(){
        return preferences.getString("password", "");
    }

    public void deleteAccount(){
        preferences.edit().putBoolean("is_remembered", false).apply();
    }
}
