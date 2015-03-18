package com.projectyr4x00091174.carl.traingain;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

/**
 * A fragment representing a single PersonalProgram detail screen.
 * This fragment is either contained in a {@link PersonalProgramListActivity}
 * in two-pane mode (on tablets) or a {@link PersonalProgramDetailActivity}
 * on handsets.
 */
public class PersonalProgramDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonalProgramDetailFragment() {
    }

    SportProgram programN;
    Person p = new Person();
    String username;
    String accessToken;
    String strengthGoal;
    String strengthTitle;
    Bundle extras;
    double start;
    double end;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getActivity().getIntent().getExtras();
        if (getArguments().containsKey("program")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // mItem = TrainingProgram.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            // program1 = getArguments().getParcelable("program");
            programN = extras.getParcelable("program");
            //s.Program = programN.Program;
            Log.v("tpdf", "hit if, arguments contains program");
        }
        else{
            Log.v("No program", "argument does not have key");
        }
        if(getArguments().containsKey("username"))
        {
            // userEmail = getArguments().getString("email");
            username = extras.getString("username");
            System.out.println("TrainingProgramDetailFragment Test userName: " + username);
        }
        else{
            Log.v("No Email", "argument does not have key");
        }
        if(getArguments().containsKey("accessToken"))
        {
            // userEmail = getArguments().getString("email");
            accessToken = extras.getString("accessToken");
            System.out.println("TrainingProgramDetailFragment Test access_token: " + accessToken);
        }
        else{
            Log.v("No access_token", "argument does not have key");
        }
        if(getArguments().containsKey("start") && getArguments().containsKey("end"))
        {
            start = extras.getDouble("start");
            end = extras.getDouble("end");
            Log.v("Contains start and end", "Start: " + start + " End: " + end);
        }
        else
        {
            Log.v("No Start and end", "argument does not contain keys");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personalprogram_detail, container, false);

        Button addInitialLiftsBtn = (Button) rootView.findViewById(R.id.addLifts);
        Button updateLiftsBtn = (Button) rootView.findViewById(R.id.updateLifts);
        System.out.println("ID: " + programN.IDsport + "\nSport: " + programN.Sport + "\nGoal: " + programN.Goal
                + "\nTitle: " + programN.Title + "\nProgram: " + programN.Program + "\nIntensity: " + programN.Intensity
                + "\nHrsPerDay: " + programN.HrsPerDay + "\nSessionsPerDay: " + programN.SessionsPerDay);

        strengthGoal = "Strength";
        strengthTitle = "Program for strength";
        addInitialLiftsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //if i pass in exerciselist into sportProgram constructor in Profile http method then i can retrieve
                    //those exercises here and set them to Strings and then set the blank textviews in xml file to them
                    //I might need a gridview layout and them a single item layout in another xml file so i can dynamically add
                    //the textViews and editTexts
                    case R.id.addLifts:
                        if(programN.Goal.toUpperCase().equals(strengthGoal.toUpperCase()) && programN.Title.toUpperCase().equals(strengthTitle.toUpperCase())) {
                            Intent intent = new Intent(getActivity(), ProgramResults.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                            break;
                        }
                }
            }
        });
        // Show the dummy content as text in a TextView.
        if (programN != null) {
            ((TextView) rootView.findViewById(R.id.personalprogram_detail)).setText(programN.Program + " Details");;
        }
        else{
            ((TextView)  rootView.findViewById(R.id.personalprogram_detail)).setText("Welcome to Train Droid");
        }
        return rootView;
    }
}
