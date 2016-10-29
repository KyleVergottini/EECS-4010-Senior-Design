package com.jordanklamut.interactiveevents;

import android.app.Application;

public class MyApplication extends Application{
    public final AppSettings settings = new AppSettings(this);

    @Override
    public void onCreate() {
        super.onCreate();
        settings.load();
    }
}
