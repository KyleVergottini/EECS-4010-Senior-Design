package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private EditText ET_USER, ET_PASS;
    private String login_user, login_pass;
    private Button register;
    private RequestQueue requestQueue;
    private StringRequest request;

    private String login_url = "http://www.jordanklamut.com/InteractiveEvents/new_register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ET_USER = (EditText) findViewById(R.id.tb_username);
        ET_PASS = (EditText) findViewById(R.id.tb_password);
        register = (Button) findViewById(R.id.btn_create_account);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show(); //REGISTER SUCCESS - return success flag and username

                                SharedPreferences csp = getApplicationContext().getSharedPreferences("login_pref", 0);
                                SharedPreferences.Editor cEditor = csp.edit();
                                cEditor.putString("usernameEmail", jsonObject.getString("success"));
                                cEditor.apply();

                                startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error: Please try again", Toast.LENGTH_SHORT).show(); //
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("email",ET_USER.getText().toString());
                        hashMap.put("password",ET_PASS.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });

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