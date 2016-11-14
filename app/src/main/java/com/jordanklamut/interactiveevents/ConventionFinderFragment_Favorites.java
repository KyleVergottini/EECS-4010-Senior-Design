package com.jordanklamut.interactiveevents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.models.Convention;

public class ConventionFinderFragment_Favorites extends Fragment {

    ArrayList<Convention> listConventions = new ArrayList<>();
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

    //GETS DATA FROM THE SQLite DATABASE
    public void initializeList() {
        listConventions.clear();

        DatabaseManager dm = new DatabaseManager(getActivity());
        Cursor res = dm.getFavoriteConventionsFromSQLite();

        String formatDate = "";
        String formatStartDate = "";
        String formatEndDate = "";
        SimpleDateFormat read = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat write = new SimpleDateFormat("MMM dd, yyyy");

        if(res.getCount() == 0) {
            Log.d("Database","No rows returned for convention finder");
        }
        else {
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext())
            {
                try{
                    formatStartDate = write.format(read.parse(res.getString(2)));
                    formatEndDate = write.format(read.parse(res.getString(3)));
                    if (formatStartDate.equals(formatEndDate))
                        formatDate = formatStartDate;
                    else formatDate = formatStartDate + " - " + formatEndDate;
                }
                catch(Exception e) {
                    formatDate = "Unknown";
                }

                Convention item = new Convention();
                item.setConID(res.getString(0));
                item.setConName(res.getString(1));
                item.setConStartDate(formatStartDate);
                item.setConEndDate(formatEndDate);
                item.setConStreetAddress(res.getString(4));
                item.setConCity(res.getString(5));
                item.setConState(res.getString(6));
                item.setConZipCode(res.getString(7));
                item.setConDescription(res.getString(8));
                item.setConFavorite(res.getString(9));

                listConventions.add(item);
            }

            //0 CON_CONVENTION_ID = "ConventionID";
            //1 CON_NAME = "Name";
            //2 CON_START_DATE = "StartDate";
            //3 CON_END_DATE = "EndDate";
            //4 CON_STREET_ADDRESS = "StreetAddress";
            //5 CON_CITY = "StreetAddress";
            //6 CON_STATE = "State";
            //7 CON_ZIP_CODE = "ZipCode";
            //8 CON_DESCRTIPION = "Description";
            //9 CON_FAVORITE?
        }
    }

    public void update()
    {
        initializeList();
        MyRecyclerView.setAdapter(new MyAdapter(listConventions));
        MyRecyclerView.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.convention_finder_fragment_favorites_recycler, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.rv_con_finder_favorites);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listConventions.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listConventions));
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
        private ArrayList<Convention> list;

        public MyAdapter(ArrayList<Convention> Data) {
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

    //VIEW HOLDER
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //title location date image
        public ImageView conPhoto;
        public TextView conName;
        public TextView conLocation;
        public TextView conDates;
        public String conStartDate;
        public String conEndDate;
        public ImageView ivFavorites;
        public TextView btnSetCon;
        public TextView btnViewDetails;
        public Convention ccv;

        public MyViewHolder(View v) {
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

                    String conID = ccv.getConID();
                    String conFavorite = ccv.getConFavorite();
                    DatabaseManager dm = new DatabaseManager(getContext());

                    if (conFavorite.equals("1")) {
                        //un-favorite event
                        ivFavorites.setTag(R.drawable.ic_like);
                        ivFavorites.setImageResource(R.drawable.ic_like);
                        dm.setConFavorite(conID, 0);
                        Toast.makeText(getContext(),"Removed from Favorites", Toast.LENGTH_SHORT).show();

                        update();
                    }
                    else if (conFavorite.equals("0")) {
                        //favorite event - should never get here
                        ivFavorites.setTag(R.drawable.ic_liked);
                        ivFavorites.setImageResource(R.drawable.ic_liked);
                        dm.setConFavorite(conID, 1);
                        Toast.makeText(getContext(),"Added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnSetCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SET CONVENTION INFO IN SHARED PREFS
                    SharedPreferences csp = getContext().getSharedPreferences("login_pref", 0);
                    SharedPreferences.Editor cEditor = csp.edit();
                    cEditor.putString("homeConventionID", ccv.getConID());
                    cEditor.putString("homeConventionName", ccv.getConName());
                    cEditor.putString("homeConventionAddress", ccv.getConStreetAddress());
                    cEditor.putString("homeConventionCity", ccv.getConCity());
                    cEditor.putString("homeConventionState", ccv.getConState());
                    cEditor.putString("homeConventionStartDate", ccv.getConStartDate());
                    cEditor.putString("homeConventionEndDate", ccv.getConEndDate());
                    cEditor.apply();

                    Toast.makeText(getContext(),"Convention Set", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), DrawerActivity.class);
                    startActivity(intent);
                }
            });

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"TODO",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
