package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PersonalProgramListFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private ArrayList<SportProgram> programs = new ArrayList<SportProgram>();

    ListView mList;
    ListView mainListView;
    ListAdapter mArrayAdapter;

    ExpandableListGenre listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<SportProgram>> listDataChild;
    ImageView testBit;
    byte[] b;// = new byte[10];
    Bundle extras;
    Context context;
    String userName;
    String access_token;
    boolean userShow;

    public PersonalProgramListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        ArrayList mNameList = new ArrayList();
        SportProgram e = new SportProgram();
        ExerciseImage eImage = new ExerciseImage();
        // 4. Access the ListView
        //mainListView = (ListView) view.findViewById(R.id.trainingprogram_list);
        extras = getActivity().getIntent().getExtras();
        if(getArguments().containsKey("username") && getArguments().containsKey("accessToken"))
        {
            userName = getArguments().getString("username");
            access_token = getArguments().getString("accessToken");
            System.out.println("TESTING LIST NEW USERNAME: " + userName);
            System.out.println("TESTING LIST NEW ACCESS_TOKEN: " + access_token);
        }
        if(getArguments().containsKey("userShow"))
        {
            userShow = getArguments().getBoolean("userShow");
            System.out.println("CONTAINS usershow: " + userShow);
        }
        if (getArguments() != null && getArguments().containsKey("programs")) {
            programs = getArguments().getParcelableArrayList("programs");
            System.out.println("PSIZE:" + programs.size());

            for (int i = 0; i < programs.size(); i++) {
                e = programs.get(i);
                System.out.println("LIST FRAGMENT ID: " + e.getIDsport() + "\nSport: " + e.getSport() + "\nGoal: " + e.getGoal()
                        + "\nTitle: " + e.getTitle() + "\nProgram: " + e.getProgram() + "\nIntensity: " + e.getIntensity()
                        + "\nHrsPerDay: " + e.getHrsPerDay() + "\nSessionsPerDay: " + e.getSessionsPerDay() + "\n");
                for (int j = 0; j < programs.get(i).getExercises().size(); j++) {
                    System.out.println("NAME: " + e.getExercises().get(j).getName());
                    System.out.println("LIFT: " + e.getExercises().get(j).getLiftValue());

                    //  System.out.println("IMAGE: " + programs.get(i).getExercises().get(j).ExerciseImageL.get(k).Image.toString());
                    // System.out.println("STEP: " + programs.get(i).getExercises().get(j).ExerciseImageL.get(k).toString());

                    //  b = new byte[e.getExercises().get(j).Img.length];

//                        b = e.getExercises().get(j).ExerciseImageL.get(j).Image;//Img.clone();
                    //                      System.out.println("BITMAP IMAGE: " + b.toString());
                }
            }
            generateData();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        SportProgram p = (SportProgram) listAdapter.getChild(groupPosition, childPosition);
        extras.putParcelable("program", p);
        extras.putBoolean("userShow", userShow);
        Intent detailIntent = new Intent(getActivity(), PersonalProgramDetailActivity.class);
        detailIntent.putExtras(extras);
        startActivity(detailIntent);
        return true;
    }

    private void generateData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<SportProgram>>();

        // Adding data to list
        listDataHeader.add("Beginner");
        listDataHeader.add("Intermediate");
        listDataHeader.add("Advanced");
        String beginnerS = "Beginner";
        String intermediateS = "Intermediate";
        String advancedS = "Advanced";
        List<SportProgram> beginner = new ArrayList<SportProgram>();
        List<SportProgram> intermediate = new ArrayList<SportProgram>();
        List<SportProgram> advanced = new ArrayList<SportProgram>();

        for (int i = 0; i < programs.size(); i++) {
            SportProgram e = programs.get(i);
            if (e.getGenre().equals(beginnerS)) {
                beginner.add(e);
                programs.get(i).setGenre(beginnerS);
                System.out.println("HIT BEGINNER");

            } else if (e.getGenre().equals(intermediateS)) {
                intermediate.add(e);
                programs.get(i).setGenre(intermediateS);
                System.out.println("HIT INTERMEDIATE");
            } else if (e.getGenre().equals(advancedS)) {
                advanced.add(e);
                programs.get(i).setGenre(advancedS);
                System.out.println("HIT ADVANCED");
            } else {
                System.out.println("NOPE!!!");
            }
        }

        listDataChild.put(listDataHeader.get(0), beginner); // Header, Child data
        listDataChild.put(listDataHeader.get(1), intermediate);
        listDataChild.put(listDataHeader.get(2), advanced);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expandable_list_program_search, container, false);
        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListSearch);
        listAdapter = new ExpandableListGenre(getActivity(), getActivity().getLayoutInflater(), getActivity().getSupportFragmentManager(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(this);
        getActivity().setProgressBarIndeterminate(false);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            public void onGroupExpand(int groupPosition) {
                int len = listAdapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        expListView.collapseGroup(i);
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeIntent = new Intent(getActivity(), NavigationDrawer.class);
                Bundle extras = getActivity().getIntent().getExtras();
                homeIntent.putExtras(extras);
                startActivity(homeIntent);
                break;
            case R.id.action_search:
                fragment = new SearchProgram();
                Bundle bundle = getActivity().getIntent().getExtras();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("").commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
