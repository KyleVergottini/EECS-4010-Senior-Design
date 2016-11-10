package com.jordanklamut.interactiveevents.models;

public class Event {
    private String eventID;
    private String eventRoomID;
    private String eventName;
    private String eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private String eventDescription;
    private String eventFavorite;
    private int eventFavoriteResourceId;

    public Event(){

    }

    public Event(String eID, String eRoomID, String eName, String eDate, String eStartTime, String eEndTime, String eDescription, String eFavorite)
    {
        this.eventID = eID;
        this.eventRoomID = eRoomID;
        this.eventName = eName;
        this.eventDate = eDate;
        this.eventStartTime = eStartTime;
        this.eventEndTime = eEndTime;
        this.eventDescription = eDescription;
        this.eventFavorite = eFavorite;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eID) {
        this.eventID = eID;
    }

    public String getEventRoomID() {
        return eventRoomID;
    }

    public void setEventRoomID(String eRoomID) {
        this.eventRoomID = eRoomID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventFavorite() {
        return eventFavorite;
    }

    public void setEventFavorite(String eventFavorite) {
        this.eventFavorite = eventFavorite;
    }

    public int getImageResourceId() {
        return eventFavoriteResourceId;
    }

    public void setImageResourceId(int eventFavoriteResourceId) {
        this.eventFavoriteResourceId = eventFavoriteResourceId;
    }

}
