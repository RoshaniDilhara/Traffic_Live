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

    ArrayList<String> dateList;
    ArrayList<String> timeList;
    ArrayList<String> violOrAccList;
    ArrayList<String> faultList;
    ArrayList<String> licenceDetailsList;
    ArrayList<String> numplateList;
    ArrayList<String> locationList;


    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView Date,Time,ViolationOrAccident,Fault,LicenceDetails,NumberPlate,Location;


        public SearchViewHolder(@NonNull View itemView) {

            super(itemView);

            Date = (TextView) itemView.findViewById(R.id.Vdate);
            Time = (TextView) itemView.findViewById(R.id.Vtime);
            ViolationOrAccident = (TextView) itemView.findViewById(R.id.accident_violation);
            Fault= (TextView) itemView.findViewById(R.id.Vfault);
            LicenceDetails = (TextView) itemView.findViewById(R.id.VLicenceDetails);
            NumberPlate = (TextView) itemView.findViewById(R.id.Vnumberplate);
            Location = (TextView) itemView.findViewById(R.id.Vlocation);

        }

    }

    public Users(Context context,  ArrayList<String> dateList, ArrayList<String> timeList, ArrayList<String> violOrAccList, ArrayList<String> faultList,ArrayList<String> licenceDetailsList, ArrayList<String> numplateList, ArrayList<String> locationList) {
        this.context = context;

        this.dateList = dateList;
        this.timeList = timeList;
        this.violOrAccList = violOrAccList;
        this.faultList =faultList;
        this.licenceDetailsList = licenceDetailsList;
        this.numplateList=numplateList;
        this.locationList=locationList;
    }


    @NonNull
    @Override
    public Users.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_layout,viewGroup,false);


        return new Users.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {

        searchViewHolder.Date.setText(dateList.get(i));
        searchViewHolder.Time.setText(timeList.get(i));
        searchViewHolder.ViolationOrAccident.setText(violOrAccList.get(i));
        searchViewHolder.Fault.setText(faultList.get(i));
        searchViewHolder.LicenceDetails.setText(licenceDetailsList.get(i));
        searchViewHolder.NumberPlate.setText(numplateList.get(i));
        searchViewHolder.Location.setText(locationList.get(i));
    }

    @Override
    public int getItemCount() {
        return licenceDetailsList.size();
    }


}
