package com.jordanklamut.interactiveevents.models;

public class EventCardView {

    String cardID;
    String cardName;
    String cardRoom;
    String cardTimes;
    int isFav;
    int isTurned;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardRoom() {
        return cardRoom;
    }

    public void setCardRoom(String cardRoom) {
        this.cardRoom = cardRoom;
    }

    public String getCardTimes() {
        return cardTimes;
    }

    public void setCardTimes(String cardTimes) {
        this.cardTimes = cardTimes;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    //public int getIsTurned() {
    //    return isTurned;
    //}
//
    //public void setIsTurned(int isTurned) {
    //    this.isTurned = isTurned;
    //}
}
