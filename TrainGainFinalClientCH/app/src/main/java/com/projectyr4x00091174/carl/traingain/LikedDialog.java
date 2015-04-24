package com.projectyr4x00091174.carl.traingain;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class LikedDialog extends DialogFragment {

    SportProgram p;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liked_dialog, container, false);
        final Bundle mArgs = getArguments();
        if(getArguments().containsKey("program")) {
            System.out.println("LIKEDIALOG CONTAINS PROGRAM");
            p = mArgs.getParcelable("program");
        }
        else
        {
            System.out.println("NO PROGRAM IN LIKEDIALOG");
        }
        ImageView likeImg = (ImageView) rootView.findViewById(R.id.liked);
        mArgs.putParcelable("program", p);
        TrainingProgramDetailFragment nextFrag= new TrainingProgramDetailFragment();
        nextFrag.setArguments(mArgs);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.trainingprogram_detail_container, nextFrag).commit();
        return rootView;
    }
}