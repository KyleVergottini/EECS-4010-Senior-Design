package com.jordanklamut.interactiveevents;

import android.content.SharedPreferences;
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

import com.jordanklamut.interactiveevents.helpers.MyScheduleFragmentPager;

public class MyScheduleFragment extends Fragment{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    DatabaseManager dm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("My Schedule");

        //Inflate tab_layout and setup Views.
        View v =  inflater.inflate(R.layout.my_schedule_fragment_pager,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        dm = new DatabaseManager(getContext());

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new MyScheduleFragmentPager(getChildFragmentManager(), getActivity(), dm));

        return v;
    }
}
