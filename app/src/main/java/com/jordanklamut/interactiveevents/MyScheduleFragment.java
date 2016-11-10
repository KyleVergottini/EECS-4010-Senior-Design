package com.jordanklamut.interactiveevents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyScheduleFragment extends Fragment{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("My Schedule");

        //Inflate tab_layout and setup Views.
        View x =  inflater.inflate(R.layout.my_schedule_fragment_pager,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            for (int i = 0; i <= position; i++)
                return MyScheduleFragment_Tab.newInstance(position, "Day " + position);
            return null;
        }

        @Override
        public int getCount() {
            int_items = 2; //TODO - SET TO NUMBER OF DAYS
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            for (int i = 0; i <= position; i++)
                return "Day " + position;
            return null;
        }
    }


}
