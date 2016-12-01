package com.jordanklamut.interactiveevents.helpers;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.R;
import com.jordanklamut.interactiveevents.models.Event;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder>{
    private ArrayList<Event> list;

    public ScheduleAdapter(ArrayList<Event> Data) {
        list = Data;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_card, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    //BINDS THE DATA TO EACH CARD
    public void onBindViewHolder(final ScheduleViewHolder holder, int position) {
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
