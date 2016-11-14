package com.jordanklamut.interactiveevents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.models.Convention;

public class ConventionFinderActivity_SearchResults extends AppCompatActivity {

    private boolean shouldRefreshOnResume = false;
    ArrayList<Convention> listConventions = new ArrayList<>();
    RecyclerView MyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String[] value = null;
        if (bundle == null) {
            //TODO: THROW ERROR

        }

        value = bundle.getStringArray("conInfo");

        String conName = value[0];
        String conCode = value[1];
        String conCity = value[2];
        String conState = value[3];
        String conWithin = value[4];
        String conStartDate = value[5];
        String conEndDate = value[6];

        //FORMAT DATES
        SimpleDateFormat read = new SimpleDateFormat("M/d/yyyy");
        SimpleDateFormat write = new SimpleDateFormat("yyyy-MM-dd");

        try{
            if (!conStartDate.matches(""))
            conStartDate = write.format(read.parse(conStartDate));
            if (!conEndDate.matches(""))
            conEndDate = write.format(read.parse(conEndDate));
        }
        catch(Exception e) {
            conStartDate = conEndDate = "";
        }

        //QUERY
        GetConventionsFromSQLite aSync = new GetConventionsFromSQLite(this);
        aSync.execute(conName, conCode, conCity, conState, conWithin, conStartDate, conEndDate);

        setContentView(R.layout.convention_finder_activity_search_results_recycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        MyRecyclerView = (RecyclerView) findViewById(R.id.rv_con_finder_search_results);
        MyRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check should we need to refresh the fragment
        if(shouldRefreshOnResume){
            // refresh fragment
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //GETS DATA FROM THE SQLite DATABASE
    public void initializeList(Cursor res) {
        listConventions.clear();

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
                    .inflate(R.layout.convention_finder_activity_search_results, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        //BINDS THE DATA TO EACH CARD
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
            conPhoto = (ImageView) v.findViewById(R.id.iv_results_convention_photo);
            conName = (TextView) v.findViewById(R.id.tv_results_con_name);
            conLocation = (TextView) v.findViewById(R.id.tv_results_con_address);
            conDates = (TextView) v.findViewById(R.id.tv_results_con_dates);
            ivFavorites = (ImageView) v.findViewById(R.id.iv_results_favorites);
            btnSetCon = (TextView) v.findViewById(R.id.btn_results_set_con);
            btnViewDetails = (TextView) v.findViewById(R.id.btn_results_view_details);

            ivFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String conID = ccv.getConID();
                    String conFavorite = ccv.getConFavorite();
                    DatabaseManager dm = new DatabaseManager(getApplicationContext());

                    if (conFavorite.equals("1")) {
                        //un-favorite event
                        ivFavorites.setTag(R.drawable.ic_like);
                        ivFavorites.setImageResource(R.drawable.ic_like);
                        dm.setConFavorite(conID, 0);
                        Toast.makeText(getApplicationContext(),"Removed from Favorites", Toast.LENGTH_SHORT).show();
                    }
                    else if (conFavorite.equals("0")) {
                        //favorite event
                        ivFavorites.setTag(R.drawable.ic_liked);
                        ivFavorites.setImageResource(R.drawable.ic_liked);
                        dm.setConFavorite(conID, 1);
                        Toast.makeText(getApplicationContext(),"Added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnSetCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SET CONVENTION INFO IN SHARED PREFS
                    SharedPreferences csp = getApplicationContext().getSharedPreferences("login_pref", 0);
                    SharedPreferences.Editor cEditor = csp.edit();
                    cEditor.putString("homeConventionID", ccv.getConID());
                    cEditor.putString("homeConventionName", ccv.getConName());
                    cEditor.putString("homeConventionAddress", ccv.getConStreetAddress());
                    cEditor.putString("homeConventionCity", ccv.getConCity());
                    cEditor.putString("homeConventionState", ccv.getConState());
                    cEditor.putString("homeConventionStartDate", ccv.getConStartDate());
                    cEditor.putString("homeConventionEndDate", ccv.getConEndDate());
                    cEditor.apply();

                    Toast.makeText(getApplicationContext(),"Convention Set", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                    startActivity(intent);
                }
            });

            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"TODO",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //EXECUTES ON SEARCH CLICK - QUERY SQLite DB AND RETURNS RESULTS
    private class GetConventionsFromSQLite extends AsyncTask<String, String, String> {
        DatabaseManager dm;
        ProgressDialog pd;
        Context context;

        public GetConventionsFromSQLite(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {

            String conName = params[0];
            String conCode = params[1];
            String conCity = params[2];
            String conState = params[3];
            String conWithin = params[4];
            String conStartDate = params[5];
            String conEndDate = params[6];

            dm = new DatabaseManager(getApplicationContext());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            //TODO - QUERY SQLite FOR CONVETNIONS
            Cursor res = dm.getSelectConventionsFromSQLite(conName, conCode, conCity, conState, conWithin, conStartDate, conEndDate);

            if(res.getCount() != 0) {
                initializeList(res);
            } else {
                return "Empty";
                //Toast.makeText(getApplicationContext(),"No conventions found",Toast.LENGTH_SHORT).show();
            }

            return "Executed";
        }

        @Override
        //GETS ALL THE CONVENTIONS FROM SQLite
        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(),"SEARCHED CONVENTIONS FROM SQLITE",Toast.LENGTH_SHORT).show();
            pd.dismiss();

            //DISPLAY THE RESULTS
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(context);
            MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            if (listConventions.size() > 0 & MyRecyclerView != null) {
                MyRecyclerView.setAdapter(new MyAdapter(listConventions));
            }
            MyRecyclerView.setLayoutManager(MyLayoutManager);

        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setTitle("Please Wait...");
            pd.setMessage("Finding Conventions...");
            pd.setCancelable(false);
            pd.show();
        }
    }


}
