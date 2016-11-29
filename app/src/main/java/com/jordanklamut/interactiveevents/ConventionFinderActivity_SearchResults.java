package com.jordanklamut.interactiveevents;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jordanklamut.interactiveevents.helpers.ConventionFinderAdapter;
import com.jordanklamut.interactiveevents.models.Convention;

public class ConventionFinderActivity_SearchResults extends AppCompatActivity {

    private boolean shouldRefreshOnResume = false;
    ArrayList<Convention> listConventions = new ArrayList<>();
    RecyclerView MyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String[] value;
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

        setContentView(R.layout.convention_finder_fragment_favorites_recycler); //convention_finder_activity_search_results_recycler
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        MyRecyclerView = (RecyclerView) findViewById(R.id.rv_con_finder_favorites); //rv_con_finder_search_results
        MyRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
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

            Cursor res = dm.getSelectConventionsFromSQLite(conName, conCode, conCity, conState, conWithin, conStartDate, conEndDate);

            if(res.getCount() != 0) {
                initializeList(res);
            } else {
                return "Empty";
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

            //DISPLAY THE RESULTS
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(context);
            MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            if (listConventions.size() > 0 & MyRecyclerView != null) {
                MyRecyclerView.setAdapter(new ConventionFinderAdapter(listConventions));
            }
            //TODO - display "No Results" screen if no matching cons
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
