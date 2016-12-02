package com.jordanklamut.interactiveevents;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jordanklamut.interactiveevents.helpers.CustomRequest;
import com.jordanklamut.interactiveevents.models.Convention;
import com.jordanklamut.interactiveevents.models.ConventionMap;
import com.jordanklamut.interactiveevents.models.Event;
import com.jordanklamut.interactiveevents.models.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        public static final String CON_FAVORITE = "Favorite";

    public static final String EVENT_TABLE_NAME = "Events";
        public static final String EVENT_EVENT_ID = "EventID";
        public static final String EVENT_ROOM_ID = "RoomID";
        public static final String EVENT_NAME = "Name";
        public static final String EVENT_EVENT_DATE = "EventDate";
        public static final String EVENT_START_TIME = "StartTime";
        public static final String EVENT_END_TIME = "EndTime";
        public static final String EVENT_DESCRIPTION = "Description";
        public static final String EVENT_FAVORITE = "Favorite";

    public static final String ROOM_TABLE_NAME = "Rooms";
        public static final String ROOM_ROOM_ID = "RoomID";
        public static final String ROOM_CONVENTION_ID = "ConventionID";
        public static final String ROOM_NAME = "Name";
        public static final String ROOM_LEVEL = "Level";
        public static final String ROOM_X_COORDINATE = "XCoordinate";
        public static final String ROOM_Y_COORDINATE = "YCoordinate";

    public static final String MAP_TABLE_NAME = "Maps";
        public static final String MAP_CONVENTION_ID = "ConventionID";
        public static final String MAP_1 = "Map1";
        public static final String MAP_2 = "Map2";
        public static final String MAP_3 = "Map3";

    private final String QUERY_CON = "CREATE TABLE " + CON_TABLE_NAME + " ("
            + CON_CONVENTION_ID + " INTEGER, "
            + CON_NAME + " TEXT, "
            + CON_START_DATE + " TEXT, "
            + CON_END_DATE + " TEXT, "
            + CON_STREET_ADDRESS + " TEXT, "
            + CON_CITY + " TEXT, "
            + CON_STATE + " TEXT, "
            + CON_ZIP_CODE + " TEXT, "
            + CON_DESCRTIPION + " TEXT, "
            + CON_FAVORITE + " INTEGER DEFAULT 0) ";

    private final String QUERY_EVENT = "CREATE TABLE " + EVENT_TABLE_NAME + " ("
            + EVENT_EVENT_ID + " INTEGER, "
            + EVENT_ROOM_ID + " INTEGER, "
            + EVENT_NAME + " TEXT, "
            + EVENT_EVENT_DATE + " TEXT, "
            + EVENT_START_TIME + " TEXT, "
            + EVENT_END_TIME + " TEXT, "
            + EVENT_DESCRIPTION + " TEXT, "
            + EVENT_FAVORITE + " INTEGER DEFAULT 0) ";

    private final String QUERY_ROOM = "CREATE TABLE " + ROOM_TABLE_NAME + " ("
            + ROOM_ROOM_ID + " INTEGER, "
            + ROOM_CONVENTION_ID + " INTEGER, "
            + ROOM_NAME + " TEXT, "
            + ROOM_LEVEL + " TEXT, "
            + ROOM_X_COORDINATE + " TEXT, "
            + ROOM_Y_COORDINATE + " TEXT) ";

    private final String QUERY_MAP = "CREATE TABLE " + MAP_TABLE_NAME + " ("
            + MAP_CONVENTION_ID + " INTEGER, "
            + MAP_1 + " BLOB, "
            + MAP_2 + " BLOB, "
            + MAP_3 + " BLOB) ";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("Database","Database Created: " + DATABASE_NAME);

        //SQLiteDatabase checkDB = null;
        //try {
        //    checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        //    checkDB.close();
        //} catch (SQLiteException e) {
        //    Log.d("Database","Database Doesnt Exist: " + DATABASE_NAME);
        //}
//
        //if (checkDB != null)
        //    Log.d("Database","Database Already Created: " + DATABASE_NAME);
        //else{
        //
        //}
    }

    @Override
    //CREATE SQLite TABLES FOR CONVENTIONS, EVENTS, ROOMS
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(QUERY_CON);
        database.execSQL(QUERY_EVENT);
        database.execSQL(QUERY_ROOM);
        database.execSQL(QUERY_MAP);

        Log.d("Database","Table created: " + CON_TABLE_NAME);
        Log.d("Database","Table created: " + EVENT_TABLE_NAME);
        Log.d("Database","Table created: " + ROOM_TABLE_NAME);
        Log.d("Database","Table created: " + MAP_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + CON_TABLE_NAME;
        database.execSQL(query);
        onCreate(database);
    }

//////////////////////CONVENTIONS//////////////////////

    //RETURNS ALL CONVENTIONS FROM PHP
    public void setConventionList(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue = Volley.newRequestQueue(context);
        String conventions_url = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/Convention/GetAllConventions/";

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
                        String conFavorite = "0";

                        insertConventionToSQLite(new Convention(conID, conName, conStartDate, conEndDate, conStreetAddress, conCity, conState, conZipCode, conDescription, conFavorite));
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
    }

    //sub method for setConventionList
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

        res.close();
    }

    //RETURN ALL CONVENTIONS FROM SQLite
    public Cursor getAllConventionsFromSQLite()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + CON_TABLE_NAME, null);
    }

    public Cursor getFavoriteConventionsFromSQLite()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + CON_TABLE_NAME + " WHERE " + CON_FAVORITE + " LIKE '1'", null);
    }

    //RETURN SELECT CONVENTIONS FROM SQLite
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
        return db.rawQuery("SELECT * FROM " + CON_TABLE_NAME + whereQuery, null);
    }

    public void clearConventionsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CON_TABLE_NAME, null, null);
        return;
    }

    public void setConFavorite(String conID, int setFav){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor res = db.rawQuery("SELECT * FROM " + CON_TABLE_NAME + " WHERE " + CON_CONVENTION_ID + " LIKE " + conID, null);

        values.put(CON_FAVORITE, setFav);

        if (res.getCount() == 0) {
            //CON DOESNT EXIST
            Log.d("Database", "Con favorite failed: " + conID);
        } else {
            //EVENT EXISTS - UPDATE IT
            db.update(CON_TABLE_NAME, values, CON_CONVENTION_ID + " = ?", new String[] {conID});
            db.close();
            Log.d("Database","Row updated: " + conID);
        }

        res.close();
    }

    public int getConventionDates(String conID) {
        //TODO - not finished
        SQLiteDatabase db = this.getWritableDatabase();
        String[] startDate;
        String[] endDate;
        int days = 0;

        Cursor res = db.rawQuery("SELECT * FROM " + CON_TABLE_NAME + " WHERE " + CON_CONVENTION_ID + " LIKE '" + conID + "'", null);
        if(res.getCount() == 1){
            res.moveToFirst();
            startDate = res.getString(res.getColumnIndex(CON_START_DATE)).split("-");
            int start = Integer.parseInt(startDate[2]);
            endDate = res.getString(res.getColumnIndex(CON_END_DATE)).split("-");
            int end = Integer.parseInt(endDate[2]);
            days = end - start;
            return days;
        }

        return days;
    }

/////////////////////////EVENTS////////////////////////
//RETURNS ALL EVENTS FROM PHP MATCHING CONVENTION_ID
    public void setEventList(Context context, String conventionID) {
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    String events_url = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/Event/GetAllEventsForAConvention/";

    Map<String,String> params = new HashMap<>();
    params.put("conventionID", conventionID);

    CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, events_url, params, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d("Database","PHP RESPONSE " + response.toString());

            try {
                JSONArray events = response.getJSONArray("events");
                for (int i = 0; i < events.length(); i++) {
                    JSONObject event = events.getJSONObject(i);

                    String[] startDateTime = event.getString("StartTime").split(" "); //--date 1-time
                    String[] endDateTime = event.getString("EndTime").split(" ");

                    String eventID = event.getString("EventID");
                    String eventRoomID = event.getString("RoomID");
                    String eventName = event.getString("Name");
                    String eventDate = startDateTime[0];
                    String eventStartTime = startDateTime[1];
                    String eventEndTime = endDateTime[1];
                    String eventDescription = event.getString("Description");
                    String eventFavorite = "0";

                    insertEventToSQLite(new Event(eventID, eventRoomID, eventName, eventDate, eventStartTime, eventEndTime, eventDescription, eventFavorite));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            System.out.append(error.getMessage());
        }
    });

    requestQueue.add(jsonObjectRequest);
}

    //sub method for setEventList
    public void insertEventToSQLite(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor res = db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + EVENT_EVENT_ID + " LIKE " + event.getEventID(), null);

        values.put(EVENT_EVENT_ID, event.getEventID());
        values.put(EVENT_ROOM_ID, event.getEventRoomID());
        values.put(EVENT_NAME, event.getEventName());
        values.put(EVENT_EVENT_DATE, event.getEventDate());
        values.put(EVENT_START_TIME, event.getEventStartTime());
        values.put(EVENT_END_TIME, event.getEventEndTime());
        values.put(EVENT_DESCRIPTION, event.getEventDescription());

        if (res.getCount() == 0) {
            //IF EVENT DOESNT EXIST - ADD IT
            db.insert(EVENT_TABLE_NAME, null, values);
            db.close();
            Log.d("Database", "Event inserted: " + event.getEventName());
        } else {
            //ELSE EVENT ALREADY EXISTS - UPDATE IT
            db.update(EVENT_TABLE_NAME, values, EVENT_EVENT_ID + " = ?", new String[] {event.getEventID()});
            db.close();
            Log.d("Database","Row updated: " + event.getEventName());
        }

        res.close();
    }

    //RETURN ALL EVENTS FROM SQLite
    public Cursor getAllEventsFromSQLite()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME, null);
    }

    //RETURN SELECT EVENTS FROM SQLite
    public Cursor getSelectEventsFromSQLite(String eventID)
    {
        String whereQuery = " WHERE ";
        whereQuery += EVENT_EVENT_ID + " LIKE " + eventID;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + whereQuery, null);
    }

    //RETURN SELECT EVENTS FROM SQLite
    public Cursor getFavoritedEventsFromSQLite()
    {
        String whereQuery = " WHERE ";
        whereQuery += EVENT_FAVORITE + " LIKE 1";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + whereQuery, null);
    }

    //RETURN UPCOMING EVENTS FOR A ROOM FROM SQLite
    public Cursor getCurrentAndNextEventsForRoomFromSQLite(String RoomID) {
        final DateFormat DATE_TO_STRING = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final String CURRENT_TIME = "'" + DATE_TO_STRING.format(new Date()) + "'";

        String roomIDWhereQuery = " WHERE ";
        roomIDWhereQuery += EVENT_ROOM_ID + " = " + RoomID;

        String currentEventQuery = "SELECT " + EVENT_NAME + ", " + EVENT_START_TIME + ", " + EVENT_END_TIME + ", 'CURRENT' AS Status";
        currentEventQuery += " FROM " + EVENT_TABLE_NAME;
        currentEventQuery += roomIDWhereQuery + " AND ";
        currentEventQuery += EVENT_START_TIME + " <= " + CURRENT_TIME + " AND ";
        currentEventQuery += EVENT_END_TIME + " > " + CURRENT_TIME;

        String nextEventQuery = "SELECT " + EVENT_NAME + ", " + EVENT_START_TIME + ", " + EVENT_END_TIME + ", 'NEXT' AS Status";
        nextEventQuery += " FROM " + EVENT_TABLE_NAME;
        nextEventQuery += roomIDWhereQuery + " AND ";
        nextEventQuery += EVENT_START_TIME + " > " + CURRENT_TIME;
        nextEventQuery += " ORDER BY " + EVENT_START_TIME;
        nextEventQuery += " LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(currentEventQuery + " UNION " + nextEventQuery, null);
    }


    public void clearEventsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENT_TABLE_NAME, null, null);
        return;
    }

    public void setEventFavorite(String eventID, int setFav){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor res = db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + EVENT_EVENT_ID + " LIKE " + eventID, null);

        values.put(EVENT_FAVORITE, setFav);

        if (res.getCount() == 0) {
            //EVENT DOESNT EXIST
            Log.d("Database", "Event favorite failed: " + eventID);
        } else {
            //EVENT EXISTS - UPDATE IT
            db.update(EVENT_TABLE_NAME, values, EVENT_EVENT_ID + " = ?", new String[] {eventID});
            db.close();
            Log.d("Database","Row updated: " + eventID);
        }

        res.close();
    }

/////////////////////////ROOMS/////////////////////////

    //RETURNS ALL ROOMS FROM PHP MATCHING CONVENTION_ID
    public void setRoomList(Context context, String conventionID) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String rooms_url = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/Room/GetRoomsForAGivenConvention/";

        HashMap<String,String> params = new HashMap<>();
        params.put("conventionID", conventionID);

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, rooms_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Database","API RESPONSE " + response.toString());

                try {
                    JSONArray rooms = response.getJSONArray("rooms");
                    for (int i = 0; i < rooms.length(); i++) {
                        JSONObject room = rooms.getJSONObject(i);

                        String roomID = room.getString("RoomID");
                        String roomConventionID = room.getString("ConventionID");
                        String roomName = room.getString("Name");
                        String roomLevel = room.getString("Level");
                        String roomXCoordinate = room.getString("XCoordinate");
                        String roomYCoordinate = room.getString("YCoordinate");

                        insertRoomToSQLite(new Room(roomID, roomConventionID, roomName, roomLevel, roomXCoordinate, roomYCoordinate));
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
    }

    //sub method for setRoomList
    public void insertRoomToSQLite(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor res = db.rawQuery("SELECT * FROM " + ROOM_TABLE_NAME + " WHERE " + ROOM_ROOM_ID + " LIKE " + room.getRoomID(), null);

        values.put(ROOM_ROOM_ID, room.getRoomID());
        values.put(ROOM_CONVENTION_ID, room.getRoomConventionID());
        values.put(ROOM_NAME, room.getRoomName());
        values.put(ROOM_LEVEL, room.getRoomLevel());
        values.put(ROOM_X_COORDINATE, room.getRoomXCoordinate());
        values.put(ROOM_Y_COORDINATE, room.getRoomYCoordinate());

        if (res.getCount() == 0) {
            //IF EVENT DOESNT EXIST - ADD IT
            db.insert(ROOM_TABLE_NAME, null, values);
            db.close();
            Log.d("Database", "Room inserted: " + room.getRoomName());
        } else {
            //ELSE EVENT ALREADY EXISTS - UPDATE IT
            db.update(ROOM_TABLE_NAME, values, ROOM_ROOM_ID + " = ?", new String[] {room.getRoomID()});
            db.close();
            Log.d("Database","Row updated: " + room.getRoomName());
        }

        res.close();
    }

    //RETURN ALL ROOMS FROM SQLite
    public Cursor getAllRoomsFromSQLite()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + ROOM_TABLE_NAME, null);
    }

    //RETURN SELECT ROOMS FROM SQLite
    public Cursor getSelectRoomsFromSQLite(String roomID)
    {
        String whereQuery = " WHERE ";
        whereQuery += ROOM_ROOM_ID + " LIKE " + roomID;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + ROOM_TABLE_NAME + whereQuery, null);
    }

    //RETURN ROOMS FOR CONVENTION FROM SQLite
    public Cursor getRoomsForLevelOfConventionFromSQLite(String conventionID, String level)
    {
        String whereQuery = " WHERE ";
        whereQuery += ROOM_CONVENTION_ID + " = " + conventionID;
        whereQuery += " AND " + ROOM_LEVEL + " = " + level;

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + ROOM_TABLE_NAME + whereQuery, null);
    }

    public Cursor getRoomByIDFromSQLite(String roomID)
    {
        String whereQuery = " WHERE ";
        whereQuery += ROOM_ROOM_ID + " = '" + roomID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + ROOM_TABLE_NAME + whereQuery, null);
    }

    public void clearRoomsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ROOM_TABLE_NAME, null, null);
        return;
    }

/////////////////////////MAPS/////////////////////////

    //RETURNS ALL MAPS FROM .NET API MATCHING CONVENTION_ID
    public void setMapList(Context context, String conventionID) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String rooms_url = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/Map/GetMapForConvention/";

        HashMap<String,String> params = new HashMap<>();
        params.put("conventionID", conventionID);

        final CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, rooms_url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Database","API RESPONSE " + response.toString());

                try {
                    String ConventionID = response.getString("ConventionID");
                    ConventionMap map = new ConventionMap(ConventionID);

                    String mapString1 = response.getString("MapImage1");
                    String mapString2 = response.getString("MapImage2");
                    String mapString3 = response.getString("MapImage3");

                    if (!mapString1.equals("null"))
                    {
                        map.setMap1(Base64.decode(mapString1, Base64.DEFAULT));
                    }
                    if (!mapString2.equals("null"))
                    {
                        map.setMap2(Base64.decode(mapString2, Base64.DEFAULT));
                    }
                    if (!mapString3.equals("null"))
                    {
                        map.setMap3(Base64.decode(mapString1, Base64.DEFAULT));
                    }

                    insertMapToSQLite(map);
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
    }

    //sub method for setMapList
    public void insertMapToSQLite(ConventionMap map) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor res = db.rawQuery("SELECT * FROM " + MAP_TABLE_NAME + " WHERE " + MAP_CONVENTION_ID + " LIKE " + map.getMapConventionID(), null);

        values.put(MAP_CONVENTION_ID, map.getMapConventionID());
        values.put(MAP_1, map.getMap1());
        values.put(MAP_2, map.getMap2());
        values.put(MAP_3, map.getMap3());

        if (res.getCount() == 0) {
            //IF MAP DOESNT EXIST - ADD IT
            db.insert(MAP_TABLE_NAME, null, values);
            db.close();
            Log.d("Database", "Map inserted for ConventionID: " + map.getMapConventionID());
        } else {
            //ELSE MAP ALREADY EXISTS - UPDATE IT
            db.update(MAP_TABLE_NAME, values, MAP_CONVENTION_ID + " = ?", new String[] {map.getMapConventionID()});
            db.close();
            Log.d("Database","Row updated for Map of ConventionID: " + map.getMapConventionID());
        }

        res.close();
    }

    //RETURN MAPS FOR CONVENTION FROM SQLite
    public Cursor getMapsForConventionFromSQLite(String conventionID)
    {
        String whereQuery = " WHERE ";
        whereQuery += MAP_CONVENTION_ID + " = " + conventionID;

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + MAP_TABLE_NAME + whereQuery, null);
    }

}