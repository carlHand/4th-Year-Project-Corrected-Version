package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import java.util.ArrayList;

/**
 * A list fragment representing a list of Tests. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link TestDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TrainingProgramListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";


    ListView mainListView;
    ListAdapter mArrayAdapter;

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
        // Get courses array from argument
        ArrayList mNameList = new ArrayList();
        // 4. Access the ListView
        //mainListView = (ListView) view.findViewById(R.id.trainingprogram_list);
        if (getArguments() != null && getArguments().containsKey("programs")) {
            programs = getArguments().getParcelableArrayList("programs");
           // Log.v("myApp", "List Fragment: argument first coursee:" + programs.get(0).title);
           // Log.v("myApp", "List Fragment: argument first coursee:" + programs.get(0).program);
            /*
            mArrayAdapter = new ArrayAdapter<TrainingProgram>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    programs);
            setListAdapter(mArrayAdapter);
            */
            setListAdapter(new ArrayAdapter<SportProgram>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    //android.R.id.text1,
                    programs));
        }

        // Set the ListView to use the ArrayAdapter



           // Log.v("test content is null", "content is: "+ programs.get(0).program);
            // Create an ArrayAdapter for the ListView

/*

            setListAdapter(new ArrayAdapter<TrainingProgram>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    //android.R.id.text1,
                    programs));
*/

            // Initialize the display adapter


    }
/*
        // TODO: replace with a real list adapter.
        setListAdapter(new ArrayAdapter<TrainingProgram.ProgramContent>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                TrainingProgram.ITEMS));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_trainingprogram_list, container, false);
        mainListView = (ListView) rootView.findViewById(R.id.trainingprogram_list);
        if (getArguments() != null && getArguments().containsKey("programs")) {
            programs = getArguments().getParcelableArrayList("programs");
            Log.v("myApp", "List Fragment: argument first coursee:" + programs.get(0).title);
            Log.v("myApp", "List Fragment: argument first coursee:" + programs.get(0).program);
            Log.v("myApp", "List Fragment: argument first coursee:" + programs.get(0).goal);
            mArrayAdapter = new ArrayAdapter<TrainingProgram>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    programs);
            setListAdapter(mArrayAdapter);
            // Show the dummy content as text in a TextView.
        }
        return mainListView;
    }
*/
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
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
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
//        mCallbacks.onItemSelected(TrainingProgram.ITEMS.get(position).getTitle());
            mCallbacks.onItemSelected((SportProgram) listView.getItemAtPosition(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
