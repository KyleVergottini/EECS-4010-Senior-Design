package com.jordanklamut.interactiveevents;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by jorda on 9/21/2016.
 */
public class PreferencesFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }
}
