package com.jordanklamut.interactiveevents;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jordanklamut.interactiveevents.models.Room;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends Fragment {

    DatabaseManager dm;

    HashMap<String, MapLayout> mapLayout;
    LinearLayout mapDisplay;

    public MapFragment() {

    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DatabaseManager(this.getActivity());

        SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
        String conventionID = csp.getString("homeConventionID", null);

        dm.setRoomList(getActivity(), conventionID);
        //dm.setMapImages(getActivity(), conventionID);

        initializeMapLayouts(conventionID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Map");

        View x = inflater.inflate(R.layout.map_fragment, null);

        mapDisplay = (LinearLayout) x.findViewById(R.id.mapDisplay);
        LinearLayout levelSelect = (LinearLayout) x.findViewById(R.id.levelSelector);

        if (mapLayout.size() > 1) {
            for (int i = 1; i <= mapLayout.size(); i++) {
                levelSelect.addView(new LevelSelectorButton(getActivity(), String.valueOf(i)));
            }
        } else {
            levelSelect.setVisibility(View.INVISIBLE);
        }

        if (mapLayout.size() > 0) {
            mapDisplay.addView(mapLayout.get("1"));
        }

        return x;
    }

    public void initializeMapLayouts(String conventionID) {
        //Clear map layouts
        mapLayout = new HashMap<String, MapLayout>();

        //Initialize map image and room list
        Bitmap mapImage;
        ArrayList<Room> roomList = new ArrayList<Room>();

        for (int i = 1; i <= 3; i++) {
            String ilevel = String.valueOf(i);
            mapImage = getMapImage(conventionID, ilevel);
            if (mapImage != null) {
                roomList = getRoomList(conventionID, ilevel);
                mapLayout.put(ilevel, new MapLayout(getActivity(), mapImage, roomList));
            } else {
                break;
            }
        }
    }

    public ArrayList<Room> getRoomList(String conventionID, String level) {
        ArrayList<Room> roomList = new ArrayList<Room>();

        Cursor res = dm.getRoomsForLevelOfConventionFromSQLite(conventionID, level);

        if(res.getCount() == 0) {
            Log.d("Database","No room for this convention's level");
        }
        else {
            int roomIDIndex = res.getColumnIndex(DatabaseManager.ROOM_ROOM_ID);
            int roomNameIndex = res.getColumnIndex(DatabaseManager.ROOM_NAME);
            int roomXCoordinateIndex = res.getColumnIndex(DatabaseManager.ROOM_X_COORDINATE);
            int roomYCoordinateIndex = res.getColumnIndex(DatabaseManager.ROOM_Y_COORDINATE);

            while (res.moveToNext())
            {
                Room item = new Room();
                item.setRoomID(res.getString(roomIDIndex));
                item.setRoomName(res.getString(roomNameIndex));
                item.setRoomXCoordinate(res.getString(roomXCoordinateIndex));
                item.setRoomYCoordinate(res.getString(roomYCoordinateIndex));
                roomList.add(item);
            }
        }

        return roomList;
    }

    public Bitmap getMapImage(String conventionID, String level) {
        int mapImageID = getResources().getIdentifier("map_" + conventionID + "_" + level,"drawable","com.jordanklamut.interactiveevents");
        Bitmap mapImage;

        if (mapImageID == 0)
        {
            mapImage = null;
        }
        else
        {
            mapImage = ((BitmapDrawable) getResources().getDrawable(mapImageID, null)).getBitmap();
        }

        return mapImage;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() { super.onDetach(); }

    class LevelSelectorButton extends Button {
        private String level;

        protected LevelSelectorButton(Context context) { super(context); }

        protected LevelSelectorButton(Context context, String level) {
            super(context);
            this.setText(level);
            this.level = level;
            this.setOnClickListener(new LevelSelectorListener());
        }

        private class LevelSelectorListener implements OnClickListener {

            @Override
            public void onClick(View view) {
                mapDisplay.removeAllViews();
                mapDisplay.addView(mapLayout.get(level));
            }
        }
    }
}
