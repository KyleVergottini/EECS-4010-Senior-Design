package com.jordanklamut.interactiveevents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get login preference and check for username
        SharedPreferences csp = getSharedPreferences("login_pref", 0);
        if (csp.getString("usernameEmail", null) != null) {
            //IF NO CON SET, START CONVENTION FINDER
            if (csp.getString("homeConventionID", null) == null) {
                Intent intent = new Intent(this, ConventionFinderActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, DrawerActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
