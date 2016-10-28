//package com.jordanklamut.interactiveevents;

//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Random;
//
//import com.jordanklamut.interactiveevents.models.Convention;
//import com.jordanklamut.interactiveevents.models.Event;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class util {
//
//    static RequestQueue requestQueue;
//    private StringRequest request;
//    static List<Event> eventsList = new ArrayList<Event>();
//    static List<Convention> conventionsList = new ArrayList<Convention>();
//    static DatabaseManager dm;
//
//    public static List<Event> getEventList(Context context) {
//        requestQueue = Volley.newRequestQueue(context);
//        String events_url = "http://www.jordanklamut.com/InteractiveEvents/new_events.php";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, events_url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                System.out.println(response.toString());
//
//                try {
//                    JSONArray events = response.getJSONArray("events");
//                    for (int i = 0; i < events.length(); i++) {
//                        JSONObject event = events.getJSONObject(i);
//
//                        String eventID = event.getString("EventID");
//                        String eventRoom = event.getString("RoomID");
//                        String eventName = event.getString("Name");
//                        String eventDate = event.getString("EventDate");
//                        String eventStartTime = event.getString("StartTime");
//                        String eventEndTime = event.getString("EndTime");
//                        String eventDescription = event.getString("Description");
//
//                        eventsList.add(new Event(eventName, eventRoom, eventStartTime, eventEndTime, eventDescription));
//
//                        //result.append(firstname + " " + lastname + " " + age + " \n");
//                    }
//                    //result.append("===\n");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.append(error.getMessage());
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);
//
//        ///////////////
//        //List<Event> events = new ArrayList<Event>();
//
//        //Event(String eTitle, String eRoom, String eStartTime, String eEndTime, String eDescription)
//        //eventsList.add(new Event("Batman Signing", "Room 214", "7:00 PM", "7:30 PM", "Danananananana Batman!"));
//        //eventsList.add(new Event("Adventure Time", "Room 212", "12:30 PM", "2:00 PM", "Finn the Human, Jake the Dog"));
//        //eventsList.add(new Event("Superman Signing", "Room 214", "7:30 PM", "8:00 PM", "Meet Superman!"));
//        //eventsList.add(new Event("Star Wars Publishing", "Room 126", "4:15 PM", "5:00 PM", "Who doesn't love Star Wars?"));
//        //eventsList.add(new Event("TMNT Panel", "Room 111", "2:15 PM", "4:00 PM", "Teenage Mutant Ninja Turtles!!"));
//        //eventsList.add(new Event("My Little Pony", "Room 113", "3:45 PM", "4:30 PM", "Meet the Pony"));
//
//        Collections.shuffle(eventsList, new Random(System.nanoTime()));
//        Collections.sort(eventsList, new Comparator<Event>()
//        {
//         @Override
//            public int compare(Event o1, Event o2)
//            {
//             return o1.getEventStartTime().compareTo(o2.getEventStartTime());
//            }
//
//        });
//        return eventsList;
//    }
//
//    public static void setConventionList(Context context) {
//        requestQueue = Volley.newRequestQueue(context);
//        String conventions_url = "http://www.jordanklamut.com/InteractiveEvents/new_conventions.php";
//
//        dm  = new DatabaseManager(context);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, conventions_url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                System.out.println(response.toString());
//
//                try {
//                    JSONArray events = response.getJSONArray("conventions");
//                    for (int i = 0; i < events.length(); i++) {
//                        JSONObject event = events.getJSONObject(i);
//
//                        String conID = event.getString("ConventionID");
//                        String conName = event.getString("Name");
//                        String conStartDate = event.getString("StartDate");
//                        String conEndDate = event.getString("EndDate");
//                        String conStreetAddress = event.getString("StreetAddress");
//                        String conState = event.getString("State");
//                        String conZipCode = event.getString("ZipCode");
//                        String conDescription = event.getString("Description");
//
//                        dm.insertConvention(new Convention(conID, conName, conStartDate, conEndDate, conStreetAddress, conState, conZipCode, conDescription));
//                        //conventionsList.add(new Convention(conID, conName, conStartDate, conEndDate, conStreetAddress, conState, conZipCode, conDescription));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.append(error.getMessage());
//
//            }
//        });
//
//        requestQueue.add(jsonObjectRequest);
//
//        return;// conventionsList;
//    }
//}
