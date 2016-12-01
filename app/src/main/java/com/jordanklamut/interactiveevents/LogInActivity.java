package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends Activity {

    private EditText et_User, et_Pass;
    private Button btn_login;
    private RequestQueue requestQueue;
    private StringRequest request;

    private String login_url = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/User/Login/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);

        et_User = (EditText) findViewById(R.id.tb_username);
        et_Pass = (EditText) findViewById(R.id.tb_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//
        //if (activeNetwork == null)
        //    Toast.makeText(getApplicationContext(), "Error: No Connection", Toast.LENGTH_SHORT).show();
        //else
        //{
        //    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
        //        Toast.makeText(getApplicationContext(), "Connection: Wifi", Toast.LENGTH_SHORT).show();
        //    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
        //        Toast.makeText(getApplicationContext(), "Connection: Data", Toast.LENGTH_SHORT).show();
        //}

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {

                                SharedPreferences csp = getApplicationContext().getSharedPreferences("login_pref", 0);
                                SharedPreferences.Editor cEditor = csp.edit();
                                cEditor.putString("usernameEmail", jsonObject.getString("success"));
                                cEditor.apply();

                                Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_SHORT).show(); //LOGIN SUCCESS - returns success flag and Username
                                //startActivity(new Intent(getApplicationContext(), DrawerActivity.class));

                                //IF NO CON SET, START CONVENTION FINDER
                                if (csp.getString("homeConventionID", null) == null) {
                                    startActivity(new Intent(getApplicationContext(), ConventionFinderActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
                                    finish();
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show(); //LOGIN FAILED - returns error flag and message
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error: Please try again", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                        if (activeNetwork == null)
                            Toast.makeText(getApplicationContext(), "Error: No Internet", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getApplicationContext(), "Error: Unknown", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("email", et_User.getText().toString());
                        hashMap.put("password", et_Pass.getText().toString()); //TODO - HASH PASSWORD

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    //Go to sign up screen
    public void registerClick(View v) {
        startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        finish();
    }
}