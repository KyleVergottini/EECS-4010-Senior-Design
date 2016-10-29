package com.jordanklamut.interactiveevents.models;

public class Event {
    private String eventTitle;
    private String eventRoom;
    private String eventStartTime;
    private String eventEndTime;
    private String eventDescription;

    public Event(){

    }

    public Event(String eTitle, String eRoom, String eStartTime, String eEndTime, String eDescription)
    {
        this.eventTitle = eTitle;
        this.eventRoom = eRoom;
        this.eventStartTime = eStartTime;
        this.eventEndTime = eEndTime;
        this.eventDescription = eDescription;
    }

    public String getEventTitle() {return eventTitle;}

    public void setEventTitle(String eTitle){
        this.eventTitle = eTitle;
    }

    public String getEventRoom() {return eventRoom;}

    public void setEventRoom(String eRoom){
        this.eventTitle = eRoom;
    }

    public String getEventStartTime() {return eventStartTime;}

    public void setEventStartTime(String eStartTime){
        this.eventTitle = eStartTime;
    }

    public String eventEndTime() {return eventEndTime;}

    public void eventEndTime(String eEndTime){
        this.eventTitle = eEndTime;
    }

    public String eventDescription() {return eventDescription;}

    public void eventDescription(String eDescription){
        this.eventTitle = eDescription;
    }
}
