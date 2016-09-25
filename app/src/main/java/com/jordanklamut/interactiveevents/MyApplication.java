package com.jordanklamut.interactiveevents;

import android.app.Application;

/**
 * Created by jorda on 9/21/2016.
 */
public class MyApplication extends Application{
    public final AppSettings settings = new AppSettings(this);

    @Override
    public void onCreate() {
        super.onCreate();
        settings.load();
    }
}
