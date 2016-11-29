package com.jordanklamut.interactiveevents.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.R;
import com.jordanklamut.interactiveevents.models.Convention;

public class ConventionFinderAdapter extends RecyclerView.Adapter<ConventionFinderViewHolder>{
        private ArrayList<Convention> list;

        public ConventionFinderAdapter(ArrayList<Convention> Data) {
            list = Data;
        }

        @Override
        public ConventionFinderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.convention_finder_fragment_favorites, parent, false); //convention_finder_activity_search_results
            return new ConventionFinderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ConventionFinderViewHolder holder, int position) {
            Convention ccv = list.get(position);

            holder.conName.setText(list.get(position).getConName());

            String location = list.get(position).getConStreetAddress() + ", \n" + list.get(position).getConCity() + ", " + list.get(position).getConState();
            holder.conLocation.setText(location);

            holder.conStartDate = list.get(position).getConStartDate();
            holder.conEndDate = list.get(position).getConEndDate();
            if (!holder.conStartDate.equals(holder.conEndDate)) //more than 1 day, display start - end
                holder.conDates.setText(holder.conStartDate + " - " + holder.conEndDate);
            else holder.conDates.setText(holder.conStartDate); //just 1 day, display start date

            if (list.get(position).getConFavorite().equals("1")) {
                holder.ivFavorites.setTag(R.drawable.ic_liked);  //set tag to later check status
                holder.ivFavorites.setImageResource(R.drawable.ic_liked);
            } else {
                holder.ivFavorites.setTag(R.drawable.ic_like);  //set tag to later check status
                holder.ivFavorites.setImageResource(R.drawable.ic_like);
            }

            holder.ccv = ccv;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



