package com.jordanklamut.interactiveevents;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView tvViewMap = (TextView) view.findViewById(R.id.btn_home_view_map);
        TextView tvViewSchedule = (TextView) view.findViewById(R.id.btn_home_view_schedule);
        ImageView ivNavigation = (ImageView) view.findViewById(R.id.iv_home_navigation);
        Button btnFindNewConvention = (Button) view.findViewById(R.id.btn_home_find_new_convention);

        tvViewMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();
            }
        });

        tvViewSchedule.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.content_frame, new ScheduleFragment()).commit();
            }
        });

        ivNavigation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
                String address = csp.getString("homeConventionAddress", null);

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

        TextView con_address = (TextView) v.findViewById(R.id.tv_home_con_address);
        con_address.setText(csp.getString("homeConventionAddress", null));

        TextView con_dates = (TextView) v.findViewById(R.id.tv_home_con_dates);
        con_dates.setText(csp.getString("homeConventionDates", null));
    }
}
