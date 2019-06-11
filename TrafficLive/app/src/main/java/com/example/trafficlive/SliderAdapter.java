package com.example.trafficlive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){

        this.context = context;
    }

    //Arrays

    //for images

//    public int[] slide_images = {
//
//            R.drawable.userphoto,
//            R.drawable.userphoto,
//            R.drawable.userphoto
//
//    };

    //for headings

    public String[] slide_headings = {

            "HOME" ,
            "DATA ENTRY PAGE" ,
            "PREVIOUS VIOLATION SEARCH"
    };

    //for description

    public String[] slide_descs = {

            "Welcome screen is the first appeared screen when the application is opened.Then you will get Login and Registration screens.After login process you will get the data entry page for violations. ",
            "You can include all of details of the traffic violation from this page." +
                    "First select whether it is a violation or an accident. And then under that category select the violation or accident type."+
                    "In QR Scanner, click the button and then you will automatically open the camera and you can scan the QR code in the license." +
                    "By clicking Recognize Number Plate button you wil open the camera and scan the vehicle number plate."+
                    "By clicking Get Current Locattion button you can get the current location,location of the accident happened."+
                    "By touching Data box,you can get the date."+
                    "From Get Time button, you can get the current time."+
                    "After filling all the datas you should submit data.",
            "This is the page of search for previous violations by using NIC number.From this you can search all details of previous details which was done by the relevant person."
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

//        ImageView slideImageView = (ImageView)view.findViewById(R.id.regUserPhoto);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

//        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);

    }
}
