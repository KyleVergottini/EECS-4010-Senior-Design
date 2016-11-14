package com.jordanklamut.interactiveevents;

import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.models.Event;

public class ScheduleFragment_Tab extends Fragment{

    private OnFragmentInteractionListener mListener;
    ArrayList<Event> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
    DatabaseManager dm;

    public ScheduleFragment_Tab() {

    }

    public static ScheduleFragment_Tab newInstance(int pageNumber, String pageTitle) {
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
        dm = new DatabaseManager(this.getActivity());
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
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
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
                item.setEventRoomID(res.getString(1));
                item.setEventName(res.getString(2));
                item.setEventDate(res.getString(3));
                item.setEventStartTime(res.getString(4));
                item.setEventEndTime(res.getString(5));
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

    //ADAPTER
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<Event> list;

        public MyAdapter(ArrayList<Event> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        //BINDS THE DATA TO EACH CARD
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            Event ecv = list.get(position);

            holder.eventName.setText(list.get(position).getEventName());
            holder.eventRoom.setText(list.get(position).getEventRoomID());
            holder.eventTime.setText(list.get(position).getEventStartTime() + " - " + list.get(position).getEventEndTime());

            if (list.get(position).getEventFavorite().equals("1")) {
                holder.ivFavorites.setTag(R.drawable.ic_liked);  //set tag to later check status
                holder.ivFavorites.setImageResource(R.drawable.ic_liked);
            } else {
                holder.ivFavorites.setTag(R.drawable.ic_like);  //set tag to later check status
                holder.ivFavorites.setImageResource(R.drawable.ic_like);
            }

            holder.ecv = ecv;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //VIEW HOLDER
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView eventRoom;
        public TextView eventTime;
        public ImageView ivFavorites;
        public TextView btnViewOnMap;
        public TextView btnViewDetails;

        public Event ecv;

        public MyViewHolder(View v) {

            super(v);
            eventName = (TextView) v.findViewById(R.id.tv_event_name);
            eventRoom = (TextView) v.findViewById(R.id.tv_event_room);
            eventTime = (TextView) v.findViewById(R.id.tv_event_time);
            ivFavorites = (ImageView) v.findViewById(R.id.iv_schedule_favorites);
            btnViewOnMap = (TextView) v.findViewById(R.id.btn_view_on_map);
            btnViewDetails = (TextView) v.findViewById(R.id.btn_view_details);

            ivFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String eventID = ecv.getEventID();
                    String eventFavorite = ecv.getEventFavorite();
                    DatabaseManager dm = new DatabaseManager(getActivity());

                    if (eventFavorite.equals("1")) {
                        //un-favorite event
                        ivFavorites.setTag(R.drawable.ic_like);
                        ivFavorites.setImageResource(R.drawable.ic_like);
                        dm.setEventFavorite(eventID, 0);
                        Toast.makeText(getActivity(),"Removed from Favorites", Toast.LENGTH_SHORT).show();
                    }
                    else if (eventFavorite.equals("0")) {
                        //favorite event
                        ivFavorites.setTag(R.drawable.ic_liked);
                        ivFavorites.setImageResource(R.drawable.ic_liked);
                        dm.setEventFavorite(eventID, 1);
                        Toast.makeText(getActivity(),"Added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnViewOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"TODO",Toast.LENGTH_SHORT).show(); //TODO - Link to map
                }
            });

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"TODO",Toast.LENGTH_SHORT).show(); //TODO - Set Details page
                }
            });
        }
    }
}
