package com.jordanklamut.interactiveevents.models;

public class Room {
    private String roomID;
    private String roomConventionID;
    private String roomName;
    private String roomLevel;
    private String roomXCoordinate;
    private String roomYCoordinate;

    public Room() {

    }

    public Room(String rID, String rConventionID, String rName, String rLevel, String rXCoordinate, String rYCoordinate) {
        this.roomID = rID;
        this.roomConventionID = rConventionID;
        this.roomName = rName;
        this.roomLevel = rLevel;
        this.roomXCoordinate = rXCoordinate;
        this.roomYCoordinate = rYCoordinate;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomConventionID() {
        return roomConventionID;
    }

    public void setRoomConventionID(String roomConventionID) {
        this.roomConventionID = roomConventionID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomLevel() {
        return roomLevel;
    }

    public void setRoomLevel(String roomLevel) {
        this.roomLevel = roomLevel;
    }

    public String getRoomXCoordinate() {
        return roomXCoordinate;
    }

    public void setRoomXCoordinate(String roomXCoordinate) {
        this.roomXCoordinate = roomXCoordinate;
    }

    public String getRoomYCoordinate() {
        return roomYCoordinate;
    }

    public void setRoomYCoordinate(String roomYCoordinate) {
        this.roomYCoordinate = roomYCoordinate;
    }
}
