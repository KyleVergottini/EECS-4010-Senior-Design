package com.jordanklamut.interactiveevents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class AccountTask extends AsyncTask<String, Void, String> {

    AlertDialog alertDialog;
    Context mContext;

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Login Information....");
    }

    @Override
    protected String doInBackground(String... params) {

        Toast.makeText(mContext, "DELETE ME?", Toast.LENGTH_LONG).show(); //TODO - Is AccountTask Used?

        //URL TO PHP SCRIPTS
        String reg_url = "http://www.jordanklamut.com/InteractiveEvents/register.php";
        String login_url = "http://www.jordanklamut.com/InteractiveEvents/login.php";
        String method = params[0];

        if (method.equals("register"))
        {
            String name = params[1];
            String user_name = params[2];
            String user_pass = params[3]; //TODO
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return null;//"create_success/" + user_name;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("login"))
        {
            String login_name = params[1];
            String login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {
        try{
            JSONObject json_data = new JSONObject(result);
            String resultUsername = json_data.getString("Username");
            String resultStatus = json_data.getString("Status");

            //SIGN IN
            if (resultStatus.equals("1")) {
                Toast.makeText(mContext, "Logged In", Toast.LENGTH_LONG).show();

                //SET LOGIN_PREFERENCE
                SharedPreferences csp = mContext.getSharedPreferences("login_pref", 0);
                SharedPreferences.Editor cEditor = csp.edit();
                cEditor.putString("usernameEmail", resultUsername);
                cEditor.apply();

                Intent myIntent = new Intent(mContext, DrawerActivity.class);
                mContext.startActivity(myIntent);
                //TODO need to finish() so user cant back into login screen
            } else if (resultStatus.equals("0")) {
                alertDialog.setTitle("Login Failed");
                alertDialog.setMessage("Email or password is not correct. Please try again.");
                //alertDialog.setMessage(result);
                alertDialog.show();
            }
            //SIGN UP
            else if (resultStatus.equals("1")) {
                Toast.makeText(mContext, "Account created", Toast.LENGTH_LONG).show();

                //SET LOGIN_PREFERENCE
                SharedPreferences csp = mContext.getSharedPreferences("login_pref", 0);
                SharedPreferences.Editor cEditor = csp.edit();
                cEditor.putString("usernameEmail", resultUsername);
                cEditor.apply();

                Intent myIntent = new Intent(mContext, DrawerActivity.class);
                mContext.startActivity(myIntent);
                //TODO need to finish() so user cant back into login screen
            } else {
                alertDialog.setTitle("Unknown Error");
                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }
        catch (JSONException e)
        {

        }
    }
}



/*
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
*/