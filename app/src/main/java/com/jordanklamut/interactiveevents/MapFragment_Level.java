package com.jordanklamut.interactiveevents;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordanklamut.interactiveevents.models.Event;
import com.jordanklamut.interactiveevents.models.Room;

import java.util.ArrayList;

public class MapFragment_Level extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Room> roomList = new ArrayList<>();
    private Bitmap mapImage;

    DatabaseManager dm;

    public MapFragment_Level() {

    }

    public static MapFragment_Level newInstance(String level) {
        MapFragment_Level fragment = new MapFragment_Level();
        Bundle args = new Bundle();
        args.putString("level", level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DatabaseManager(this.getActivity());

        SharedPreferences csp = getActivity().getSharedPreferences("login_pref", 0);
        String conventionID = csp.getString("homeConventionID", null);

        String level = getArguments().getString("level");

        initializeList(conventionID, level);
        setMapImage(conventionID, level);
        getActivity().setTitle("Map");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new MapLayout(getActivity(), mapImage, roomList);
    }

    public void initializeList(String conventionID, String level) {
        roomList.clear();

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

        return;
    }

    public void setMapImage(String conventionID, String level) {
        int mapImageID = getResources().getIdentifier(conventionID + "_" + level,"drawable","com.jordanklamut.interactiveevents");

        if (mapImageID == 0)
        {
            mapImage = null;
        }
        else
        {
            mapImage = ((BitmapDrawable) getResources().getDrawable(mapImageID, null)).getBitmap();
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
}
