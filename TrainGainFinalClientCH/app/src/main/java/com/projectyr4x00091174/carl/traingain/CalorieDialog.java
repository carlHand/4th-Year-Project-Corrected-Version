package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CalorieDialog extends DialogFragment {
    TextView mCalorieH, mCalorieI, mUtilH, mUtilDesc, mTEE, mTEEdesc, mFactorH, mFactorDesc,
            mActivityH, mActivitySedH, mActivitySedDesc, mActivityMildH, mActivityMildDesc,
            mActivityModH, mActivityModDesc, mActivityHeavyH, mActivityHeavyDesc, mActivityExtremeH,
            mActivityExtremeDesc;
    Button exitB;
    public CalorieDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calorie_dialog, container, false);
        mCalorieH = (TextView) rootView.findViewById(R.id.calorieHeading);
        mCalorieH.setTextSize(16);
        mCalorieH.setTextColor(Color.WHITE);
       // mCalorieI = (TextView) rootView.findViewById(R.id.calorieIntro);
        mUtilH = (TextView) rootView.findViewById(R.id.utilizationHeading);
        mUtilDesc = (TextView) rootView.findViewById(R.id.utilizationDesc);
        mTEE = (TextView) rootView.findViewById(R.id.teeHeading);
        mTEEdesc = (TextView) rootView.findViewById(R.id.teeDesc);
        mFactorH = (TextView) rootView.findViewById(R.id.factorHeading);
        mFactorDesc = (TextView) rootView.findViewById(R.id.factorD);
        mActivityH = (TextView) rootView.findViewById(R.id.activityDescH);
        mActivitySedH = (TextView) rootView.findViewById(R.id.activitySedH);
        mActivitySedH.setTextColor(Color.WHITE);
        mActivitySedDesc = (TextView) rootView.findViewById(R.id.activitySedDesc);
        mActivityMildH = (TextView) rootView.findViewById(R.id.activityMildH);
        mActivityMildDesc = (TextView) rootView.findViewById(R.id.activityMildDesc);
        mActivityModH = (TextView) rootView.findViewById(R.id.activityModH);
        mActivityModDesc = (TextView) rootView.findViewById(R.id.activityModDesc);
        mActivityHeavyH = (TextView) rootView.findViewById(R.id.activityHeavyH);
        mActivityHeavyDesc = (TextView) rootView.findViewById(R.id.activityHeavyDesc);
        mActivityExtremeH = (TextView) rootView.findViewById(R.id.activityExtremeH);
        mActivityExtremeDesc = (TextView) rootView.findViewById(R.id.activityExtremeDesc);
        exitB = (Button) rootView.findViewById(R.id.xBt);
        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return rootView;
    }
}
