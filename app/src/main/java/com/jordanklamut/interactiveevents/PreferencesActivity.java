package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.view.MenuItem;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity {

    public static void startThisActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PreferencesActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        private AppSettings settings;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            //Notification Reminder
            Preference notificationReminder = findPreference("notifications_reminder");
            notificationReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(String.valueOf(newValue));
                    return true;
                }
            });

            //Ringtone
            Preference notificationRingtone = findPreference("notification_ringtone");
            notificationRingtone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(String.valueOf(newValue));
                    return true;
                }
            });

            //Sync Frequency
            Preference syncFrequency = findPreference("sync_frequency");
            syncFrequency.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(String.valueOf(newValue));
                    return true;
                }
            });

            Preference logout = findPreference("logout");
            logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_LONG).show();

                    //remove username from login_pref
                    SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
                    SharedPreferences.Editor cEditor = csp.edit();
                    cEditor.clear().apply();

                    startActivity(new Intent(getActivity(), LogInActivity.class));
                    getActivity().finish();
                    return false;
                }
            });
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            settings = AppSettings.getSettings(getActivity());

            Preference notificationsReminder = findPreference("notifications_reminder");
            notificationsReminder.setSummary(settings.getNotificationReminder());

            Preference notificationRingtone = findPreference("notification_ringtone");
            notificationRingtone.setSummary(settings.getNotificationRingtone());

            Preference syncFrequency = findPreference("sync_frequency");
            syncFrequency.setSummary(settings.getSyncFrequency());
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
        }

        private final SharedPreferences.OnSharedPreferenceChangeListener sharedPrefsChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                settings.load();
            }
        };
    }
}
