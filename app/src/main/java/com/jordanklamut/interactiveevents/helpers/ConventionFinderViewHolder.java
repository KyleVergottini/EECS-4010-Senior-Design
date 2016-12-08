package com.jordanklamut.interactiveevents.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanklamut.interactiveevents.DatabaseManager;
import com.jordanklamut.interactiveevents.DrawerActivity;
import com.jordanklamut.interactiveevents.R;
import com.jordanklamut.interactiveevents.models.Convention;
import com.jordanklamut.interactiveevents.models.DetailsDialog;

public class ConventionFinderViewHolder extends RecyclerView.ViewHolder {
    public ImageView conPhoto;
    public TextView conName;
    public TextView conLocation;
    public TextView conDates;
    public String conStartDate;
    public String conEndDate;
    public ImageView ivFavorites;
    public TextView btnSetCon;
    public TextView btnViewDetails;
    Context context;

    public Convention ccv;

    public ConventionFinderViewHolder(View v) {

        super(v);
        context  = v.getContext();
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
                DatabaseManager dm = new DatabaseManager(context);

                if (conFavorite.equals("1")) {
                    //un-favorite event
                    ivFavorites.setTag(R.drawable.ic_like);
                    ivFavorites.setImageResource(R.drawable.ic_like);
                    dm.setConFavorite(conID, 0);
                    ccv.setConFavorite("0");
                    Toast.makeText(context,"Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
                else if (conFavorite.equals("0")) {
                    //favorite event
                    ivFavorites.setTag(R.drawable.ic_liked);
                    ivFavorites.setImageResource(R.drawable.ic_liked);
                    dm.setConFavorite(conID, 1);
                    ccv.setConFavorite("1");
                    Toast.makeText(context,"Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSetCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SET CONVENTION INFO IN SHARED PREFS
                SharedPreferences csp = context.getSharedPreferences("login_pref", 0);
                SharedPreferences.Editor cEditor = csp.edit();
                cEditor.putString("homeConventionID", ccv.getConID());
                cEditor.putString("homeConventionName", ccv.getConName());
                cEditor.putString("homeConventionAddress", ccv.getConStreetAddress());
                cEditor.putString("homeConventionCity", ccv.getConCity());
                cEditor.putString("homeConventionState", ccv.getConState());
                cEditor.putString("homeConventionStartDate", ccv.getConStartDate());
                cEditor.putString("homeConventionEndDate", ccv.getConEndDate());
                cEditor.apply();


                new GetAllEventsPHPtoSQLite().execute();

                Toast.makeText(context,"Convention Set", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DrawerActivity.class);
                context.startActivity(intent);
            }
        });

        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DetailsDialog().setEventDetailsDialog(context, ccv);
            }
        });
    }

    //EXECUTES ON SET CON CLICK - GETS ALL EVENTS FROM PHP MATCHING CONVENTION ID, CREATES SQLite AND RETURNS
    private class GetAllEventsPHPtoSQLite extends AsyncTask<String, Void, String> {
        DatabaseManager dm;
        ProgressDialog pd;

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences csp = context.getSharedPreferences("login_pref", 0);
            dm = new DatabaseManager(context);

            dm.clearEventsTable();
            dm.clearRoomsTable();


            dm.setEventList(context, csp.getString("homeConventionID", null)); //gets from php, and inserts into sqlite
            dm.setRoomList(context, csp.getString("homeConventionID", null)); //gets from php, and inserts into sqlite //TODO - should get latest Room table changes too
            dm.setMapList(context, csp.getString("homeConventionID", null));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setTitle("Please Wait...");
            pd.setMessage("Gettings Events...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


}
