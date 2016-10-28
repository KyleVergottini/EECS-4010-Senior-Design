package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by jorda on 9/21/2016.
 */
public class DebugActivity extends Activity {

    private static final int SETTINGS_REQUEST = 1000;
    private TextView txt_notifications_enabled;
    private TextView txt_notifications_reminder;
    private TextView txt_notification_ringtone;
    private TextView txt_notification_vibrate_enabled;
    private TextView txt_wifi_only_enabled;
    private TextView txt_sync_frequency;
    private TextView txt_username;
    private TextView txt_home_convention_id;
    private TextView txt_home_convention_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);
        initViews();
        fillViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SETTINGS_REQUEST:
                fillViews();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        txt_notifications_enabled = (TextView) findViewById(R.id.txt_notifications_enabled);
        txt_notifications_reminder = (TextView) findViewById(R.id.txt_notifications_reminder);
        txt_notification_ringtone = (TextView) findViewById(R.id.txt_notification_ringtone);
        txt_notification_vibrate_enabled = (TextView) findViewById(R.id.txt_notification_vibrate_enabled);
        txt_wifi_only_enabled = (TextView) findViewById(R.id.txt_wifi_only_enabled);
        txt_sync_frequency = (TextView) findViewById(R.id.txt_sync_frequency);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_home_convention_id = (TextView) findViewById(R.id.txt_home_convention);
        txt_home_convention_name = (TextView) findViewById(R.id.txt_home_convention_name);
    }

    private void fillViews() {
        AppSettings settings = AppSettings.getSettings(this);

        txt_notifications_enabled.setText(String.valueOf(settings.isNotificationsEnabled()));
        txt_notifications_reminder.setText(String.valueOf(settings.getNotificationReminder()));
        //txt_notification_ringtone.setText(settings.getNotificationRingtone());
        txt_notification_vibrate_enabled.setText(String.valueOf(settings.isNotificationVibrateEnabled()));
        txt_wifi_only_enabled.setText(String.valueOf(settings.isWifiOnlyEnabled()));
        txt_sync_frequency.setText(String.valueOf(settings.getSyncFrequency()));

        SharedPreferences csp = getSharedPreferences("login_pref", 0);
        txt_username.setText(csp.getString("usernameEmail", null));
        txt_home_convention_id.setText((csp.getString("homeConventionID", null)));
        txt_home_convention_name.setText((csp.getString("homeConventionName", null)));
    }
}
