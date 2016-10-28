package com.jordanklamut.interactiveevents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jordanklamut.interactiveevents.models.Convention;
import com.jordanklamut.interactiveevents.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DatabaseManager extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "IE.db";

    public static final String CON_TABLE_NAME = "Conventions";
        public static final String CON_CONVENTION_ID = "ConventionID";
        public static final String CON_NAME = "Name";
        public static final String CON_START_DATE = "StartDate";
        public static final String CON_END_DATE = "EndDate";
        public static final String CON_STREET_ADDRESS = "StreetAddress";
        public static final String CON_CITY = "City";
        public static final String CON_STATE = "State";
        public static final String CON_ZIP_CODE = "ZipCode";
        public static final String CON_DESCRTIPION = "Description";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("Database","Database Created: " + DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE " + CON_TABLE_NAME + " ("
                + CON_CONVENTION_ID + " INTEGER, "
                + CON_NAME + " TEXT, "
                + CON_START_DATE + " TEXT, "
                + CON_END_DATE + " TEXT, "
                + CON_STREET_ADDRESS + " TEXT, "
                + CON_CITY + " TEXT, "
                + CON_STATE + " TEXT, "
                + CON_ZIP_CODE + " TEXT, "
                + CON_DESCRTIPION +" TEXT)";
        database.execSQL(query);
        Log.d("Database","Table created: " + CON_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + CON_TABLE_NAME;
        database.execSQL(query);
        onCreate(database);
    }

    public void insertConventionToSQLite(Convention con) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor res = db.rawQuery("SELECT * FROM " + CON_TABLE_NAME + " WHERE " + CON_CONVENTION_ID + " LIKE " + con.getConID(), null);

        values.put(CON_CONVENTION_ID, con.getConID());
        values.put(CON_NAME, con.getConName());
        values.put(CON_START_DATE, con.getConStartDate());
        values.put(CON_END_DATE, con.getConEndDate());
        values.put(CON_STREET_ADDRESS, con.getConStreetAddress());
        values.put(CON_CITY, con.getConCity());
        values.put(CON_STATE, con.getConState());
        values.put(CON_ZIP_CODE, con.getConZipCode());
        values.put(CON_DESCRTIPION, con.getConDescription());

        if (res.getCount() == 0) {
            //IF CONVENTION DOESNT EXIST - ADD IT
            db.insert(CON_TABLE_NAME, null, values);
            db.close();
            Log.d("Database", "Con inserted: " + con.getConName());
        } else {
            //ELSE CONVENTION ALREADY EXISTS - UPDATE IT
            db.update(CON_TABLE_NAME, values, CON_CONVENTION_ID + " = ?", new String[] {con.getConID()});
            db.close();
            Log.d("Database","Row updated: " + con.getConName());
        }
    }

    public Cursor getAllConventionsFromSQLite()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CON_TABLE_NAME, null);
        return c;
    }

    public Cursor getSelectConventionsFromSQLite(String conName, String conCode, String conCity, String conState, String conWithin, String conStartDate, String conEndDate)
    {
        if (conStartDate.matches(""))
            conStartDate = "2000/01/01";
        if (conEndDate.matches(""))
            conEndDate = "2999/12/31";

        String whereQuery = " WHERE ";
        whereQuery += CON_CONVENTION_ID + " > '0'";

        if (!conName.matches(""))
            whereQuery += " AND " + CON_NAME + " LIKE '%" + conName + "%'";
        //TODO - Add Convention Code
        //if (conCode != "")
        //    whereQuery += CON_CONVENTION_ID + " = " + conName;
        if (!conCity.matches(""))
            whereQuery += " AND " + CON_CITY + " LIKE '%" + conCity + "%'";
        if (!conState.matches("Any"))
            whereQuery += " AND " + CON_STATE + " = '" + conState + "'";
        //TODO - Method for find cities within X miles
        //if (conName != "")
        //    whereQuery += CON_CITY + " = " + conWithin;
        //TODO - Dates
        //if (!conStartDate.matches("2000/01/01") || !conEndDate.matches("2999/12/31")) //IF THEY ARE NOT MIN OR MAX, THEN ONE IS SET AND PROCEED
        //    whereQuery += " AND NOT (" + conStartDate + " < " + CON_END_DATE + " OR " + conEndDate + " > " + CON_START_DATE + ")";

        //else if (!conStartDate.matches("") && conEndDate.matches("")) //FROM SET and TO NOT SET
        //    whereQuery += " AND " + CON_END_DATE + " >= " + conStartDate;
        //else if (conStartDate.matches("") && !conEndDate.matches("")) //FROM NOT SET and TO SET
        //    whereQuery += " AND " + CON_START_DATE + " <= " + conEndDate;
        //if (!conEndDate.matches(""))
        //    whereQuery += " AND " + CON_END_DATE + " = '" + conEndDate + "'";


        SQLiteDatabase db = this.getWritableDatabase();
        String t = "SELECT * FROM " + CON_TABLE_NAME + whereQuery;
        Cursor c = db.rawQuery("SELECT * FROM " + CON_TABLE_NAME + whereQuery, null);
        return c;
    }

    ////////////////////////////////////////////////////////////////////////

    static RequestQueue requestQueue;
    static List<Event> eventsList = new ArrayList<Event>();

    public static List<Event> getEventList(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        String events_url = "http://www.jordanklamut.com/InteractiveEvents/new_events.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, events_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                try {
                    JSONArray events = response.getJSONArray("events");
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject event = events.getJSONObject(i);

                        String eventID = event.getString("EventID");
                        String eventRoom = event.getString("RoomID");
                        String eventName = event.getString("Name");
                        String eventDate = event.getString("EventDate");
                        String eventStartTime = event.getString("StartTime");
                        String eventEndTime = event.getString("EndTime");
                        String eventDescription = event.getString("Description");

                        eventsList.add(new Event(eventName, eventRoom, eventStartTime, eventEndTime, eventDescription));

                        //result.append(firstname + " " + lastname + " " + age + " \n");
                    }
                    //result.append("===\n");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });

        requestQueue.add(jsonObjectRequest);

        ///////////////
        //List<Event> events = new ArrayList<Event>();

        //Event(String eTitle, String eRoom, String eStartTime, String eEndTime, String eDescription)
        //eventsList.add(new Event("Batman Signing", "Room 214", "7:00 PM", "7:30 PM", "Danananananana Batman!"));
        //eventsList.add(new Event("Adventure Time", "Room 212", "12:30 PM", "2:00 PM", "Finn the Human, Jake the Dog"));
        //eventsList.add(new Event("Superman Signing", "Room 214", "7:30 PM", "8:00 PM", "Meet Superman!"));
        //eventsList.add(new Event("Star Wars Publishing", "Room 126", "4:15 PM", "5:00 PM", "Who doesn't love Star Wars?"));
        //eventsList.add(new Event("TMNT Panel", "Room 111", "2:15 PM", "4:00 PM", "Teenage Mutant Ninja Turtles!!"));
        //eventsList.add(new Event("My Little Pony", "Room 113", "3:45 PM", "4:30 PM", "Meet the Pony"));

        Collections.shuffle(eventsList, new Random(System.nanoTime()));
        Collections.sort(eventsList, new Comparator<Event>()
        {
            @Override
            public int compare(Event o1, Event o2)
            {
                return o1.getEventStartTime().compareTo(o2.getEventStartTime());
            }

        });
        return eventsList;
    }

    //RETURNS ALL CONVENTIONS FROM PHP
    public void setConventionList(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        String conventions_url = "http://www.jordanklamut.com/InteractiveEvents/new_conventions.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, conventions_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                try {
                    JSONArray events = response.getJSONArray("conventions");
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject event = events.getJSONObject(i);

                        String conID = event.getString("ConventionID");
                        String conName = event.getString("Name");
                        String conStartDate = event.getString("StartDate");
                        String conEndDate = event.getString("EndDate");
                        String conStreetAddress = event.getString("StreetAddress");
                        String conCity = event.getString("City");
                        String conState = event.getString("State");
                        String conZipCode = event.getString("ZipCode");
                        String conDescription = event.getString("Description");

                        insertConventionToSQLite(new Convention(conID, conName, conStartDate, conEndDate, conStreetAddress, conCity, conState, conZipCode, conDescription));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });

        requestQueue.add(jsonObjectRequest);
        return;// conventionsList;
    }
}
