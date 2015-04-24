package com.projectyr4x00091174.carl.traingain;

//import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingProgramListActivity extends ActionBarActivity
        implements TrainingProgramListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    Bundle bundle;
    String userName;
    String access_token;
    boolean show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
  //      setSupportProgressBarIndeterminate(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            getActionBar().setHomeButtonEnabled(true);
        }
/*
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminate(true);
  */
  //      Bundle b = getIntent().getExtras();
//        String result = b.getString("program");
       // ArrayList<TrainingProgram> programs =  (ArrayList<TrainingProgram>) getIntent().getSerializableExtra("programs");
        ArrayList<SportProgram> programs = this.getIntent().getParcelableArrayListExtra("programs");
        //Bundle extras = getIntent().getExtras();
        //ArrayList<TrainingProgram> programs = extras.getParcelableArrayList("programs");
        userName = this.getIntent().getStringExtra("username");
        access_token = this.getIntent().getStringExtra("accessToken");
        show = this.getIntent().getExtras().getBoolean("userShow");
    //getSupportFragmentManager().addOnBackStackChangedListener(this);
      //  shouldDisplayHomeUp();
        FragmentManager fm = getSupportFragmentManager();
//        setContentView(R.layout.activity_trainingprogram_list);
        setContentView(R.layout.fragment_expandable_list);
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
/*
            ((TrainingProgramListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.trainingprogram_list))
                    .setActivateOnItemClick(true);
*/
        }
        TrainingProgramListFragment lf = (TrainingProgramListFragment) fm.findFragmentByTag("List");
        if(lf == null)
        {
            lf = new TrainingProgramListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("programs", programs);
            arguments.putString("username", userName);
            arguments.putString("accessToken", access_token);
            arguments.putBoolean("userShow", show);
            lf.setArguments(arguments);
            Log.v("myApp", "List Activity: Create a new List Fragment " + lf);
            fm.beginTransaction().replace(R.id.containerE, lf, "List").addToBackStack("listStack").commit();
            //fm.beginTransaction().replace(R.id.titleTextView, lf, "List").commit();
        }
        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_program, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                break;
            case R.id.action_home:
                Intent homeIntent = new Intent(this, NavigationDrawer.class);
                Bundle extras = this.getIntent().getExtras();
                homeIntent.putExtras(extras);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                System.out.println("HIT HOMEINTENT");
                return true;
            case R.id.action_search:
                fragment = new SearchProgram();
                System.out.println("HIT SEARCH");
                break;
            default:
                break;
        }

        if (fragment != null) {
            Bundle bundle = this.getIntent().getExtras();//new Bundle();
          //  bundle.putString("username", userName);
           // bundle.putString("access_token", access_token);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerE, fragment).commit();

         //   fragment.getActivity().supportInvalidateOptionsMenu();

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
            getSupportFragmentManager().beginTransaction()
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
            Log.v("pr", "program details check. Title: " + p.getTitle() );//+ " content : " + p.program);
            startActivity(detailIntent);
        }
    }
}
