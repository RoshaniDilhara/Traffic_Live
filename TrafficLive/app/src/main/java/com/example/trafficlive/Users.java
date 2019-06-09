package com.example.trafficlive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Users extends RecyclerView.Adapter<Users.SearchViewHolder> {

    Context context;

    ArrayList<String> licenceDetailsList;
    ArrayList<String> dateList;
    ArrayList<String> timeList;
    ArrayList<String> violOrAccList;


    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView LicenceDetails,Date,Time,ViolationOrAccident;


        public SearchViewHolder(@NonNull View itemView) {

            super(itemView);

            LicenceDetails = (TextView) itemView.findViewById(R.id.VLicenceDetails);
            Date = (TextView) itemView.findViewById(R.id.Vdate);
            Time = (TextView) itemView.findViewById(R.id.Vtime);
            ViolationOrAccident = (TextView) itemView.findViewById(R.id.accident_violation);
        }

    }

    public Users(Context context, ArrayList<String> licenceDetailsList, ArrayList<String> dateList, ArrayList<String> timeList, ArrayList<String> violOrAccList) {
        this.context = context;
        this.licenceDetailsList = licenceDetailsList;
        this.dateList = dateList;
        this.timeList = timeList;
        this.violOrAccList = violOrAccList;
    }


    @NonNull
    @Override
    public Users.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_layout,viewGroup,false);


        return new Users.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.ViolationOrAccident.setText(violOrAccList.get(i));
        searchViewHolder.Date.setText(dateList.get(i));
        searchViewHolder.Time.setText(timeList.get(i));
        searchViewHolder.LicenceDetails.setText(licenceDetailsList.get(i));
    }

    @Override
    public int getItemCount() {
        return licenceDetailsList.size();
    }


//    //should match exactly to the database key names
//    public String Date,Location,Time,ViolationOrAccident;
//
//    public Users(){
//
//    }
//
//    public String getDate() {
//        return Date;
//    }
//
//    public void setDate(String date) {
//        this.Date = date;
//    }
//
//    public String getLocation() {
//        return Location;
//    }
//
//    public void setLocation(String location) {
//        this.Location = location;
//    }
//
//    public String getTime() {
//        return Time;
//    }
//
//    public void setTime(String time) {
//        this.Time = time;
//    }
//
//    public String getViolationOrAccident() {
//        return ViolationOrAccident;
//    }
//
//    public void setViolationOrAccident(String violationOrAccident) {
//        this.ViolationOrAccident = violationOrAccident;
//    }
//
//    public Users(String date, String location, String time, String violationOrAccident) {
//        this.Date = date;
//        this.Location = location;
//        this.Time = time;
//        this.ViolationOrAccident = violationOrAccident;
//    }



}
