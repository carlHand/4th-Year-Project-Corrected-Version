package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HeartRateDialog extends DialogFragment {
    TextView mHeartH1, mHeartIntro, mZoneH, mZone1H, mZone1Desc, mZone2H,
            mZone2Desc, mZone3H, mZone3Desc, mZone4H, mZone4Desc;
    Button exitB;
    public HeartRateDialog() {
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
        View rootView = inflater.inflate(R.layout.fragment_heart_rate_dialog, container, false);
        mHeartH1 = (TextView) rootView.findViewById(R.id.heartHeading);
        mHeartH1.setTextSize(16);
        mHeartH1.setTextColor(Color.WHITE);
        mHeartIntro = (TextView) rootView.findViewById(R.id.heartI);
        mZoneH = (TextView) rootView.findViewById(R.id.zoneH);
        mZoneH.setTextColor(Color.WHITE);
        mZone1H = (TextView) rootView.findViewById(R.id.zone1H);
        mZone1H.setTextColor(Color.WHITE);
        mZone1Desc = (TextView) rootView.findViewById(R.id.zone1D);
        mZone2H = (TextView) rootView.findViewById(R.id.zone2H);
        mZone2H.setTextColor(Color.WHITE);
        mZone2Desc = (TextView) rootView.findViewById(R.id.zone2D);
        mZone3H = (TextView) rootView.findViewById(R.id.zone3H);
        mZone3H.setTextColor(Color.WHITE);
        mZone3Desc = (TextView) rootView.findViewById(R.id.zone3D);
        mZone4H = (TextView) rootView.findViewById(R.id.zone4H);
        mZone4H.setTextColor(Color.WHITE);
        mZone4Desc = (TextView) rootView.findViewById(R.id.zone4D);
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
