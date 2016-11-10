package com.jordanklamut.interactiveevents;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.jordanklamut.interactiveevents.models.EventCardView;

public class MyScheduleFragment_Tab extends Fragment {

    private OnFragmentInteractionListener mListener;
    ArrayList<EventCardView> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;

    public MyScheduleFragment_Tab() {

    }

    public static MyScheduleFragment_Tab newInstance(int pageNumber, String pageTitle) {
        MyScheduleFragment_Tab fragment = new MyScheduleFragment_Tab();
        Bundle args = new Bundle();
        args.putInt("someInt", pageNumber);
        args.putString("someTitle", pageTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
        getActivity().setTitle("My Schedule");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_schedule_fragment_recycler, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.rv_my_schedule_events);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyScheduleFragment_Tab.MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    //TODO - CHANGE TO FAVORITED EVENTS
    public void initializeList() {
        listitems.clear();
        DatabaseManager dm = new DatabaseManager(getActivity());
        Cursor res = dm.getAllEventsFromSQLite();

        if(res.getCount() == 0) {
            Log.d("Database","No events for this convention");
        }
        else {
            while (res.moveToNext())
            {
                EventCardView item = new EventCardView();
                item.setCardName(res.getString(2));
                item.setCardRoom(res.getString(1));
                item.setCardTimes(res.getString(4) + " - " + res.getString(5));
                item.setIsFav(0);
                //item.setIsTurned(0);
                listitems.add(item);
            }

            //0 EVENT_EVENT_ID
            //1 EVENT_ROOM_ID
            //2 EVENT_NAME
            //3 EVENT_EVENT_DATE
            //4 EVENT_START_TIME
            //5 EVENT_END_TIME
            //6 EVENT_DESCRIPTION
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
        private ArrayList<EventCardView> list;

        public MyAdapter(ArrayList<EventCardView> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            EventCardView ccv = list.get(position);

            holder.eventName.setText(list.get(position).getCardName());
            holder.eventRoom.setText(list.get(position).getCardRoom());
            holder.eventTime.setText(list.get(position).getCardTimes());
            holder.ivFavorites.setTag(R.drawable.ic_like);

            holder.ccv = ccv;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //VIEW HOLDER
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //title location date image
        public TextView eventName;
        public TextView eventRoom;
        public TextView eventTime;
        public ImageView ivFavorites;
        public TextView btnViewOnMap;
        public TextView btnViewDetails;
        public EventCardView ccv;

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
                    int id = (int)ivFavorites.getTag();
                    if( id == R.drawable.ic_like){
                        ivFavorites.setTag(R.drawable.ic_liked);
                        ivFavorites.setImageResource(R.drawable.ic_liked);
                        Toast.makeText(getActivity(),"Added to Favorites", Toast.LENGTH_SHORT).show();
                    }else{
                        ivFavorites.setTag(R.drawable.ic_like);
                        ivFavorites.setImageResource(R.drawable.ic_like);
                        Toast.makeText(getActivity(),"Removed from Favorites",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnViewOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"TODO",Toast.LENGTH_SHORT).show();
                }
            });

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"TODO",Toast.LENGTH_SHORT).show();
                }
            });
//
            //shareImageView.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {
            //        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
            //                "://" + getResources().getResourcePackageName(coverImageView.getId())
            //                + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));
//
            //        Intent shareIntent = new Intent();
            //        shareIntent.setAction(Intent.ACTION_SEND);
            //        shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
            //        shareIntent.setType("image/jpeg");
            //        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            //    }
            //});
        }
    }

}