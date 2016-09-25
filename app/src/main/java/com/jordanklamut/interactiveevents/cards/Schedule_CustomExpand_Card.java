package com.jordanklamut.interactiveevents.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jordanklamut.interactiveevents.R;

import it.gmariotti.cardslib.library.internal.CardExpand;

public class Schedule_CustomExpand_Card extends CardExpand {

    int count;

    public Schedule_CustomExpand_Card(Context context) {
        super(context, R.layout.schedule_inner_expand);
    }

    public Schedule_CustomExpand_Card(Context context, int i) {
        super(context, R.layout.schedule_inner_expand);
        count = i;
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;

        //Retrieve TextView elements
        TextView tx1 = (TextView) view.findViewById(R.id.schedule_card_expand_title);
        tx1.setText("Event Details: \n");
        TextView tx2 = (TextView) view.findViewById(R.id.schedule_card_expand_details_text);
        //tx2.setText("Event Details2");
        //text hardcoded for now
    }
}
