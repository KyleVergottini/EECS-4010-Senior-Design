package com.jordanklamut.interactiveevents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SETTINGS_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        //SET USERNAME IN NAV HEADER
        View header = navigationView.getHeaderView(0);
        TextView tv_username = (TextView) header.findViewById(R.id.tv_username);
        SharedPreferences csp = getSharedPreferences("login_pref", 0);
        if (csp.getString("usernameEmail", null) != null){
            tv_username.setText(csp.getString("usernameEmail", null));
        }

        //TODO - SET BUILD IN NAV HEADER, REMOVE UPON RELEASE
        TextView tv_build = (TextView) header.findViewById(R.id.tv_app_version);
        String date = new SimpleDateFormat("yy.MM.dd").format(new Date());
        tv_build.setText("Alpha Build " + date, null);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                PreferencesActivity.startThisActivityForResult(this, SETTINGS_REQUEST);
                return true;
            case R.id.action_log_out:
                //remove username from login_pref
                SharedPreferences csp = this.getSharedPreferences("login_pref", 0);
                SharedPreferences.Editor cEditor = csp.edit();
                cEditor.clear().apply();
                Toast.makeText(DrawerActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DrawerActivity.this, LogInActivity.class));
                finish();
                return true;
            case R.id.action_refresh:
                new GetAllEventsPHPtoSQLite().execute();
                Toast.makeText(DrawerActivity.this, "Refreshing Database...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_filter:
                Toast.makeText(DrawerActivity.this, "Filters Not Available", Toast.LENGTH_LONG).show(); //TODO - add filter screen
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        } else if (id == R.id.nav_map) {
            fm.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();
        } else if (id == R.id.nav_schedule) {
            fm.beginTransaction().replace(R.id.content_frame, new ScheduleFragment()).commit();
        } else if (id == R.id.nav_my_schedule) {
            fm.beginTransaction().replace(R.id.content_frame, new MyScheduleFragment()).commit();
        } else if (id == R.id.nav_find_convention) {
            startActivity(new Intent(DrawerActivity.this, ConventionFinderActivity.class));
        }else if (id == R.id.nav_share) {
            composeEmail("", "Check out ConBuddy", "Check out this app! Plan your busy convention schedule using ConBuddy."); //TODO - add link if/when available
        }else if (id == R.id.nav_feedback) {
            composeEmail("feedback@jordanklamut.com", "ConBuddy Feedback", null);
        }else if (id == R.id.nav_about) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.about_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogLayout);
            builder.show();
        } else if (id == R.id.pref) {
            PreferencesActivity.startThisActivityForResult(this, SETTINGS_REQUEST);
        }else if (id == R.id.debug) {
            startActivity(new Intent(DrawerActivity.this, DebugActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void composeEmail(String mailTo, String emailSubject, String emailContent)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailTo, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    //EXECUTES ON REFRESH CLICK - GETS ALL EVENTS FROM PHP MATCHING CONVENTION ID, CREATES SQLite AND RETURNS
    private class GetAllEventsPHPtoSQLite extends AsyncTask<String, Void, String> {
        DatabaseManager dm;
        ProgressDialog pd;

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences csp = getSharedPreferences("login_pref", 0);
            dm = new DatabaseManager(DrawerActivity.this);
            dm.setEventList(DrawerActivity.this, csp.getString("homeConventionID", null)); //gets from php, and inserts into sqlite
            dm.setRoomList(DrawerActivity.this, csp.getString("homeConventionID", null)); //gets from php, and inserts into sqlite
			dm.setMapList(DrawerActivity.this, csp.getString("homeConventionID", null));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(DrawerActivity.this);
            pd.setTitle("Please Wait...");
            pd.setMessage("Gettings Events...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
