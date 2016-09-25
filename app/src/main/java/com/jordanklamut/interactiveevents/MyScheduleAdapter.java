package com.jordanklamut.interactiveevents;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by jorda on 9/14/2016.
 */
public class MyScheduleAdapter extends FragmentPagerAdapter
{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    public MyScheduleAdapter(FragmentManager fm) {
        super(fm);
    }
    //Return fragment with respect to Position .
    @Override
    public Fragment getItem(int position)
    {
        switch (position){
            case 0 :
                return new MyScheduleFragment_Tab();
            case 1 :
                return new MyScheduleFragment_Tab();
            case 2 :
                return new MyScheduleFragment_Tab();
        }
        return null;
    }
    @Override
    public int getCount() {
        return int_items;
    }
    //This method returns the title of the tab according to the position.
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Day 1";
            case 1 :
                return "Day 2";
            case 2 :
                return "Day 3";
        }
        return null;
    }
}

