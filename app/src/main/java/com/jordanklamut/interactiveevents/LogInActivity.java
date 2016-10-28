package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Context;
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

public class LogInActivity extends Activity {

    private EditText et_User, et_Pass;
    private Button btn_login;
    private RequestQueue requestQueue;
    private StringRequest request;

    private String login_url = "http://www.jordanklamut.com/InteractiveEvents/new_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);

        et_User = (EditText) findViewById(R.id.tb_username);
        et_Pass = (EditText) findViewById(R.id.tb_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

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
                        public void onErrorResponse(VolleyError error) {}
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("email",et_User.getText().toString());
                        hashMap.put("password",et_Pass.getText().toString());

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