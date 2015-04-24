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

import org.w3c.dom.Text;

public class BeginnerDialog extends DialogFragment {

    TextView bTitle;
    TextView bDesc;
    Button xButton;
    public BeginnerDialog() {
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
        View rootView = inflater.inflate(R.layout.fragment_beginner_dialog, container, false);
        bTitle = (TextView) rootView.findViewById(R.id.begT);
        bDesc = (TextView) rootView.findViewById(R.id.begD);
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
