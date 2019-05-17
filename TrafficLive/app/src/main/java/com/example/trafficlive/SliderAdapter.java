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

            "TEST1" ,
            "TEST2" ,
            "TEST3"
    };

    //for description

    public String[] slide_descs = {

            "desc1 grfygfv tyytfedytfwytrd6tfytf tfetwr6 tft f6rwfe6f6",
            "desc2 grfygfv tyytfedytfwytrd6tfytf tfetwr6 tft f6rwfe6f6",
            "desc3 grfygfv tyytfedytfwytrd6tfytf tfetwr6 tft f6rwfe6f6"
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
