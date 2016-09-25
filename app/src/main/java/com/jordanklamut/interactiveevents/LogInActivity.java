package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jorda on 9/17/2016.
 */
public class LogInActivity extends Activity{

    EditText ET_USER, ET_PASS;
    String login_user, login_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);

        ET_USER = (EditText) findViewById(R.id.tb_username);
        ET_PASS = (EditText) findViewById(R.id.tb_password);

    }

    public void loginClick(View v) {

        login_user = ET_USER.getText().toString();
        login_pass = ET_PASS.getText().toString();

        String method = "login";

        AccountTask accountTask = new AccountTask(this);
        accountTask.execute(method,login_user,login_pass);
    }

    //Go to sign up screen
    public void registerClick(View v) {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        finish();
    }
}
