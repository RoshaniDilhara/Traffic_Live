package com.example.trafficlive;

import android.app.Application;

import com.firebase.client.Firebase;

public class TrafficLive extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);

    }
}
