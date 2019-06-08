package com.example.trafficlive;

public class Users {

    //should match exactly to the database key names
    public String Date,Location,Time,ViolationOrAccident;

    public Users(){

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getViolationOrAccident() {
        return ViolationOrAccident;
    }

    public void setViolationOrAccident(String violationOrAccident) {
        this.ViolationOrAccident = violationOrAccident;
    }

    public Users(String date, String location, String time, String violationOrAccident) {
        this.Date = date;
        this.Location = location;
        this.Time = time;
        this.ViolationOrAccident = violationOrAccident;
    }



}
