package com.jordanklamut.interactiveevents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanklamut.interactiveevents.models.Event;

import java.util.List;

/**
 * Created by jorda on 9/14/2016.
 */

public class MyScheduleFragment_Tab extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    public MyScheduleFragment_Tab() {

    }

    public static MyScheduleFragment_Tab newInstance() {
        MyScheduleFragment_Tab fragment = new MyScheduleFragment_Tab();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("My Schedule");
        //return inflater.inflate(R.layout.schedule_tab_fragment, container, false);
        //return inflater.inflate(R.layout.my_schedule_tab_fragment, null);

        View rootView = inflater.inflate(R.layout.my_schedule_tab_fragment, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.my_schedule_rv);
        rv.setHasFixedSize(true);
        //MyScheduleCardAdapter adapter = new MyScheduleCardAdapter(new String[]{"test one", "test two", "test three", "test four", "test five" , "test six" , "test seven"});
        //rv.setAdapter(adapter);

        List<Event> event = util.getEventList(getContext());
        MyScheduleCardAdapter adapter2 = new MyScheduleCardAdapter(event);
        rv.setAdapter(adapter2);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}