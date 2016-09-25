package com.jordanklamut.interactiveevents;

//import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();

        //TextView tv = (TextView) findViewById(R.id.tv_username);
        //tv.setText("Changed");
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            PreferencesActivity.startThisActivityForResult(this, SETTINGS_REQUEST);
            //startActivity(new Intent(DrawerActivity.this, SettingsActivity.class));
        }
        else if (id == R.id.action_log_out) {
            Toast.makeText(DrawerActivity.this, "Logged Out", Toast.LENGTH_LONG).show();

            //remove username from login_pref
            SharedPreferences csp = this.getSharedPreferences("login_pref", 0);
            SharedPreferences.Editor cEditor = csp.edit();
            cEditor.clear().apply();

            startActivity(new Intent(DrawerActivity.this, LogInActivity.class));
            finish();
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
            fm.beginTransaction().replace(R.id.content_frame, new MyScheduleFragment()).commit();
            //fm.beginTransaction().replace(R.id.content_frame, new ScheduleFragment()).commit();
            //Toast.makeText(DrawerActivity.this, "Schedule is not available", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_my_schedule) {
            fm.beginTransaction().replace(R.id.content_frame, new MyScheduleFragment()).commit();
        } else if (id == R.id.nav_find_convention) {
            startActivity(new Intent(DrawerActivity.this, ConventionFinderActivity.class));
        }else if (id == R.id.nav_share) {
            composeEmail("", "Check out Interactive Events", "TODO : App details and link to app to download?");
        }else if (id == R.id.nav_feedback) {
            composeEmail("feedback@jordanklamut.com", "Interactive Events Feedback", null);
        }else if (id == R.id.nav_about) {
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.about_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialoglayout);
            builder.show();
        } else if (id == R.id.pref) {
            //startActivity(new Intent(DrawerActivity.this, PreferencesActivity.class));
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

    public void findNewConventionClick(View v)
    {
        startActivity(new Intent(DrawerActivity.this, ConventionFinderActivity.class));
    }

}
