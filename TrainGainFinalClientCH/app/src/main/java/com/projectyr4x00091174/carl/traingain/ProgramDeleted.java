package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgramDeleted.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgramDeleted#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramDeleted extends DialogFragment {
    SportProgram p;
    public ProgramDeleted() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liked_dialog, container, false);
        final Bundle mArgs = getArguments();
        if (getArguments().containsKey("program")) {
            System.out.println("LIKEDIALOG CONTAINS PROGRAM");
            p = mArgs.getParcelable("program");
        } else {
            System.out.println("NO PROGRAM IN LIKEDIALOG");
        }
        ImageView deletedImg = (ImageView) rootView.findViewById(R.id.deleted);
        mArgs.putParcelable("program", p);
        PersonalProgramDetailFragment nextFrag = new PersonalProgramDetailFragment();
        nextFrag.setArguments(mArgs);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.personalprogram_detail_container, nextFrag).commit();
        return rootView;
    }
}
