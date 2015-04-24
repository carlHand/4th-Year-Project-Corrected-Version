package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgramAddSuccessDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgramAddSuccessDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramAddSuccessDialog extends DialogFragment {

    SportProgram p;

    public ProgramAddSuccessDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program_add_success_dialog, container, false);
        final Bundle mArgs = getArguments();
        if(getArguments().containsKey("program")) {
            System.out.println("LIKEDIALOG CONTAINS PROGRAM");
            p = mArgs.getParcelable("program");
        }
        else
        {
            System.out.println("NO PROGRAM IN LIKEDIALOG");
        }
        ImageView addedImg = (ImageView) rootView.findViewById(R.id.added);
        mArgs.putParcelable("program", p);
        TrainingProgramDetailFragment nextFrag= new TrainingProgramDetailFragment();
        nextFrag.setArguments(mArgs);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.trainingprogram_detail_container, nextFrag).commit();
        return rootView;
    }
}
