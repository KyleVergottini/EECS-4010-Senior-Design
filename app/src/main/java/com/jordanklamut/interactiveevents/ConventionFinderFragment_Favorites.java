package com.jordanklamut.interactiveevents;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanklamut.interactiveevents.models.ConventionCardView;

import java.util.ArrayList;

public class ConventionFinderFragment_Favorites extends Fragment {

    ArrayList<ConventionCardView> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;

    public ConventionFinderFragment_Favorites() {
    }

    public static ConventionFinderFragment_Favorites newInstance() {
        ConventionFinderFragment_Favorites fragment = new ConventionFinderFragment_Favorites();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
        getActivity().setTitle("Convention Finder");
    }

    public void initializeList() {
        listitems.clear();
        DatabaseManager dm = new DatabaseManager(getActivity());
        Cursor res = dm.getAllConventionsFromSQLite();

        if(res.getCount() == 0) {
            Log.d("Database","No rows returned for convention finder");
            return;
        }
        else {
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext())
            {
                ConventionCardView item = new ConventionCardView();
                item.setCardName(res.getString(1));
                item.setCardLocation(res.getString(4) + ", " + res.getString(5));
                item.setCardDates(res.getString(2) + " - " + res.getString(3));
                item.setImageResourceId(R.drawable.ic_wallpaper_black_48dp);
                item.setIsfav(0);
                item.setIsturned(0);
                listitems.add(item);
            }

            //0 CON_CONVENTION_ID = "ConventionID";
            //1 CON_NAME = "Name";
            //2 CON_START_DATE = "StartDate";
            //3 CON_END_DATE = "EndDate";
            //4 CON_STREET_ADDRESS = "StreetAddress";
            //5 CON_STATE = "State";
            //6 CON_ZIP_CODE = "ZipCode";
            //7 CON_DESCRTIPION = "Description";

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.convention_finder_fragment_favorites_recycler, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.rv_con_finder_favorites);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    //ADAPTER
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<ConventionCardView> list;

        public MyAdapter(ArrayList<ConventionCardView> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.convention_finder_fragment_favorites, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            ConventionCardView ccv = list.get(position);

            holder.conName.setText(list.get(position).getCardName());
            holder.conLocation.setText(list.get(position).getCardLocation());
            holder.conDates.setText(list.get(position).getCardDates());
            holder.conPhoto.setImageResource(list.get(position).getImageResourceId());
            holder.conPhoto.setTag(list.get(position).getImageResourceId());
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
        public ImageView conPhoto;
        public TextView conName;
        public TextView conLocation;
        public TextView conDates;
        public ImageView ivFavorites;
        public TextView btnSetCon;
        public TextView btnViewDetails;
        public ConventionCardView ccv;

        public MyViewHolder(View v) {

            //use ccv.GETX to get card info
            super(v);
            conPhoto = (ImageView) v.findViewById(R.id.iv_convention_photo);
            conName = (TextView) v.findViewById(R.id.tv_con_name);
            conLocation = (TextView) v.findViewById(R.id.tv_con_address);
            conDates = (TextView) v.findViewById(R.id.tv_con_dates);

            ivFavorites = (ImageView) v.findViewById(R.id.iv_favorites);
            btnSetCon = (TextView) v.findViewById(R.id.btn_set_con);
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

            btnSetCon.setOnClickListener(new View.OnClickListener() {
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
