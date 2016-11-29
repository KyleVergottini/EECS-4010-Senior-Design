package com.jordanklamut.interactiveevents.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ConventionMap {
    private String mapConventionID;
    private byte[] map1;
    private byte[] map2;
    private byte[] map3;

    public ConventionMap() {

    }

    public ConventionMap(String mapConventionID) {
        this.mapConventionID = mapConventionID;
    }

    public String getMapConventionID() {
        return mapConventionID;
    }

    public void setMapConventionID(String mapConventionID) {
        this.mapConventionID = mapConventionID;
    }

    public byte[] getMap1() {
        return map1;
    }

    public void setMap1(byte[] map1) {
        this.map1 = map1;
    }

    public byte[] getMap2() {
        return map2;
    }

    public void setMap2(byte[] map2) {
        this.map2 = map2;
    }

    public byte[] getMap3() {
        return map3;
    }

    public void setMap3(byte[] map3) {
        this.map3 = map3;
    }

    public Bitmap getMapImage1() {
        return (map1 == null ? null : BitmapFactory.decodeByteArray(map1, 0, map1.length));
    }

    public Bitmap getMapImage2() {
        return (map2 == null ? null : BitmapFactory.decodeByteArray(map2, 0, map2.length));
    }

    public Bitmap getMapImage3() {
        return (map3 == null ? null : BitmapFactory.decodeByteArray(map3, 0, map3.length));
    }
}