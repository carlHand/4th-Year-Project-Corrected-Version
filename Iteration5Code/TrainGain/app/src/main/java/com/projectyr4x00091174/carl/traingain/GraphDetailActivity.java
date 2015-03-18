package com.projectyr4x00091174.carl.traingain;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;


/**
 * An activity representing a single Graph detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GraphListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link GraphDetailFragment}.
 */
public class GraphDetailActivity extends Activity implements GraphDetailFragment.OnDataPass{


    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Intent intent2 = getIntent();
            Bundle bundle = intent2.getExtras();
            String userName = bundle.getString("username");
            String accessToken = bundle.getString("accessToken");
            double benchVal = bundle.getDouble("liftValue");

            System.out.println("GraphDetailActivity Test Username: " + userName);
            System.out.println("GraphDetailActivity Test access_token: " + accessToken);
            System.out.println("GraphDetailActivity Test LiftValue: " + benchVal);

            GraphDetailFragment fragment = new GraphDetailFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.graph_detail_container, fragment)
                    .commit();
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
            extras = intent2.getExtras();
            String userName = extras.getString("username");
            String access_token = extras.getString("accessToken");
            double benchVal = extras.getDouble("liftValue");
            double start = extras.getDouble("start");
            double end = extras.getDouble("end");
            System.out.println("GraphDetailActivity test, start: " + start + " end: " + end);
            Intent intent = new Intent(this, GraphListActivity.class);
            intent.putExtras(extras);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataPass(double data, double data2) {
        Log.d("LOG","start: " + data + " end: " + data2);
//        extras.putDouble("start", data);
 //       extras.putDouble("end", data2);
    }
}
