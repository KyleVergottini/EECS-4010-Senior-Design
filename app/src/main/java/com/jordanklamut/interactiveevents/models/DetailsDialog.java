package com.jordanklamut.interactiveevents.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jordanklamut.interactiveevents.LogInActivity;
import com.jordanklamut.interactiveevents.R;

public class DetailsDialog {

    public DetailsDialog(){

    }

    public void setEventDetailsDialog(Context ctx, Event event) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// getLayoutInflater(null);
        View dialogLayout = inflater.inflate(R.layout.details_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(dialogLayout);

        TextView detailsName = (TextView) dialogLayout.findViewById(R.id.tv_details_event_name);
        TextView deatilsLocation = (TextView) dialogLayout.findViewById(R.id.tv_details_event_room);
        TextView detailsDateTime = (TextView) dialogLayout.findViewById(R.id.tv_details_event_time);
        TextView detailsDescription = (TextView) dialogLayout.findViewById(R.id.tv_details_description);
        TextView deatilsLocationHint = (TextView) dialogLayout.findViewById(R.id.tv_details_event_room_hint);
        TextView detailsDateTimeHint = (TextView) dialogLayout.findViewById(R.id.tv_details_event_time_hint);

        deatilsLocationHint.setText("Event Room: ");
        detailsDateTimeHint.setText("Event Time: ");
        detailsName.setText(event.getEventName());
        deatilsLocation.setText(event.getEventRoomID());
        detailsDateTime.setText(event.getEventStartTime() + " - " + event.getEventEndTime());
        detailsDescription.setText(event.getEventDescription());

        builder.show();
    }

    public void setEventDetailsDialog(Context ctx, Convention con) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// getLayoutInflater(null);
        View dialogLayout = inflater.inflate(R.layout.details_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(dialogLayout);

        TextView detailsName = (TextView) dialogLayout.findViewById(R.id.tv_details_event_name);
        TextView deatilsLocation = (TextView) dialogLayout.findViewById(R.id.tv_details_event_room);
        TextView detailsDateTime = (TextView) dialogLayout.findViewById(R.id.tv_details_event_time);
        TextView detailsDescription = (TextView) dialogLayout.findViewById(R.id.tv_details_description);
        TextView deatilsLocationHint = (TextView) dialogLayout.findViewById(R.id.tv_details_event_room_hint);
        TextView detailsDateTimeHint = (TextView) dialogLayout.findViewById(R.id.tv_details_event_time_hint);

        deatilsLocationHint.setText("Con Address: ");
        detailsDateTimeHint.setText("Con Dates: ");
        detailsName.setText(con.getConName());
        deatilsLocation.setText(con.getConStreetAddress() + ", \n" + con.getConCity() + ", " + con.getConState());
        detailsDateTime.setText(con.getConStartDate() + " - " + con.getConEndDate());
        detailsDescription.setText(con.getConDescription());

        builder.show();
    }
}
