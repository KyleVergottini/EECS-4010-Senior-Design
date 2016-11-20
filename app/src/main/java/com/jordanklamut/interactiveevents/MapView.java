package com.jordanklamut.interactiveevents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MapView extends View {

    private Bitmap bitmap;
    private float mScaleFactor = 1.f;

    private float mPosX;
    private float mPosY;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, Bitmap mapImage)
    {
        super(context);
        setImageBitmap(mapImage);
    }

    public void setImageBitmap(Bitmap bmp) {
        bitmap = bmp;
        resetZoom();
        resetPan();
        invalidate();
    }

    public void resetZoom() {
        mScaleFactor = 1.0f;
    }

    public void resetPan() {
        mPosX = 0f;
        mPosY = 0f;
    }

    public void setImageDrawable(Drawable drawable) {
        setImageBitmap(((BitmapDrawable) drawable).getBitmap());
    }

    public BitmapDrawable getImageDrawable() {
        BitmapDrawable bd = new BitmapDrawable(getContext().getResources(), bitmap);
        return bd;
    }

    public void UpdateBounds(float newScaleFactor, float newPosX, float newPosY)
    {
        mScaleFactor = newScaleFactor;
        mPosX = newPosX;
        mPosY = newPosY;
        invalidate();
        return;
    }

    public void onDraw(Canvas canvas) { //Generates image

        if (bitmap == null) {
            super.onDraw(canvas);
            return;
        }

        //Save the canvas without translating (panning) or scaling (zooming)
        //After each change, restore to this state, instead of compounding
        //changes upon changes
        canvas.save();

        //Scale canvas using updated scale factor
        canvas.scale(mScaleFactor, mScaleFactor);

        //Pan canvas to specific coordinates
        canvas.translate(mPosX, mPosY);

        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, mPosX, mPosY, null);
        canvas.restore(); //clear translation/scaling
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}