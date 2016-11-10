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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private EditText ET_USER, ET_PASS;
    private Button register;
    private RequestQueue requestQueue;
    private StringRequest request;

    private String login_url = "http://www.jordanklamut.com/InteractiveEvents/register.php";

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

                                //TODO - CHECK FOR SHARED PREFS
                                if (csp.getString("homeConventionID", null) == null) {
                                    Intent intent = new Intent(getApplicationContext(), ConventionFinderActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
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
                        hashMap.put("password",ET_PASS.getText().toString()); //TODO - HASH PASSWORD

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    //Go back to sign in screen
    public void loginClick(View v) {
        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
        finish();
    }
}
