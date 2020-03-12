package com.SocietyManagements.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.SocietyManagements.R;
import com.SocietyManagements.controller.PrefManager;
import com.SocietyManagements.ui.home.HomeActivity;
import com.SocietyManagements.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager=new PrefManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.isLogin())
                {
                    Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        },1500);
    }
}
