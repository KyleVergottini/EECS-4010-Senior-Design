package com.jordanklamut.interactiveevents;

import android.content.SharedPreferences;

public class Settings {

    private boolean notificationsEnabled;
    private String notificationReminder;
    private String notificationRingtone;
    private boolean notificationVibrateEnabled;
    private boolean wifiOnlyEnabled;
    private String syncFrequency;

    private static final String NOTIFICATIONS_ENABLED_KEY = "notifications_enabled";
    private static final String NOTIFICATION_REMINDER_KEY = "notifications_reminder";
    private static final String NOTIFICATION_RINGTONE_KEY = "notification_ringtone";
    private static final String NOTIFICATION_VIBRATE_ENABLED_KEY = "notification_vibrate_enabled";
    private static final String WIFI_ONLY_ENABLED_KEY = "wifi_only_enabled";
    private static final String SYNC_FREQUENCY_KEY = "sync_frequency";

    public boolean isNotificationsEnabled(){
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled){
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getNotificationReminder() {
        return notificationReminder;
    }

    public void setNotificationReminder(String notificationReminder){
        this.notificationReminder = notificationReminder;
    }

    public String getNotificationRingtone(){
        return notificationRingtone;
    }

    public void setNotificationRingtone(String notificationRingtone){
        this.notificationRingtone = notificationRingtone;
    }

    public boolean isNotificationVibrateEnabled() {
        return notificationVibrateEnabled;
    }

    public void setNotificationVibrateEnabled(boolean notificationVibrateEnabled) {
        this.notificationVibrateEnabled = notificationVibrateEnabled;
    }

    public boolean isWifiOnlyEnabled() {
        return wifiOnlyEnabled;
    }

    public void setWifiOnlyEnabled(boolean wifiOnlyEnabled) {
        this.wifiOnlyEnabled = wifiOnlyEnabled;
    }

    public String getSyncFrequency() {
        return syncFrequency;
    }

    public void setSyncFrequency(String syncFrequency) {
        this.syncFrequency = syncFrequency;
    }

    public void load(SharedPreferences prefs) {
        notificationsEnabled = prefs.getBoolean(NOTIFICATIONS_ENABLED_KEY, true);
        notificationReminder = prefs.getString(NOTIFICATION_REMINDER_KEY, "15");
        notificationRingtone = prefs.getString(NOTIFICATION_RINGTONE_KEY, "");
        notificationVibrateEnabled = prefs.getBoolean(NOTIFICATION_VIBRATE_ENABLED_KEY, true);
        wifiOnlyEnabled = prefs.getBoolean(WIFI_ONLY_ENABLED_KEY, false);
        syncFrequency = prefs.getString(SYNC_FREQUENCY_KEY, "10");
    }

    public void save(SharedPreferences.Editor editor) {
        editor.putBoolean(NOTIFICATIONS_ENABLED_KEY, notificationsEnabled);
        editor.putString(NOTIFICATION_REMINDER_KEY, notificationReminder);
        editor.putString(NOTIFICATION_RINGTONE_KEY, notificationRingtone);
        editor.putBoolean(NOTIFICATION_VIBRATE_ENABLED_KEY, notificationVibrateEnabled);
        editor.putBoolean(WIFI_ONLY_ENABLED_KEY, wifiOnlyEnabled);
        editor.putString(SYNC_FREQUENCY_KEY, syncFrequency);
    }

    public void save(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        save(editor);
        editor.commit();
    }

    public void saveDeferred(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        save(editor);
        editor.apply();
    }
}