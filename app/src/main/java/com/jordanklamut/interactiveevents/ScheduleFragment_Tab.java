package com.jordanklamut.interactiveevents;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.helpers.ScheduleAdapter;
import com.jordanklamut.interactiveevents.models.Event;

public class ScheduleFragment_Tab extends Fragment{

    private OnFragmentInteractionListener mListener;
    ArrayList<Event> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
    static DatabaseManager dm;

    public ScheduleFragment_Tab() {

    }

    public static ScheduleFragment_Tab newInstance(int pageNumber, String pageTitle, DatabaseManager dManager) {
        dm = dManager;
        ScheduleFragment_Tab fragment = new ScheduleFragment_Tab();
        Bundle args = new Bundle();
        args.putInt("someInt", pageNumber);
        args.putString("someTitle", pageTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dm = new DatabaseManager(this.getActivity());
        initializeList();
        getActivity().setTitle("Schedule");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment_recycler, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.rv_schedule_events);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new ScheduleAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    //GETS DATA FROM THE SQLite DATABASE
    public void initializeList() {
        listitems.clear();
        //DatabaseManager dm = new DatabaseManager(getActivity());
        Cursor res = dm.getAllEventsFromSQLite();

        if(res.getCount() == 0) {
            Log.d("Database","No events for this convention");
        }
        else {
            while (res.moveToNext())
            {
                Event item = new Event();
                item.setEventID(res.getString(0));

                //GET ROOM ROW MATCHING THE ROOM_ID
                Cursor roomRes = dm.getSelectRoomsFromSQLite(res.getString(1));
                roomRes.moveToFirst();

                //0 ROOM_ROOM_ID
                //1 ROOM_CONVENTION_ID
                //2 ROOM_NAME
                //3 ROOM_LEVEL
                //4 ROOM_X_COORDINATE
                //5 ROOM_Y_COORDINATE

                item.setEventRoomID(roomRes.getString(2) + "    (Floor: " + roomRes.getString(3) + ")");
                item.setEventName(res.getString(2));
                item.setEventDate(res.getString(3));

                //CONVERT TO AM/PM TIMES
                String formatStartTime = "";
                String formatEndTime = "";
                SimpleDateFormat read = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat write = new SimpleDateFormat("h:mm a");

                try {
                    formatStartTime = write.format(read.parse(res.getString(4)));
                    formatEndTime = write.format(read.parse(res.getString(5))); //res.getString(5)
                }
                catch(Exception e) {
                    formatStartTime = "?";
                    formatEndTime = "?";
                }

                item.setEventStartTime(formatStartTime); //res.getString(4)
                item.setEventEndTime(formatEndTime);//res.getString(5)
                item.setEventDescription(res.getString(6));

                item.setEventFavorite(res.getString(7));
                listitems.add(item);
            }

            //0 EVENT_EVENT_ID
            //1 EVENT_ROOM_ID
            //2 EVENT_NAME
            //3 EVENT_EVENT_DATE
            //4 EVENT_START_TIME
            //5 EVENT_END_TIME
            //6 EVENT_DESCRIPTION
            //7 EVENT_FAVORITE?
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
