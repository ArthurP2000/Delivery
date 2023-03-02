package com.example.deliveryapp;

public class Parcel {

    private String trackNum;
    private String cusId;
    private String date;

    public Parcel() {
    }

    public Parcel(String trackNum, String cusId, String date) {
        this.trackNum = trackNum;
        this.cusId = cusId;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(String trackNum) {
        this.trackNum = trackNum;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }
}
