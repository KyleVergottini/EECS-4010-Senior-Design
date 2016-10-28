package com.jordanklamut.interactiveevents;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.jordanklamut.interactiveevents.models.ConventionCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConventionFinderActivity_SearchResults extends AppCompatActivity {

    ArrayList<ConventionCardView> listConventions = new ArrayList<>();
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
        //LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        //MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //if (listConventions.size() > 0 & MyRecyclerView != null) {
        //    MyRecyclerView.setAdapter(new MyAdapter(listConventions));
        //}
        //MyRecyclerView.setLayoutManager(MyLayoutManager);
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

    public void initializeList(Cursor res) {
        listConventions.clear();
        DatabaseManager dm = new DatabaseManager(this);
        //Cursor res = dm.getAllConventionsFromSQLite();

        String formatDate = "";
        SimpleDateFormat read = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat write = new SimpleDateFormat("MMM dd, yyyy");

        if(res.getCount() == 0) {
            Log.d("Database","No rows returned for convention finder");
            return;
        }
        else {
            StringBuffer buffer = new StringBuffer();
            //create cards for each row in DB
            while (res.moveToNext())
            {
                try{
                    String formatStartDate = write.format(read.parse(res.getString(2)));
                    String formatEndDate = write.format(read.parse(res.getString(3)));
                    if (formatStartDate.equals(formatEndDate))
                        formatDate = formatStartDate;
                    else formatDate = formatStartDate + " - " + formatEndDate;
                }
                catch(Exception e) {
                    formatDate = "Unknown";
                }

                ConventionCardView item = new ConventionCardView();
                item.setCardID(res.getString(0));
                item.setCardName(res.getString(1));
                item.setCardLocation(res.getString(4) + ", \n" + res.getString(5) + ", " + res.getString(6) );
                item.setCardDates(formatDate);
                item.setImageResourceId(R.drawable.ic_wallpaper_black_48dp);
                item.setIsfav(0);
                item.setIsturned(0);
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
        }
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
                    .inflate(R.layout.convention_finder_activity_search_results, parent, false);
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
            holder.mCardView.setTag(position);

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
        public CardView mCardView;
        public ImageView conPhoto;
        public TextView conName;
        public TextView conLocation;
        public TextView conDates;
        public ImageView ivFavorites;
        public TextView btnSetCon;
        public TextView btnViewDetails;

        public ConventionCardView ccv;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view); //test
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
                    int id = (int)ivFavorites.getTag();
                    if( id == R.drawable.ic_like){
                        ivFavorites.setTag(R.drawable.ic_liked);
                        ivFavorites.setImageResource(R.drawable.ic_liked);
                        Toast.makeText(getApplicationContext(),"Added to Favorites", Toast.LENGTH_SHORT).show();
                    }else{
                        ivFavorites.setTag(R.drawable.ic_like);
                        ivFavorites.setImageResource(R.drawable.ic_like);
                        Toast.makeText(getApplicationContext(),"Removed from Favorites",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnSetCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SET CONVENTION INFO IN SHARED PREFS
                    SharedPreferences csp = getApplicationContext().getSharedPreferences("login_pref", 0);
                    SharedPreferences.Editor cEditor = csp.edit();
                    cEditor.putString("homeConventionID", ccv.getCardID());
                    cEditor.putString("homeConventionName", ccv.getCardName());
                    cEditor.putString("homeConventionAddress", ccv.getCardLocation());
                    cEditor.putString("homeConventionDates", ccv.getCardDates());
                    cEditor.apply();

                    Toast.makeText(getApplicationContext(),"Convention Set: " + ccv.getCardID(),Toast.LENGTH_SHORT).show();

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
        Cursor res;

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
                Thread.sleep(1000);
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

            //Toast.makeText(getApplicationContext(),"conName: " + conName,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"conCode: " + conCode,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"conCity: " + conCity,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"conState: " + conState,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"conWithin: " + conWithin,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"conStartDate: " + conStartDate,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"conEndDate: " + conEndDate,Toast.LENGTH_SHORT).show();

            Toast.makeText(getApplicationContext(),"SEARCHED CONVENTIONS FROM SQLITE",Toast.LENGTH_SHORT).show();
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
