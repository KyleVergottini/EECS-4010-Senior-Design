package com.jordanklamut.interactiveevents.helpers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jordanklamut.interactiveevents.DatabaseManager;
import com.jordanklamut.interactiveevents.ScheduleFragment_Tab;

public class ScheduleFragmentPager extends FragmentPagerAdapter {

    DatabaseManager dm;
    Activity a;

    public ScheduleFragmentPager(FragmentManager fm, Activity activity, DatabaseManager databaseManager) {
        super(fm);
        a = activity;
        dm = databaseManager;
    }

    @Override
    public Fragment getItem(int position) {
        for (int i = 0; i <= position; i++)
            return ScheduleFragment_Tab.newInstance(position, "Day " + position, dm);
        return null;
    }

    @Override
    public int getCount() {
        SharedPreferences csp = a.getSharedPreferences("login_pref", 0);
        String conID =  csp.getString("homeConventionID", null);

        DatabaseManager dm = new DatabaseManager(a);
        int days = dm.getConventionDates(conID) + 1;
        return days;
    }

    @Override
    public String getPageTitle(int position) {
        SharedPreferences csp = a.getSharedPreferences("login_pref", 0);
        String conID =  csp.getString("homeConventionID", null);

        SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOut = new SimpleDateFormat("EEE, MMM d");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar today  = Calendar.getInstance();

        try {
            start.setTime(sdfIn.parse(dm.getConventionStartDate(conID)));
            end.setTime(sdfIn.parse(dm.getConventionEndDate(conID)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= position; i++)
        {
            start.roll(Calendar.DATE, position);
            String formatted = sdfOut.format(start.getTime());

            if (sdfOut.format(today.getTime()).equals(sdfOut.format(start.getTime())))
                return "Today";
            today.roll(Calendar.DATE, 1);
            if (sdfOut.format(today.getTime()).equals(sdfOut.format(start.getTime())))
                return "Tomorrow";

            return formatted;
        }
        return null;
    }
}
