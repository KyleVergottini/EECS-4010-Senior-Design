package com.jordanklamut.interactiveevents;

import android.app.AlertDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

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
    Settings settings = new Settings();
    public static final String PREFERENCES_NAME = "IEPref";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    AccountTask(Context ctx)
    {
        this.mContext =ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Login Information....");
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://www.jordanklamut.com/InteractiveEvents/register.php";
        String login_url = "http://www.jordanklamut.com/InteractiveEvents/login.php";
        String method = params[0];

        if (method.equals("register"))
        {
            String name = params[1];
            String user_name = params[2];
            String user_pass = params[3];
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
                return "create_success/" + user_name;
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

        String[] results = result.split("/");

        //sign in
        if (results[0].trim().equals("login_success")) {
            Toast.makeText(mContext, "Logged In", Toast.LENGTH_LONG).show();

            //SET LOGIN_PREFERENCE
            SharedPreferences csp = mContext.getSharedPreferences("login_pref", 0);
            SharedPreferences.Editor cEditor = csp.edit();
            cEditor.putString("usernameEmail", results[1].trim());
            cEditor.apply();

            Intent myIntent = new Intent(mContext, DrawerActivity.class);
            mContext.startActivity(myIntent);
            //TODO need to finish() so user cant back into login screen
        }
        else if (results[0].trim().equals("login_failed")) {
            alertDialog.setTitle("Login Failed");
            alertDialog.setMessage("Email or password is not correct. Please try again.");
            //alertDialog.setMessage(result);
            alertDialog.show();
        }
        //sign up
        else if(results[0].equals("create_success")) {
            Toast.makeText(mContext, "Account created", Toast.LENGTH_LONG).show();

            //SET LOGIN_PREFERENCE
            SharedPreferences csp = mContext.getSharedPreferences("login_pref", 0);
            SharedPreferences.Editor cEditor = csp.edit();
            cEditor.putString("usernameEmail", results[1].trim());
            cEditor.apply();

            Intent myIntent = new Intent(mContext, DrawerActivity.class);
            mContext.startActivity(myIntent);
            //TODO need to finish() so user cant back into login screen
        }
        else {
            alertDialog.setTitle("Unknown Error");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}
