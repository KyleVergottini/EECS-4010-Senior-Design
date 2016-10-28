package com.jordanklamut.interactiveevents.models;

/**
 * Created by jorda on 9/14/2016.
 */

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

    public Convention(){

    }

    public Convention(String cID, String cName, String cStartDate, String cEndDate, String cStreetAddress, String cCity, String cState, String cZipCode, String cDescription)
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
}
