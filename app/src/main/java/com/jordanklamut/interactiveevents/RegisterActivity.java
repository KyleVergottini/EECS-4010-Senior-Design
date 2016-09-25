package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jorda on 9/20/2016.
 */
public class RegisterActivity extends Activity {

    EditText ET_USER, ET_PASS;
    String login_user, login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ET_USER = (EditText) findViewById(R.id.tb_username);
        ET_PASS = (EditText) findViewById(R.id.tb_password);

    }

    public void createAccountClick(View v) {

        String name,user_name,user_pass;

        name = ET_USER.getText().toString();
        user_name = ET_USER.getText().toString();
        user_pass = ET_PASS.getText().toString();
        String method = "register";
        AccountTask accountTask = new AccountTask(this);
        accountTask.execute(method,name,user_name,user_pass);
        finish();
    }

    //Go back to sign in screen
    public void loginClick(View v) {
        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
        finish();
    }
}
