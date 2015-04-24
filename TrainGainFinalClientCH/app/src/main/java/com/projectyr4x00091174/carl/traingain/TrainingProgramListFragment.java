package com.projectyr4x00091174.carl.traingain;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A list fragment representing a list of Tests. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link //TestDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TrainingProgramListFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";


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
    boolean show;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private ArrayList<SportProgram> programs = new ArrayList<SportProgram>();

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(SportProgram p);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(SportProgram p) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrainingProgramListFragment() {
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
        if(getArguments().containsKey("userShow")) {
            show = getArguments().getBoolean("userShow");
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
        Intent detailIntent = new Intent(getActivity(), TrainingProgramDetailActivity.class);
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
        listAdapter = new ExpandableListGenre(getActivity(), getActivity().getLayoutInflater(), getActivity().getSupportFragmentManager(),listDataHeader, listDataChild);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            //setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }
}