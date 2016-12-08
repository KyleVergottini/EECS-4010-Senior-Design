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

    public void setEventDetailsDialog(Context ctx, Event event, String roomName) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// getLayoutInflater(null);
        View dialogLayout = inflater.inflate(R.layout.details_dialog_event, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(dialogLayout);

        TextView detailsName = (TextView) dialogLayout.findViewById(R.id.tv_details_event_name);
        TextView detailsLocation = (TextView) dialogLayout.findViewById(R.id.tv_details_event_room);
        TextView detailsDateTime = (TextView) dialogLayout.findViewById(R.id.tv_details_event_time);
        TextView detailsDateDate = (TextView) dialogLayout.findViewById(R.id.tv_details_event_date);
        TextView detailsDescription = (TextView) dialogLayout.findViewById(R.id.tv_details_description);

        detailsName.setText(event.getEventName());
        detailsLocation.setText(roomName);
        detailsDateDate.setText(event.getEventDate());
        detailsDateTime.setText(event.getEventStartTime() + " - " + event.getEventEndTime());
        detailsDescription.setText(event.getEventDescription());

        builder.show();
    }

    public void setEventDetailsDialog(Context ctx, Convention con) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// getLayoutInflater(null);
        View dialogLayout = inflater.inflate(R.layout.details_dialog_convention, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(dialogLayout);

        TextView detailsName = (TextView) dialogLayout.findViewById(R.id.tv_details_event_name);
        TextView detailsLocation = (TextView) dialogLayout.findViewById(R.id.tv_details_event_room);
        TextView detailsDateTime = (TextView) dialogLayout.findViewById(R.id.tv_details_event_time);
        TextView detailsDescription = (TextView) dialogLayout.findViewById(R.id.tv_details_description);

        detailsName.setText(con.getConName());
        detailsLocation.setText(con.getConStreetAddress() + ", \n" + con.getConCity() + ", " + con.getConState());
        detailsDateTime.setText(con.getConStartDate() + " - " + con.getConEndDate());
        detailsDescription.setText(con.getConDescription());

        builder.show();
    }
}
