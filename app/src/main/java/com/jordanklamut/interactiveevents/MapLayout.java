package com.jordanklamut.interactiveevents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.common.collect.Lists;
import com.jordanklamut.interactiveevents.models.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class MapLayout extends FrameLayout
{

    private static final int INVALID_POINTER_ID = -1;

    private int mActivePointerId = INVALID_POINTER_ID;

    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private float viewWidth;
    private float viewHeight;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private float minScaleFactor;

    private float mPosX;
    private float mPosY;

    private float mLastTouchX, mLastTouchY;

    private MapView mapView;
    private ImageView roomView;
    private MapInfoLayout infoView;

    private static final int ROOM_ICON_WIDTH = 80;
    private static final int ROOM_ICON_HEIGHT = 120;

    private static final int ROOM_ICON_CENTER_X = ROOM_ICON_WIDTH / 2;
    private static final int ROOM_ICON_CENTER_Y = ROOM_ICON_HEIGHT;

    private List<Room> roomListDraw;
    private List<Room> roomListTouch;
    final private Bitmap unpressedRoomIcon = Bitmap.createScaledBitmap(((BitmapDrawable) ContextCompat.getDrawable(this.getContext(), R.drawable.map_icon_unpressed)).getBitmap(), ROOM_ICON_WIDTH, ROOM_ICON_HEIGHT, true);
    final private Bitmap highlightedRoomIcon = Bitmap.createScaledBitmap(((BitmapDrawable) ContextCompat.getDrawable(this.getContext(), R.drawable.map_icon_highlighted)).getBitmap(), ROOM_ICON_WIDTH, ROOM_ICON_HEIGHT, true);

    private String highlightedRoom = "";

    private boolean firstDraw = true;
    private String startingRoom = "";

    public MapLayout(Context context, Bitmap mapImage, List<Room> roomList) {
        super(context);
        this.bitmap = mapImage;
        this.bitmapWidth = mapImage.getWidth();
        this.bitmapHeight = mapImage.getHeight();

        //RoomList is saved in forward and reverse order because we need to draw them one way and check for touch events in the other
        this.roomListTouch = roomList;
        this.roomListDraw = new ArrayList(roomList);
        Collections.reverse(roomListDraw);

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        mapView = new MapView(context, mapImage);
        roomView = new ImageView(context);
        infoView = new MapInfoLayout(context);
        this.addView(mapView);
        this.addView(roomView);
        this.addView(infoView);
    }

    public MapLayout(Context context, Bitmap mapImage, List<Room> roomList, String startingRoom) {
        this(context, mapImage, roomList);
        this.startingRoom = startingRoom;
    }

    private long startClickTime;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev); //Pass event to scale detector
        final int action = ev.getAction(); //Get Action

        switch (action & MotionEvent.ACTION_MASK){ //Switch for action
            case MotionEvent.ACTION_DOWN: { //Case: Finger is pressed down on the screen
                startClickTime = Calendar.getInstance().getTimeInMillis(); //Get time when finger is pressed

                final float x = ev.getX(); //Get X coordinate at which finger is pressed
                final float y = ev.getY(); //Get Y coordinate...

                mLastTouchX = x; //Set mLastTouchX to coordinates
                mLastTouchY = y; //Set mLastTouchY to coordinates
                mActivePointerId = ev.getPointerId(0); //Get pointer for current press
                break;
            }

            case MotionEvent.ACTION_MOVE: { //Case: Finger is moved on screen
                highlightedRoom = ""; //Lose focus on the highlighted room when moving

                final int pointerIndex = ev.findPointerIndex(mActivePointerId); //Get pointer of last finger pressed
                final float x = ev.getX(pointerIndex); //Get the new X coordinate for this pointer
                final float y = ev.getY(pointerIndex); //Get the new Y coordinate...

                if (!mScaleDetector.isInProgress()) { // Only move if the ScaleGestureDetector isn't processing a gesture.
                    float dx = x - mLastTouchX; //Find X difference between new and old positions
                    float dy = y - mLastTouchY; //Find Y difference between new and old positions

                    //Adjust for zoom factor. Otherwise, the user's finger moving 10 pixels
                    //at 200% zoom causes the image to slide 20 pixels instead of perfectly
                    //following the user's touch
                    dx /= (mScaleFactor * 2); //Update the X difference based on the scale factor
                    dy /= (mScaleFactor * 2); //Update the Y difference...

                    mPosX += dx; //Update the X position of image view with the difference
                    mPosY += dy; //Update the Y position...

                    Redraw(); //Cause the view to redraw
                }

                mLastTouchX = x; //Update last touch X to be the new position
                mLastTouchY = y; //Update last touch Y...

                break;
            }

            case MotionEvent.ACTION_UP: { //Case: Last finger is lifted
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < 1000) //If it's been less than a second since the screen was touched, register this as a click
                {
                    int translatedClickMaxX = (int) (((mLastTouchX + ROOM_ICON_CENTER_X) / (mScaleFactor)) - (2 * mPosX));
                    int translatedClickMinX = (int) (translatedClickMaxX - (ROOM_ICON_WIDTH / mScaleFactor));
                    int translatedClickMaxY = (int) (((mLastTouchY + ROOM_ICON_CENTER_Y) / (mScaleFactor)) - (2 * mPosY));
                    int translatedClickMinY = (int) (translatedClickMaxY - (ROOM_ICON_HEIGHT / mScaleFactor));
                    boolean roomPressed = false;
                    for (Room currentRoom : roomListTouch) {
                        if (    Integer.parseInt(currentRoom.getRoomXCoordinate()) >= translatedClickMinX
                            &&  Integer.parseInt(currentRoom.getRoomXCoordinate()) <= translatedClickMaxX
                            &&  Integer.parseInt(currentRoom.getRoomYCoordinate()) >= translatedClickMinY
                            &&  Integer.parseInt(currentRoom.getRoomYCoordinate()) <= translatedClickMaxY)
                        {
                            //If touch coordinates fall in range of a room, set that room as highlighted and break
                            highlightedRoom = currentRoom.getRoomID();
                            roomPressed = true;
                            break;
                        }
                    }
                    if (!roomPressed)
                    {
                        //If no room is pressed, clear any highlights
                        highlightedRoom = "";
                    }
                    Redraw();
                }
                mActivePointerId = INVALID_POINTER_ID; //Invalidate active pointer
                break;
            }

            case MotionEvent.ACTION_CANCEL: { //Case: Action is cancelled
                mActivePointerId = INVALID_POINTER_ID; //Same as if finger is lifted
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: { //Case: One finger was lifted while multiple are on screen
                final int pointerIndex = (ev.getAction() //Get event action
                        & MotionEvent.ACTION_POINTER_INDEX_MASK) //Mask to only show indices
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT; //Shift to get index bits
                final int pointerId = ev.getPointerId(pointerIndex); //Get pointer for action
                if (pointerId == mActivePointerId) { //If the pointer lifted was the active pointer
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0; // Flip the active pointer index to get the new pointer index
                    mLastTouchX = ev.getX(newPointerIndex); //Get the X coordinate of the new pointer index
                    mLastTouchY = ev.getY(newPointerIndex); //Get the Y coordinate...
                    mActivePointerId = ev.getPointerId(newPointerIndex); //Set the Id of the new pointer index to be the active one
                }
                break;
            }
        }
        return true;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) { //When scale gesture is detected
            mScaleFactor *= detector.getScaleFactor(); //Get the new scale factor
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f)); //Set the new scale factor to be a max of 5.0f and min of 0.1f
            Redraw(); //Force the view to redraw
            return true;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;

        float minXScaleFactor = viewWidth / (float) bitmapWidth; //Set minimum X Scale Factor
        float minYScaleFactor = viewHeight / (float) bitmapHeight; //and Y Scale Factor
        minScaleFactor = Math.max(minXScaleFactor, minYScaleFactor); //Min scale factor is the greater of X and Y
        mScaleFactor = minScaleFactor; //start out "zoomed out" all the way
        mPosX = mPosY = 0; //Set X and Y positions to false
        if (firstDraw && !startingRoom.equals(""))
        {
            //If we're on the first draw, and a starting room was set, focus the starting room was the map has been rendered
            this.post(new Runnable() {
                @Override
                public void run() {
                    FocusRoom(startingRoom);
                    Redraw();
                }
            });
        }
        firstDraw = false;
        Redraw();
        return;
    }

    public void Redraw()
    {
        mScaleFactor = Math.max(mScaleFactor, minScaleFactor); //Make sure scale factor isn't greater than min

        int maxX, minX, maxY, minY;
        //Regardless of the screen density (HDPI, MDPI) or the scale factor,
        //The image always consists of bitmap width divided by 2 pixels. If an image
        //is 200 pixels wide and you scroll right 100 pixels, you just scrolled the image
        //off the screen to the left.
        minX = (int) (((viewWidth / mScaleFactor) - bitmapWidth) / 2);
        maxX = 0;
        //How far can we move the image vertically without having a gap between image and frame?
        minY = (int) (((viewHeight / mScaleFactor) - bitmapHeight) / 2);
        maxY = 0;
        //Do not go beyond the boundaries of the image
        if (mPosX > maxX) {
            mPosX = maxX;
        }
        if (mPosX < minX) {
            mPosX = minX;
        }
        if (mPosY > maxY) {
            mPosY = maxY;
        }
        if (mPosY < minY) {
            mPosY = minY;
        }

        mapView.UpdateBounds(mScaleFactor, mPosX, mPosY);
        roomView.setImageBitmap(DrawRoomsOntoMap());

        return;
    }

    public Bitmap DrawRoomsOntoMap() {
        if (!roomListDraw.isEmpty()) {
            Room highlightedRoomObject = null;
            infoView.clear(); //Clear information overlay before drawing
            Bitmap mapWithRooms = Bitmap.createBitmap((int)viewWidth, (int)viewHeight, bitmap.getConfig());
            Canvas canvas = new Canvas(mapWithRooms);
            for (Room currentRoom : roomListDraw) {
                //Determine position that room would need to be drawn on the screen
                int drawPosX = (int)(mScaleFactor * ((Integer.parseInt(currentRoom.getRoomXCoordinate())) + (2 * mPosX)) - ROOM_ICON_CENTER_X);
                int drawPosY = (int)(mScaleFactor * ((Integer.parseInt(currentRoom.getRoomYCoordinate())) + (2 * mPosY)) - ROOM_ICON_CENTER_Y);
                //Check if the room would be appear on the screen and if we need to draw it
                if (    drawPosX >= -ROOM_ICON_WIDTH && drawPosX <= viewWidth + ROOM_ICON_WIDTH
                    &&  drawPosY >= -ROOM_ICON_HEIGHT && drawPosY <= viewHeight + ROOM_ICON_HEIGHT) {
                    if (currentRoom.getRoomID().equals(highlightedRoom)) {
                        //If the roomID matches what was pressed, save it as the highlighted room to draw last
                        highlightedRoomObject = currentRoom;
                    }
                    else
                    {
                        //Otherwise, draw the normal room icon
                        canvas.drawBitmap(unpressedRoomIcon, drawPosX, drawPosY, null);
                    }
                }
            }
            if (highlightedRoomObject != null)
            {
                //Draw the highlighted room icon on the map
                int drawPosX = (int)(mScaleFactor * ((Integer.parseInt(highlightedRoomObject.getRoomXCoordinate())) + (2 * mPosX)) - ROOM_ICON_CENTER_X);
                int drawPosY = (int)(mScaleFactor * ((Integer.parseInt(highlightedRoomObject.getRoomYCoordinate())) + (2 * mPosY)) - ROOM_ICON_CENTER_Y);
                canvas.drawBitmap(highlightedRoomIcon, drawPosX, drawPosY, null);
                //Set layout parameters for the information window
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) infoView.getLayoutParams();
                int infoGravity = Gravity.NO_GRAVITY;
                params.leftMargin = 50;
                params.rightMargin = 50;
                //Left/right gravity is set based on which side of the screen we want to draw it on
                if (drawPosX + (ROOM_ICON_WIDTH / 2) <= (viewWidth / 2)) {
                    infoGravity |= Gravity.LEFT;
                } else {
                    infoGravity |= Gravity.RIGHT;
                }
                //Top/bottom gravity also is set based on the room position, but margins are also set so it lies next to the icon
                if (drawPosY + (ROOM_ICON_HEIGHT / 2) <= (viewHeight / 2)) {
                    params.topMargin = drawPosY + ROOM_ICON_HEIGHT + 50;
                    params.bottomMargin = 50;
                    infoGravity |= Gravity.TOP;
                } else {
                    params.topMargin = 50;
                    params.bottomMargin = (int)viewHeight - drawPosY + 50;
                    infoGravity |= Gravity.BOTTOM;
                }
                infoView.setLayoutParams(params);
                infoView.setGravity(infoGravity);
                infoView.setRoomInformation(highlightedRoomObject);
            }
            return mapWithRooms;
        }
        else {
            return null;
        }
    }

    public void FocusRoom(String roomID)
    {
        for (Room currentRoom : roomListDraw) {
            //Locate the room with the passed in ID
            if (currentRoom.getRoomID().equals(roomID)) {
                //Room is highlighted and screen position is set to put room as close to center screen as possible
                highlightedRoom = roomID;
                mPosX = (viewWidth / (4 * mScaleFactor)) - (Integer.parseInt(currentRoom.getRoomXCoordinate()) / 2);
                mPosY = (viewHeight / (4 * mScaleFactor)) - (Integer.parseInt(currentRoom.getRoomYCoordinate()) / 2);
                return;
            }
        }
    }
}
