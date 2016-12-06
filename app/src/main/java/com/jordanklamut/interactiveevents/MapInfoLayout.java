package com.jordanklamut.interactiveevents;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jordanklamut.interactiveevents.models.Room;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapInfoLayout extends LinearLayout {

    private TextView roomHeader;
    private TableLayout eventTable;

    private String currentRoomID;

    private DatabaseManager dm;

    public MapInfoLayout(Context context)
    {
        super(context);

        View.inflate(context, R.layout.map_info_layout, this);

        this.setVisibility(INVISIBLE);

        roomHeader = (TextView)findViewById(R.id.roomHeader);
        eventTable = (TableLayout)findViewById(R.id.eventTable);

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
        int eventNameIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_NAME);
        int eventStartTimeIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_START_TIME);
        int eventEndTimeIndex = eventCursor.getColumnIndex(DatabaseManager.EVENT_END_TIME);
        try {
            while (eventCursor.moveToNext()) {
                TableRow eventRow = new TableRow(this.getContext());

                TextView eventName = new TextView(this.getContext());
                eventName.setPadding(10, 10, 10, 10);
                eventName.setText(eventCursor.getString(eventNameIndex));
                eventRow.addView(eventName);

                //Need to format start and end times
                final DateFormat STRING_TO_DATE = new SimpleDateFormat("hh:mm:ss");
                final DateFormat DATE_TO_STRING = new SimpleDateFormat("hh:mm a");

                String eventStartTime, eventEndTime;
                try {
                    eventStartTime = DATE_TO_STRING.format(STRING_TO_DATE.parse(eventCursor.getString(eventStartTimeIndex)));
                    eventEndTime = DATE_TO_STRING.format(STRING_TO_DATE.parse(eventCursor.getString(eventEndTimeIndex)));
                } catch (ParseException e) {
                    e.printStackTrace();
                    eventStartTime = "";
                    eventEndTime = "";
                }

                TextView eventTime = new TextView(this.getContext());
                eventTime.setPadding(10, 10, 10, 10);
                eventTime.setText(eventStartTime + " - " + eventEndTime);
                eventRow.addView(eventTime);

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
}
