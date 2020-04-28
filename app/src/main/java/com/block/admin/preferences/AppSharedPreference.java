package com.block.admin.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
    private static final String DB_NAME = "pch";
    private static AppSharedPreference appSharedPreference;
    private static SharedPreferences preferences;

    private AppSharedPreference() {
    }

    public static AppSharedPreference getInstance(Context context) {
        if (preferences == null) {
            appSharedPreference = new AppSharedPreference();
            preferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        }
        return appSharedPreference;
    }

    public long getLastDateForMessage() {
        return preferences.getLong("last_date", 0);
    }

    public void setLastDateForMessage() {
        preferences.edit().putLong("last_date", System.currentTimeMillis()).apply();
    }

    public void setState(String state) {
        preferences.edit().putString("state", state).apply();
    }

    public String getState() {
        return preferences.getString("state", "0");
    }

}
