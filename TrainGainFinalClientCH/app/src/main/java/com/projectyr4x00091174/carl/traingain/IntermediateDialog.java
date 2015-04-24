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

public class IntermediateDialog extends DialogFragment {

    TextView interTitle;
    TextView interDesc;
    Button xButton;
    public IntermediateDialog() {
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
        View rootView = inflater.inflate(R.layout.fragment_intermediate_dialog, container, false);
        interTitle = (TextView) rootView.findViewById(R.id.begT);
        interDesc = (TextView) rootView.findViewById(R.id.begD);
        xButton = (Button) rootView.findViewById(R.id.xBt);
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return rootView;
    }
}
