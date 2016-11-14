package com.jordanklamut.interactiveevents;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        setHomeConventionPage(view);
        loadFragmentManager(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void loadFragmentManager(View view) {
        final FragmentManager fm = getFragmentManager();

        final NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        TextView tvViewMap = (TextView) view.findViewById(R.id.btn_home_view_map);
        TextView tvViewSchedule = (TextView) view.findViewById(R.id.btn_home_view_schedule);
        ImageView ivNavigation = (ImageView) view.findViewById(R.id.iv_home_navigation);
        Button btnFindNewConvention = (Button) view.findViewById(R.id.btn_home_find_new_convention);

        tvViewMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();
                navigationView.getMenu().getItem(1).setChecked(true);
            }
        });

        tvViewSchedule.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.content_frame, new ScheduleFragment()).commit();
                navigationView.getMenu().getItem(2).setChecked(true);
            }
        });

        ivNavigation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
                String address = csp.getString("homeConventionAddress", null) + " \n" + csp.getString("homeConventionCity", null) + ", " + csp.getString("homeConventionState", null);

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps/?daddr=" + address));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        btnFindNewConvention.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ConventionFinderActivity.class));
            }
        });
    }

    public void setHomeConventionPage(View v) {
        SharedPreferences csp = this.getActivity().getSharedPreferences("login_pref", 0);

        TextView con_name = (TextView) v.findViewById(R.id.tv_home_con_name);
        con_name.setText(csp.getString("homeConventionName", null));

        String address = csp.getString("homeConventionAddress", null) + " \n" + csp.getString("homeConventionCity", null) + ", " + csp.getString("homeConventionState", null);
        TextView con_address = (TextView) v.findViewById(R.id.tv_home_con_address);
        con_address.setText(address);

        TextView con_dates = (TextView) v.findViewById(R.id.tv_home_con_dates);
        String conStartDate = csp.getString("homeConventionStartDate", null);
        String conEndDate = csp.getString("homeConventionEndDate", null);
        if (!conStartDate.equals(conEndDate)) //more than 1 day, display start - end
            con_dates.setText(conStartDate + " - " + conEndDate);
        else con_dates.setText(conStartDate); //just 1 day, display start date
    }
}
