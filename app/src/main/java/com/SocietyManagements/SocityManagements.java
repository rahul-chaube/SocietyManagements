package com.SocietyManagements;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class SocityManagements extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
