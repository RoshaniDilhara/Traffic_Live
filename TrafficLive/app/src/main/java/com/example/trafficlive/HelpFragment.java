package com.example.trafficlive;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private Button mNextBtn;
    private Button mBackBtn;
    private int mCurrentPage;


    private SliderAdapter sliderAdapter;


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View helpview = inflater.inflate(R.layout.fragment_help, container, false);

        mSlideViewPager = helpview.findViewById(R.id.slideViewPager);
        mDotLayout = helpview.findViewById(R.id.dotsLayout);

        mNextBtn = helpview.findViewById(R.id.nextBtn);
        mBackBtn = helpview.findViewById(R.id.previousBtn);

        sliderAdapter = new SliderAdapter(getContext());
        mSlideViewPager.setAdapter(sliderAdapter);

        addDOtsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSlideViewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSlideViewPager.setCurrentItem(mCurrentPage - 1);

            }
        });

        return helpview;
    }

    public void addDOtsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0;i<mDots.length;i++){

            mDots[i] = new TextView(getContext());
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.black));

            mDotLayout.addView(mDots[i]);

        }

        if (mDots.length > 0){

            mDots[position].setTextColor(getResources().getColor(R.color.gray));

        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDOtsIndicator(i);
            mCurrentPage = i;

            if (i == 0){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");

            }else if (i == mDots.length - 1){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");

            }else {

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");

            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
