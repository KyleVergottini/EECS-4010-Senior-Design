package com.jordanklamut.interactiveevents.helpers;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanklamut.interactiveevents.DatabaseManager;
import com.jordanklamut.interactiveevents.MapFragment;
import com.jordanklamut.interactiveevents.R;
import com.jordanklamut.interactiveevents.models.DetailsDialog;
import com.jordanklamut.interactiveevents.models.Event;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    public TextView eventName;
    public TextView eventRoom;
    public TextView eventTime;
    public ImageView ivFavorites;
    public TextView btnViewOnMap;
    public TextView btnViewDetails;
    Context context;
    public Event ecv;

    public ScheduleViewHolder(View v) {

        super(v);
        context = v.getContext();
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
                DatabaseManager dm = new DatabaseManager(context);

                if (eventFavorite.equals("1")) {
                    //un-favorite event
                    ivFavorites.setTag(R.drawable.ic_like);
                    ivFavorites.setImageResource(R.drawable.ic_like);
                    dm.setEventFavorite(eventID, 0);
                    Toast.makeText(context,"Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
                else if (eventFavorite.equals("0")) {
                    //favorite event
                    ivFavorites.setTag(R.drawable.ic_liked);
                    ivFavorites.setImageResource(R.drawable.ic_liked);
                    dm.setEventFavorite(eventID, 1);
                    Toast.makeText(context,"Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"TODO",Toast.LENGTH_SHORT).show(); //TODO - Link to map

                final FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();

                final NavigationView navigationView = (NavigationView) ((Activity) context).findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(1).setChecked(true);
            }
        });

        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DetailsDialog().setEventDetailsDialog(context, ecv);
            }
        });
    }
}
