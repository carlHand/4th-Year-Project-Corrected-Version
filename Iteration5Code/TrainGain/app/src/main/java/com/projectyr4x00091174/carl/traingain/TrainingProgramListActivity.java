package com.projectyr4x00091174.carl.traingain;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
//import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingProgramListActivity extends FragmentActivity
        implements TrainingProgramListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //      Bundle b = getIntent().getExtras();
//        String result = b.getString("program");
       // ArrayList<TrainingProgram> programs =  (ArrayList<TrainingProgram>) getIntent().getSerializableExtra("programs");
        ArrayList<SportProgram> programs = this.getIntent().getParcelableArrayListExtra("programs");
        //Bundle extras = getIntent().getExtras();
        //ArrayList<TrainingProgram> programs = extras.getParcelableArrayList("programs");

        FragmentManager fm = getFragmentManager();
        setContentView(R.layout.activity_trainingprogram_list);
        if (findViewById(R.id.trainingprogram_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.

            mTwoPane = true;
            TrainingProgramDetailFragment pf = (TrainingProgramDetailFragment) fm.findFragmentByTag("Detail");
            if(pf == null)
            {
                pf = new TrainingProgramDetailFragment();
                Log.v("myApp", "List Activity: Initialize new detail view");
                pf = new TrainingProgramDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("program", new SportProgram("Welcome to Golf Droid","program"));//, "Details"));
                pf.setArguments(args);
                fm.beginTransaction().replace(R.id.trainingprogram_detail_container, pf, "Detail").commit();
            }

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.

            ((TrainingProgramListFragment) getFragmentManager()
                    .findFragmentById(R.id.trainingprogram_list))
                    .setActivateOnItemClick(true);

        }
        TrainingProgramListFragment lf = (TrainingProgramListFragment) fm.findFragmentByTag("List");
        if(lf == null)
        {
            lf = new TrainingProgramListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("programs", programs);
            lf.setArguments(arguments);
            Log.v("myApp", "List Activity: Create a new List Fragment " + lf);
            fm.beginTransaction().replace(R.id.trainingprogram_list, lf, "List").commit();
            //fm.beginTransaction().replace(R.id.titleTextView, lf, "List").commit();
        }



        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent2 = getIntent();
            Bundle extras = intent2.getExtras();
            String userName = extras.getString("username");
            String access_token = extras.getString("accessToken");
            Intent intent = new Intent(this, SearchProgram.class);
            intent.putExtras(extras);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link TrainingProgramListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(SportProgram p) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
//            arguments.putString(GolfcourseDetailFragment.ARG_ITEM_ID, id);
            arguments.putParcelable("program", p);
            TrainingProgramDetailFragment fragment = new TrainingProgramDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.trainingprogram_detail_container, fragment, "Detail")
                    .commit();

        }
            /*
            Bundle arguments = new Bundle();
            arguments.putString("program", getIntent().getExtras().getString("program"));
            //arguments.putString(TrainingProgramDetailFragment.ARG_ITEM_ID, id);
            TrainingProgramDetailFragment fragment = new TrainingProgramDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.trainingprogram_detail_container, fragment)
                    .commit();
*/
         else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent intent2 = getIntent();
            Bundle bundle = intent2.getExtras();
            String userName = bundle.getString("username");
            String accessToken = bundle.getString("accessToken");
            bundle.putParcelable("program", p);
            /*
            Bundle extras = new Bundle();
            extras.putParcelable("program", p);
            extras.putString("email", userEmail);
            */
            System.out.println("TrainingProgramListActivity Test Username: " + userName);
            System.out.println("TrainingProgramListActivity Test Access_Token: " + accessToken);
            Intent detailIntent = new Intent(this, TrainingProgramDetailActivity.class);
           // detailIntent.putExtra(TrainingProgramDetailFragment.ARG_ITEM_ID, id);
            //detailIntent.putExtra("program", p);
            //detailIntent.putExtra("email", userEmail);
            //detailIntent.putExtras(extras);
            detailIntent.putExtras(bundle);
            Log.v("pr", "program details check. Title: " + p.Title );//+ " content : " + p.program);
            startActivity(detailIntent);
        }
    }
}
