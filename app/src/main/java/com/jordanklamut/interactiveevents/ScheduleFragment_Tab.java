package com.jordanklamut.interactiveevents;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jordanklamut.interactiveevents.cards.Schedule_CustomExpand_Card;
import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 Code behind each tab of the schedule
 */
public class ScheduleFragment_Tab extends Fragment{

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment_Tab() {

    }

    public static ScheduleFragment_Tab newInstance() {
        return new ScheduleFragment_Tab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Schedule");
        return inflater.inflate(R.layout.schedule_tab_fragment,null);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }

    private void initCards() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0;i<10;i++){
            Card card = init_standard_header_with_expandcollapse_button_custom_area("Event Name "+i,i);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.schedule_card_list_expand);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    //This method builds a standard header with a custom expand/collpase
    private Card init_standard_header_with_expandcollapse_button_custom_area(String titleHeader,int i)
    {
        Card card = new Card(getActivity());

        CardHeader header = new CardHeader(getActivity());
        header.setTitle(titleHeader);
        header.setButtonExpandVisible(true);
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        Schedule_CustomExpand_Card expand = new Schedule_CustomExpand_Card(getActivity(),i);
        //Add Expand Area to Card
        card.addCardExpand(expand);

        //Just an example to expand a card
        if (i==2)
            card.setExpanded(true);

        card.setSwipeable(false);

        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                Toast.makeText(getActivity(),"Expand "+card.getCardHeader().getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        card.setOnCollapseAnimatorEndListener(new Card.OnCollapseAnimatorEndListener() {
            @Override
            public void onCollapseEnd(Card card) {
                Toast.makeText(getActivity(),"Collpase " +card.getCardHeader().getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        return card;
    }
}
