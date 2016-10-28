package com.jordanklamut.interactiveevents;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Interactive Map");

        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Interactive Map2");

    }
}
