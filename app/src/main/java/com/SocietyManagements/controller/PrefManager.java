package com.SocietyManagements.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserName(String userName) {
        editor.putString(Constants.USER_NAME, userName).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(Constants.USER_NAME, "");
    }

    public void setLogin(boolean staus) {
        editor.putBoolean(Constants.IS_USER_LOGIN, staus).apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(Constants.IS_USER_LOGIN, false);
    }

    public void setUserEmail(String email) {
        editor.putString(Constants.USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(Constants.USER_EMAIL, "");
    }

    public void setUserMobile(String userMobile) {
        editor.putString(Constants.USER_MOBILE, userMobile).apply();
    }

    public String getUserMobile() {
        return sharedPreferences.getString(Constants.USER_MOBILE, "");
    }

    public void setUserID(String userID) {
        editor.putString(Constants.USER_ID, userID).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(Constants.USER_ID, "");
    }
}
