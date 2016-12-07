package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    private EditText ET_USER, ET_PASS, ET_PASS2;
    private Button register;
    private RequestQueue requestQueue;
    private StringRequest request;
    boolean continueRegister;

    private String register_url = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/User/CreateUser/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ET_USER = (EditText) findViewById(R.id.tb_username);
        ET_PASS = (EditText) findViewById(R.id.tb_password);
        ET_PASS2 = (EditText) findViewById(R.id.tb_password2);
        register = (Button) findViewById(R.id.btn_create_account);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueRegister = true;

                String pass1 = ET_PASS.getText().toString();
                String pass2 = ET_PASS2.getText().toString();
                final String email = ET_USER.getText().toString();

                if(!pass1.equals(pass2)){
                    Toast.makeText(getApplicationContext(), "Passwords do not match: Please try again", Toast.LENGTH_SHORT).show();
                    continueRegister = false;
                }

                if (!validateEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Invalid Email: Please try again", Toast.LENGTH_SHORT).show();
                    continueRegister = false;
                }
                if (!validatePassword(pass1)) {
                    Toast.makeText(getBaseContext(), "Invalid Password: must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    continueRegister = false;
                }

                if(continueRegister) {
                    request = new StringRequest(Request.Method.POST, register_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_SHORT).show(); //REGISTER SUCCESS - return success flag and username

                                    SharedPreferences csp = getApplicationContext().getSharedPreferences("login_pref", 0);
                                    SharedPreferences.Editor cEditor = csp.edit();
                                    cEditor.putString("usernameEmail", email);
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
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("email", ET_USER.getText().toString());
                            hashMap.put("password", ET_PASS.getText().toString()); //TODO - HASH PASSWORD

                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
    }

    //Go back to sign in screen
    public void loginClick(View v) {
        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
        finish();
    }

    public boolean validateEmail(String email){
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    boolean validatePassword(String password) {
        return !(password.length() < 6);
    }


}
