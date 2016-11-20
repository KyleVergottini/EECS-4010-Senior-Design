package com.jordanklamut.interactiveevents;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jordanklamut.interactiveevents.models.Room;

public class InfoLayout extends LinearLayout {

    private TextView roomHeader;
    private TableLayout eventTable;

    private DatabaseManager dm;

    public InfoLayout(Context context)
    {
        super(context);

        View.inflate(context, R.layout.info_layout, this);

        this.setVisibility(INVISIBLE);

        roomHeader = (TextView)findViewById(R.id.roomHeader);
        eventTable = (TableLayout)findViewById(R.id.eventTable);

        dm = new DatabaseManager(context);
    }

    public InfoLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InfoLayout(Context context, AttributeSet attrs) {
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

                TextView eventTime = new TextView(this.getContext());
                eventTime.setPadding(10, 10, 10, 10);
                eventTime.setText(eventCursor.getString(eventStartTimeIndex) + " - " + eventCursor.getString(eventEndTimeIndex));
                eventRow.addView(eventTime);

                eventTable.addView(eventRow);
            }
        } finally {
            eventCursor.close();
        }
        this.setVisibility(VISIBLE);
        return;
    }
}
