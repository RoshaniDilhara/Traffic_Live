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
        Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getViolationOrAccident() {
        return ViolationOrAccident;
    }

    public void setViolationOrAccident(String violationOrAccident) {
        ViolationOrAccident = violationOrAccident;
    }

    public Users(String date, String location, String time, String violationOrAccident) {
        Date = date;
        Location = location;
        Time = time;
        ViolationOrAccident = violationOrAccident;
    }



}
