package com.projectyr4x00091174.carl.traingain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//Fragment explaining BMI to the user, invoked from CalcHealthMetrics.class/ExampleAdapter.class
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BMIDialog extends DialogFragment {
    TextView bmiH1, bmiH2, bmiH3, bmiT, bmiD1, bmiD2, bmiD3;
    Button exitB;
    public BMIDialog() {
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
        View rootView = inflater.inflate(R.layout.fragment_bmidialog, container, false);
        bmiH1 = (TextView) rootView.findViewById(R.id.bmiHeading);
        bmiD1 = (TextView) rootView.findViewById(R.id.bmiIntro);
        bmiH2 = (TextView) rootView.findViewById(R.id.bmiHeading2);
        bmiD2 = (TextView) rootView.findViewById(R.id.bmiMean);
        bmiH3 = (TextView) rootView.findViewById(R.id.bmiHeading3);
        bmiD3 = (TextView) rootView.findViewById(R.id.bmiDesc3);
        bmiT = (TextView) rootView.findViewById(R.id.bmiTableHeading);
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
