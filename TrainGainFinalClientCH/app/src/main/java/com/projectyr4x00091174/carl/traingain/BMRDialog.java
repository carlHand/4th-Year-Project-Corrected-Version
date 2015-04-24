package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//Fragment explaining BMR to user, invoked from CalcHealthMetrics.class/ExampleAdapter.class
public class BMRDialog extends DialogFragment {
    TextView bmrH1, bmrDesc, bmrVarH, bmrVarDesc, bmrH2, bmrDesc2, bmrSubH, bmrSubDesc;
    Button exitB;
    public BMRDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bmrdialog, container, false);
        bmrH1 = (TextView) rootView.findViewById(R.id.bmrHeading);
        bmrDesc = (TextView) rootView.findViewById(R.id.bmrDesc);
        bmrVarH = (TextView) rootView.findViewById(R.id.varianceHeading);
        bmrVarDesc = (TextView) rootView.findViewById(R.id.varianceDesc);
        bmrH2 = (TextView) rootView.findViewById(R.id.bmrHeading2);
        bmrDesc2 = (TextView) rootView.findViewById(R.id.bmrDesc2);
        bmrSubH = (TextView) rootView.findViewById(R.id.summHeading);
        bmrSubDesc = (TextView) rootView.findViewById(R.id.summaryDesc);
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
