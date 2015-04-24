package com.projectyr4x00091174.carl.traingain;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;


/**
 * An activity representing a list of PersonalPrograms. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PersonalProgramDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PersonalProgramListFragment} and the item details
 * (if present) is a {@link PersonalProgramDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link PersonalProgramListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class PersonalProgramListActivity extends ActionBarActivity
{//implements PersonalProgramListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalprogram_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            getActionBar().setHomeButtonEnabled(true);
        }
        ArrayList<SportProgram> programs = this.getIntent().getParcelableArrayListExtra("programs");
        if(programs == null)
        {
            System.out.println("Hit null in LA");
        }
        else
        {
            System.out.println("Hit else in LA");
        }
        //Bundle extras = getIntent().getExtras();
        //ArrayList<TrainingProgram> programs = extras.getParcelableArrayList("programs");

        FragmentManager fm = getSupportFragmentManager();
        if (findViewById(R.id.personalprogram_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            PersonalProgramDetailFragment pf = (PersonalProgramDetailFragment) fm.findFragmentByTag("Detail");
            if (pf == null) {
                Log.v("myApp", "List Activity: Initialize new detail view");
                pf = new PersonalProgramDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("program", new SportProgram("Welcome to Golf Droid", "program"));//, "Details"));
                pf.setArguments(args);
                fm.beginTransaction().replace(R.id.personalprogram_detail_container, pf, "Detail").commit();
            }
            // TODO: If exposing deep links into your app, handle intents here.
        }
        PersonalProgramListFragment lf = (PersonalProgramListFragment) fm.findFragmentByTag("List");
        if (lf == null) {
            lf = new PersonalProgramListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("programs", programs);
            lf.setArguments(arguments);
            Log.v("myApp", "List Activity: Create a new List Fragment " + lf);
            fm.beginTransaction().replace(R.id.personalprogram_list, lf, "List").addToBackStack("listPStack").commit();
            //fm.beginTransaction().replace(R.id.titleTextView, lf, "List").commit();
        }
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
            fragmentManager.beginTransaction().replace(R.id.personalprogram_list, fragment).commit();

            //   fragment.getActivity().supportInvalidateOptionsMenu();

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link PersonalProgramListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    /*
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
                    .replace(R.id.personalprogram_detail_container, fragment, "Detail")
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent intent2 = getIntent();
            Bundle bundle = intent2.getExtras();
            String userName = bundle.getString("username");
            String accessToken = bundle.getString("accessToken");
            bundle.putParcelable("program", p);
            System.out.println("TrainingProgramListActivity Test Username: " + userName);
            System.out.println("TrainingProgramListActivity Test Access_Token: " + accessToken);
            Intent detailIntent = new Intent(this, PersonalProgramDetailActivity.class);
            detailIntent.putExtras(bundle);
            Log.v("pr", "program details check. Title: " + p.getTitle() );//+ " content : " + p.program);
            startActivity(detailIntent);
        }
    }
    */
}
