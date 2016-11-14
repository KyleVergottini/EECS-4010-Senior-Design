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
        View x =  inflater.inflate(R.layout.schedule_fragment_pager,null); //TODO
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            for (int i = 0; i <= position; i++)
                return ScheduleFragment_Tab.newInstance(position, "Day " + position);
            return null;
        }

        @Override
        public int getCount() {

            SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
            String conID =  csp.getString("homeConventionID", null);

            DatabaseManager dm = new DatabaseManager(getActivity());
            int days = dm.getConventionDates(conID);

            //int_items = 5; //TODO - SET TO NUMBER OF DAYS
            return days;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            for (int i = 0; i <= position; i++)
                return "Day " + position;
            return null;
        }
    }
}
