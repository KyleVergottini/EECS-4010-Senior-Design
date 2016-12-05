package com.jordanklamut.interactiveevents;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jordanklamut.interactiveevents.models.ConventionMap;
import com.jordanklamut.interactiveevents.models.Room;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends Fragment {

    DatabaseManager dm;

    HashMap<String, MapLayout> mapLayout;
    LinearLayout mapDisplay;
    LinearLayout levelSelect;

    String startingRoom = null;

    String currentLevel;

    public MapFragment() {

    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public static MapFragment newInstance(String startingRoom) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("startingRoom", startingRoom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DatabaseManager(this.getActivity());

        SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
        String conventionID = csp.getString("homeConventionID", null);

        Bundle args = getArguments();
        if (args != null) {
            this.startingRoom = args.getString("startingRoom");
        }

        mapLayout = new HashMap<String, MapLayout>();

        initializeMapLayouts(conventionID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Map");

        View x = inflater.inflate(R.layout.map_fragment, null);

        mapDisplay = (LinearLayout) x.findViewById(R.id.mapDisplay);
        levelSelect = (LinearLayout) x.findViewById(R.id.levelSelector);

        if (mapLayout.size() > 1) {
            for (int i = 1; i <= mapLayout.size(); i++) {
                levelSelect.addView(new LevelSelectorButton(getActivity(), String.valueOf(i)));
            }
        } else {
            levelSelect.setVisibility(View.INVISIBLE);
        }

        if (mapLayout.size() > 0) {
            if (startingRoom != null) {
                currentLevel = getStartingLevel();
            } else {
                currentLevel = "1";
            }
            mapDisplay.addView(mapLayout.get(currentLevel));
            levelSelect.getChildAt(Integer.parseInt(currentLevel)).setSelected(true);
        }

        return x;
    }

    public void initializeMapLayouts(String conventionID) {
        //Clear map layouts
        mapLayout.clear();

        //Initialize map image and room list
        Bitmap mapImage;
        ArrayList<Room> roomList = new ArrayList<Room>();

        ConventionMap map = getMap(conventionID);

        String startingLevel = getStartingLevel();

        if (map != null) {
            mapImage = map.getMapImage1();
            if (mapImage != null) {
                roomList = getRoomList(conventionID, "1", mapImage.getWidth());
                if (startingLevel.equals("1")) {
                    mapLayout.put("1", new MapLayout(getActivity(), mapImage, roomList, startingRoom));
                } else {
                    mapLayout.put("1", new MapLayout(getActivity(), mapImage, roomList));
                }
            }

            mapImage = map.getMapImage2();
            if (mapImage != null) {
                roomList = getRoomList(conventionID, "2", mapImage.getWidth());
                if (startingLevel.equals("2")) {
                    mapLayout.put("2", new MapLayout(getActivity(), mapImage, roomList, startingRoom));
                } else {
                    mapLayout.put("2", new MapLayout(getActivity(), mapImage, roomList));
                }
            }

            mapImage = map.getMapImage3();
            if (mapImage != null) {
                roomList = getRoomList(conventionID, "3", mapImage.getWidth());
                if (startingLevel.equals("3")) {
                    mapLayout.put("3", new MapLayout(getActivity(), mapImage, roomList, startingRoom));
                } else {
                    mapLayout.put("3", new MapLayout(getActivity(), mapImage, roomList));
                }
            }
        }
    }

    //Gets list of rooms for a given string and level
    //Image width is also supplied to get the ratio between the image size and the coordinates in the database
    public ArrayList<Room> getRoomList(String conventionID, String level, float imageWidth) {
        ArrayList<Room> roomList = new ArrayList<Room>();

        Cursor res = dm.getRoomsForLevelOfConventionFromSQLite(conventionID, level);

        if(res.getCount() == 0) {
            Log.d("Database","No room for this convention's level");
        }
        else {
            //Coordinates are modified based on the size of the map image
            //They are translated such that the coordinate refers to the point on the image that the room icon points to

            //Set dimensions and center point of room icon used in admin site
            final int WEB_APP_ICON_WIDTH = 25;
            final int WEB_APP_ICON_HEIGHT = 40;
            final float WEB_APP_ICON_X_CENTER = (float) WEB_APP_ICON_WIDTH / 2;
            final float WEB_APP_ICON_Y_CENTER = (float) WEB_APP_ICON_HEIGHT;

            //The map width in the admin site is always the same, while the height changes
            //Need to calculate ratio between the two
            final int WEB_APP_MAP_WIDTH = 1043;
            float coordinateModifier =  imageWidth / WEB_APP_MAP_WIDTH;

            //Get column indices for room table
            int roomIDIndex = res.getColumnIndex(DatabaseManager.ROOM_ROOM_ID);
            int roomNameIndex = res.getColumnIndex(DatabaseManager.ROOM_NAME);
            int roomXCoordinateIndex = res.getColumnIndex(DatabaseManager.ROOM_X_COORDINATE);
            int roomYCoordinateIndex = res.getColumnIndex(DatabaseManager.ROOM_Y_COORDINATE);

            while (res.moveToNext())
            {
                //Get room coordinates and translate them
                int roomXCoordinate = Integer.parseInt(res.getString(roomXCoordinateIndex));
                int roomYCoordinate = Integer.parseInt(res.getString(roomYCoordinateIndex));
                roomXCoordinate = (int) ((roomXCoordinate + WEB_APP_ICON_X_CENTER) * coordinateModifier);
                roomYCoordinate = (int) ((roomYCoordinate + WEB_APP_ICON_Y_CENTER) * coordinateModifier);

                Room item = new Room();
                item.setRoomID(res.getString(roomIDIndex));
                item.setRoomName(res.getString(roomNameIndex));
                item.setRoomXCoordinate(String.valueOf(roomXCoordinate));
                item.setRoomYCoordinate(String.valueOf(roomYCoordinate));
                roomList.add(item);
            }
        }

        return roomList;
    }

    public ConventionMap getMap(String conventionID) {
        ConventionMap map = null;

        Cursor res = dm.getMapsForConventionFromSQLite(conventionID);

        if(res.getCount() == 0) {
            Log.d("Database","No maps for this convention");
        }
        else {
            int mapIDIndex = res.getColumnIndex(DatabaseManager.MAP_CONVENTION_ID);
            int map1Index = res.getColumnIndex(DatabaseManager.MAP_1);
            int map2Index = res.getColumnIndex(DatabaseManager.MAP_2);
            int map3Index = res.getColumnIndex(DatabaseManager.MAP_3);

            if (res.moveToNext())
            {
                map = new ConventionMap(res.getString(mapIDIndex));
                map.setMap1(res.getBlob(map1Index));
                map.setMap2(res.getBlob(map2Index));
                map.setMap3(res.getBlob(map3Index));
            }
        }

        return map;
    }

    public String getStartingLevel()
    {
        String startingLevel = "";
        if (startingRoom != null)
        {
            Cursor res = dm.getRoomByIDFromSQLite(startingRoom);

            if(res.getCount() == 0) {
                Log.d("Database","No maps for this convention");
            }
            else {
                int mapIDIndex = res.getColumnIndex(DatabaseManager.ROOM_LEVEL);

                if (res.moveToNext())
                {
                    startingLevel = res.getString(mapIDIndex);
                }
            }
        }
        return startingLevel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() { super.onDetach(); }

    class LevelSelectorButton extends Button {

        private String level;
        private final Drawable BUTTON_BACKGROUND = (Drawable) ContextCompat.getDrawable(this.getContext(), R.drawable.level_select_button);
        private final int PADDING = 5;

        protected LevelSelectorButton(Context context, String level) {
            super(context);
            this.setBackground(BUTTON_BACKGROUND);
            this.setPadding(PADDING, PADDING, PADDING, PADDING);
            this.setText(level);
            this.level = level;
            this.setOnClickListener(new LevelSelectorListener());
        }

        private class LevelSelectorListener implements OnClickListener {

            @Override
            public void onClick(View view) {

                if (currentLevel != level) {
                    levelSelect.getChildAt(Integer.parseInt(currentLevel)).setSelected(false);
                    view.setSelected(true);
                    mapDisplay.removeAllViews();
                    mapDisplay.addView(mapLayout.get(level));
                    currentLevel = level;
                }
            }
        }
    }
}
