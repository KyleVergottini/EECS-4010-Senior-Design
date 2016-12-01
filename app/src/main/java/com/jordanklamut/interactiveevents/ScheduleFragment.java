package com.jordanklamut.interactiveevents;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScheduleFragment extends Fragment{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Schedule");

        //Inflate tab_layout and setup Views.
        View v =  inflater.inflate(R.layout.schedule_fragment_pager,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        return v;
    }

    class MyAdapter extends FragmentPagerAdapter{

        DatabaseManager dm;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            dm = new DatabaseManager(getActivity());
        }

        @Override
        public Fragment getItem(int position) {
            for (int i = 0; i <= position; i++)
                return ScheduleFragment_Tab.newInstance(position, "Day " + position, dm);
            return null;
        }

        @Override
        public int getCount() {
            SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
            String conID =  csp.getString("homeConventionID", null);

            DatabaseManager dm = new DatabaseManager(getActivity());
            int days = dm.getConventionDates(conID) + 1;
            return days;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //TODO - get start date of event and set titles, comparing with current day to set Today, Tomorrow, etc titles
            for (int i = 0; i <= position; i++)
                return "Day " + position + 1;
            return null;
        }
    }
}
