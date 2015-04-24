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
 * An activity representing a list of Graphs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GraphDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link GraphListFragment} and the item details
 * (if present) is a {@link GraphDetailFragment}.
 * <p/>
 * This activity also implements the required
/ * {@link GraphListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class GraphListActivity extends ActionBarActivity
        implements GraphListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    String userName;
    String access_token;
    private String[] mNavItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_list);
        mNavItems = getResources().getStringArray(R.array.nav_slider_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.supportInvalidateOptionsMenu();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            getActionBar().setHomeButtonEnabled(true);
        }

        userName = this.getIntent().getStringExtra("username");
        access_token = this.getIntent().getStringExtra("accessToken");
        ArrayList<SportProgram> programs = this.getIntent().getParcelableArrayListExtra("programs");
        //Bundle extras = getIntent().getExtras();
        //ArrayList<TrainingProgram> programs = extras.getParcelableArrayList("programs");

        FragmentManager fm = getSupportFragmentManager();
        if (findViewById(R.id.graph_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            GraphDetailFragment pf = (GraphDetailFragment) fm.findFragmentByTag("Detail");
            if (pf == null) {
                Log.v("myApp", "List Activity: Initialize new detail view");
                pf = new GraphDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("program", new SportProgram("Welcome to Golf Droid", "program"));//, "Details"));
                pf.setArguments(args);
                fm.beginTransaction().replace(R.id.graph_detail_container, pf, "Detail").commit();
            }
        }
        GraphListFragment lf = (GraphListFragment) fm.findFragmentByTag("List");
        if (lf == null) {
            lf = new GraphListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("programs", programs);
            arguments.putString("username", userName);
            arguments.putString("accessToken", access_token);
            lf.setArguments(arguments);
            Log.v("myApp", "List Activity: Create a new List Fragment " + lf);
            fm.beginTransaction().replace(R.id.graph_list, lf, "List").addToBackStack("graphStack").commit();
            //fm.beginTransaction().replace(R.id.titleTextView, lf, "List").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeIntent = new Intent(this, NavigationDrawer.class);
                Bundle extras = this.getIntent().getExtras();
                homeIntent.putExtras(extras);
                startActivity(homeIntent);
                break;
            case R.id.action_search:
                 fragment = new SearchProgram();
                break;
        }
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", userName);
            bundle.putString("access_token", access_token);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.graph_list, fragment).addToBackStack("").commit();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Callback method from {@link PersonalProgramListFragment.Callbacks}
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
            GraphDetailFragment fragment = new GraphDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.graph_detail_container, fragment, "Detail")
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent intent2 = getIntent();
            Bundle bundle = intent2.getExtras();
            String userName = bundle.getString("username");
            String accessToken = bundle.getString("accessToken");
            double benchVal = bundle.getDouble("liftValue");
            bundle.putParcelable("program", p);
            /*
            Bundle extras = new Bundle();
            extras.putParcelable("program", p);
            extras.putString("email", userEmail);
            */
            System.out.println("GraphListActivity Test Username: " + userName);
            System.out.println("GraphListActivity Test Access_Token: " + accessToken);
            Intent detailIntent = new Intent(this, GraphDetailActivity.class);
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

