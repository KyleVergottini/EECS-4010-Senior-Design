package com.jordanklamut.interactiveevents.models;

public class ConventionCardView {
    String cardID;
    String cardName;
    String cardLocation;
    String cardDates;
    int imageResourceId;
    int isfav;

    public int getIsfav() {
        return isfav;
    }

    public void setIsfav(int isfav) {
        this.isfav = isfav;
    }

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

    public String getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
    }

    public String getCardDates() {
        return cardDates;
    }

    public void setCardDates(String cardDates) {
        this.cardDates = cardDates;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
