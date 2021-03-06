package com.jordanklamut.interactiveevents.models;

public class Convention {
    private String conID;
    private String conName;
    private String conStartDate;
    private String conEndDate;
    private String conStreetAddress;
    private String conCity;
    private String conState;
    private String conZipCode;
    private String conDescription;
    private String conFavorite;
    private int conFavoriteResourceId;


    public Convention(){

    }

    public Convention(String cID, String cName, String cStartDate, String cEndDate, String cStreetAddress, String cCity, String cState, String cZipCode, String cDescription, String cFavorite)
    {
        this.conID = cID;
        this.conName = cName;
        this.conStartDate = cStartDate;
        this.conEndDate = cEndDate;
        this.conStreetAddress = cStreetAddress;
        this.conCity = cCity;
        this.conState = cState;
        this.conZipCode = cZipCode;
        this.conDescription = cDescription;
        this.conFavorite = cFavorite;
    }

    public String getConID() {return conID;}

    public void setConID(String conID){
        this.conID = conID;
    }

    public String getConName() {return conName;}

    public void setConName(String conName) { this.conName = conName; }

    public String getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(String conStartDate) {
        this.conStartDate = conStartDate;
    }

    public String getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(String conEndDate) {
        this.conEndDate = conEndDate;
    }

    public String getConStreetAddress() {
        return conStreetAddress;
    }

    public void setConStreetAddress(String conStreetAddress) {
        this.conStreetAddress = conStreetAddress;
    }

    public String getConCity() {
        return conCity;
    }

    public void setConCity(String conCity) {
        this.conCity = conCity;
    }

    public String getConState() {
        return conState;
    }

    public void setConState(String conState) {
        this.conState = conState;
    }

    public String getConZipCode() {
        return conZipCode;
    }

    public void setConZipCode(String conZipCode) {
        this.conZipCode = conZipCode;
    }

    public String getConDescription() {
        return conDescription;
    }

    public void setConDescription(String conDescription) {
        this.conDescription = conDescription;
    }

    public String getConFavorite() {
        return conFavorite;
    }

    public void setConFavorite(String conFavorite) {
        this.conFavorite = conFavorite;
    }

    public int getConFavoriteResourceId() {
        return conFavoriteResourceId;
    }

    public void setConFavoriteResourceId(int conFavoriteResourceId) {
        this.conFavoriteResourceId = conFavoriteResourceId;
    }
}
