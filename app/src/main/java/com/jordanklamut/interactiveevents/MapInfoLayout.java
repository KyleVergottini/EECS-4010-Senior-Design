package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jordanklamut.interactiveevents.models.DetailsDialog;
import com.jordanklamut.interactiveevents.models.Event;
import com.jordanklamut.interactiveevents.models.Room;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MapInfoLayout extends LinearLayout {

    private TextView roomHeader;
    private LinearLayout eventTable;

    private DatabaseManager dm;

    public MapInfoLayout(Context context)
    {
        super(context);

        View.inflate(context, R.layout.map_info_layout, this);

        this.setVisibility(INVISIBLE);

        roomHeader = (TextView)findViewById(R.id.roomHeader);
        eventTable = (LinearLayout)findViewById(R.id.eventTable);

        findViewById(R.id.viewRoomSchedule).setOnClickListener(new ViewRoomScheduleListener());

        dm = new DatabaseManager(context);
    }

    public MapInfoLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MapInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void clear()
    {
        eventTable.removeAllViews();
        this.setVisibility(INVISIBLE);
    }

    public void setRoomInformation(Room room)
    {
        roomHeader.setText(room.getRoomName());
        Cursor eventCursor = dm.getUpcomingEventsForRoomFromSQLite(room.getRoomID());
        int eventIDIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_EVENT_ID);
        int eventNameIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_NAME);
        int eventDateIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_EVENT_DATE);
        int eventStartTimeIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_START_TIME);
        int eventEndTimeIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_END_TIME);
        int eventFavoriteIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_FAVORITE);
        try {
            while (eventCursor.moveToNext()) {
                Event event = new Event();
                event.setEventName(eventCursor.getString(eventNameIndex));
                event.setEventDate(eventCursor.getString(eventDateIndex));
                event.setEventStartTime(eventCursor.getString(eventStartTimeIndex));
                event.setEventEndTime(eventCursor.getString(eventEndTimeIndex));
                event.setEventFavorite(eventCursor.getString(eventFavoriteIndex));

                LinearLayout eventRow = new LinearLayout(this.getContext());
                LinearLayout eventLine = new LinearLayout(this.getContext());

                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 0);

                FavoriteEventButton eventFavorite = new FavoriteEventButton(this.getContext(), event.getEventID(), Integer.parseInt(event.getEventFavorite()));
                eventFavorite.setLayoutParams(params);
                eventFavorite.setPadding(0, 10, 0, 10);
                eventLine.addView(eventFavorite);

                EventInfoButton eventInfo = new EventInfoButton(this.getContext(), event, roomHeader.getText().toString());
                eventInfo.setLayoutParams(params);
                eventInfo.setPadding(0, 10, 0, 10);
                eventLine.addView(eventInfo);

                //Need to format start and end times
                final DateFormat STRING_TO_DATE = new SimpleDateFormat("hh:mm:ss");
                final DateFormat DATE_TO_STRING = new SimpleDateFormat("hh:mm a");

                String eventStartTime, eventEndTime;
                try {
                    eventStartTime = DATE_TO_STRING.format(STRING_TO_DATE.parse(event.getEventStartTime()));
                    eventEndTime = DATE_TO_STRING.format(STRING_TO_DATE.parse(event.getEventEndTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    eventStartTime = "";
                    eventEndTime = "";
                }

                TextView eventTime = new TextView(this.getContext());
                eventTime.setPadding(10, 10, 10, 10);
                eventTime.setTextAppearance(this.getContext(), android.R.style.TextAppearance_Small);
                eventTime.setText(eventStartTime + " - " + eventEndTime);
                eventLine.addView(eventTime);
                eventRow.addView(eventLine);

                TextView eventName = new TextView(this.getContext());
                eventName.setPadding(10, 10, 10, 10);
                eventName.setTextAppearance(this.getContext(), android.R.style.TextAppearance_Small);
                eventName.setText(event.getEventName());
                eventRow.addView(eventName);

                eventTable.addView(eventRow);
            }
        } finally {
            eventCursor.close();
        }
        this.setVisibility(VISIBLE);
        return;
    }

    private class ViewRoomScheduleListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            final FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, new ScheduleFragment()).commit();

            final NavigationView navigationView = (NavigationView) ((Activity) getContext()).findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(2).setChecked(true);
        }
    }

    private class FavoriteEventButton extends ImageView {
        private String eventID;
        private int nextFavorite;

        final private Drawable favoriteButton = ContextCompat.getDrawable(this.getContext(), R.drawable.map_info_add_event);
        final private Drawable unfavoriteButton = ContextCompat.getDrawable(this.getContext(), R.drawable.map_info_remove_event);

        public FavoriteEventButton(Context context, String eventID, int currentFavorite)
        {
            super(context);
            this.eventID = eventID;
            this.nextFavorite = currentFavorite == 0 ? 1 : 0;
            this.setImageDrawable(nextFavorite == 0 ? unfavoriteButton : favoriteButton);
            this.setOnClickListener(new FavoriteEventListener());
        }

        private class FavoriteEventListener implements OnClickListener {
            @Override
            public void onClick(View view) {
                dm.setEventFavorite(eventID, nextFavorite);
                nextFavorite = nextFavorite == 0 ? 1 : 0;
                ((ImageView) view).setImageDrawable(nextFavorite == 0 ? unfavoriteButton : favoriteButton);
            }
        }
    }

    private class EventInfoButton extends ImageView {
        private Event event;
        private String roomName;

        final private Drawable eventInfoIcon = ContextCompat.getDrawable(this.getContext(), R.drawable.map_info_event_info);

        public EventInfoButton(Context context, Event event, String roomName)
        {
            super(context);
            this.event = event;
            this.roomName = roomName;
            this.setImageDrawable(eventInfoIcon);
            this.setOnClickListener(new EventInfoListener());
        }

        private class EventInfoListener implements OnClickListener {
            @Override
            public void onClick(View view) {
                new DetailsDialog().setEventDetailsDialog(getContext(), event, roomName);
            }
        }
    }
}
