package com.jordanklamut.interactiveevents;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.databinding.DataBindingUtil;
import com.jordanklamut.interactiveevents.databinding.MyScheduleEventCardBinding;
import com.jordanklamut.interactiveevents.models.Event;
//import android.support.v7.widget.CardView;
//import android.widget.TextView;

import java.util.List;

public class MyScheduleCardAdapter extends RecyclerView.Adapter<MyScheduleCardAdapter.MyViewHolder>{

    //private String[] mDataset;
    private List<Event> mEvent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //public CardView mCardView;
        //public TextView mTextView;

        private MyScheduleEventCardBinding listItemBinding;
        public MyViewHolder(View v) {
            super(v);
            listItemBinding = DataBindingUtil.bind(v);
        }

        public MyScheduleEventCardBinding getBinding(){
            return listItemBinding;
        }
    }

    public MyScheduleCardAdapter(List<Event> event) {
        mEvent = event;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyScheduleCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_schedule_event_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Event event = mEvent.get(position);
        holder.getBinding().setVariable(com.jordanklamut.interactiveevents.BR.event, event);
        holder.getBinding().executePendingBindings();

        //holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mEvent.size();
        //return mDataset.length;
    }
}
