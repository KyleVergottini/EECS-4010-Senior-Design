package com.jordanklamut.interactiveevents;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import com.jordanklamut.interactiveevents.models.Event;

/**
 * Created by jorda on 9/14/2016.
 */
public class util {

    public static List<Event> getEventList(Context context) {
        List<Event> events = new ArrayList<Event>();

        //Event(String eTitle, String eRoom, String eStartTime, String eEndTime, String eDescription)
        events.add(new Event("Batman Signing", "Room 214", "7:00 PM", "7:30 PM", "Danananananana Batman!"));
        events.add(new Event("Adventure Time", "Room 212", "12:30 PM", "2:00 PM", "Finn the Human, Jake the Dog"));
        events.add(new Event("Superman Signing", "Room 214", "7:30 PM", "8:00 PM", "Meet Superman!"));
        events.add(new Event("Star Wars Publishing", "Room 126", "4:15 PM", "5:00 PM", "Who doesn't love Star Wars?"));
        events.add(new Event("TMNT Panel", "Room 111", "2:15 PM", "4:00 PM", "Teenage Mutant Ninja Turtles!!"));
        events.add(new Event("My Little Pony", "Room 113", "3:45 PM", "4:30 PM", "Meet the Pony"));

        Collections.shuffle(events, new Random(System.nanoTime()));
        Collections.sort(events, new Comparator<Event>()
        {
         @Override
            public int compare(Event o1, Event o2)
            {
             return o1.getEventStartTime().compareTo(o2.getEventStartTime());
            }

        });
        return events;
    }

    public static  Event getRandomPerson(Context context) {
        return getEventList(context).get(0);
    }
}
