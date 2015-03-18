package com.projectyr4x00091174.carl.traingain;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
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
 * {@link GraphListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class GraphListActivity extends Activity
        implements GraphListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_list);

        ArrayList<SportProgram> programs = this.getIntent().getParcelableArrayListExtra("programs");
        //Bundle extras = getIntent().getExtras();
        //ArrayList<TrainingProgram> programs = extras.getParcelableArrayList("programs");

        FragmentManager fm = getFragmentManager();
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
            ((PersonalProgramListFragment) getFragmentManager()
                    .findFragmentById(R.id.graph_list))
                    .setActivateOnItemClick(true);
            // TODO: If exposing deep links into your app, handle intents here.
        }
        GraphListFragment lf = (GraphListFragment) fm.findFragmentByTag("List");
        if (lf == null) {
            lf = new GraphListFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("programs", programs);
            lf.setArguments(arguments);
            Log.v("myApp", "List Activity: Create a new List Fragment " + lf);
            fm.beginTransaction().replace(R.id.graph_list, lf, "List").commit();
            //fm.beginTransaction().replace(R.id.titleTextView, lf, "List").commit();
        }
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
            double start = extras.getDouble("start");
            double end = extras.getDouble("end");
            Log.v("GraphListActivity new test", "start: " + start + " end: " + end);
            Intent intent = new Intent(this, Profile.class);
            intent.putExtras(extras);
            NavUtils.navigateUpTo(this, intent);
            return true;
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
            getFragmentManager().beginTransaction()
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
            Log.v("pr", "program details check. Title: " + p.Title );//+ " content : " + p.program);
            startActivity(detailIntent);
        }
    }
}

