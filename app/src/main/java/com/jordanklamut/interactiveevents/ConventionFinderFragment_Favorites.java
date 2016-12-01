package com.jordanklamut.interactiveevents;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.helpers.ConventionFinderAdapter;
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
        MyRecyclerView.setAdapter(new ConventionFinderAdapter(listConventions));
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
            MyRecyclerView.setAdapter(new ConventionFinderAdapter(listConventions));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}