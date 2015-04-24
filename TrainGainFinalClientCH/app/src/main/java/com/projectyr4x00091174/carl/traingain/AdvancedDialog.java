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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdvancedDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdvancedDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvancedDialog extends DialogFragment {
    TextView advancedTitle;
    TextView advancedDesc;
    Button xButton;
    public AdvancedDialog() {
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
        View rootView = inflater.inflate(R.layout.fragment_advanced_dialog, container, false);
        advancedTitle = (TextView) rootView.findViewById(R.id.advT);
        advancedDesc = (TextView) rootView.findViewById(R.id.advD);
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
