package com.jordanklamut.interactiveevents;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanklamut.interactiveevents.helpers.ScheduleFragmentPager;

public class ScheduleFragment extends Fragment{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    DatabaseManager dm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Schedule");

        //Inflate tab_layout and setup Views.
        View v =  inflater.inflate(R.layout.schedule_fragment_pager,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        dm = new DatabaseManager(getContext());

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new ScheduleFragmentPager(getChildFragmentManager(), getActivity(), dm));

        return v;
    }
}
