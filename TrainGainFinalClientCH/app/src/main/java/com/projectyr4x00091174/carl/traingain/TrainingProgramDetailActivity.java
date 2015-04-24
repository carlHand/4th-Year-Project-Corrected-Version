package com.projectyr4x00091174.carl.traingain;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.socialize.ActionBarUtils;
import com.socialize.Socialize;
import com.socialize.entity.Entity;


/**
 * An activity representing a single Training Program detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TrainingProgramListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link TrainingProgramDetailFragment}.
 */
public class TrainingProgramDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingprogram_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setHomeButtonEnabled(true);
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if(getSupportFragmentManager().getBackStackEntryCount() == 0)
                {
                    finish();
                }
            }
        });
        // Show the Up button in the action bar.
        ///// getActionBar().setDisplayHomeAsUpEnabled(true);

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
            boolean show  = bundle.getBoolean("userShow");
            System.out.println("TrainingProgramDetailActivity Test Username: " + userName);
            System.out.println("TrainingProgramDetailActivity Test access_token: " + accessToken);
            /*
            Bundle arguments = new Bundle();
            arguments.putParcelable("program", getIntent().getParcelableExtra("program"));
            arguments.putString("email", userEmail);
            */
            TrainingProgramDetailFragment fragment = new TrainingProgramDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.trainingprogram_detail_container, fragment)
                    .commit();
        }
/*
        // Call Socialize in onCreate
        Socialize.onCreate(this, savedInstanceState);

        // Your entity key. May be passed as a Bundle parameter to your activity
        String entityKey = "http://www.getsocialize.com";

        // Create an entity object including a name
        // The Entity object is Serializable, so you could also store the whole object in the Intent
        Entity entity = Entity.newInstance(entityKey, "Socialize");

        View actionBarWrapped = ActionBarUtils.showActionBar(this, R.layout.activity_trainingprogram_detail, entity);

        // Now set the view for your activity to be the wrapped view.
        //setContentView(actionBarWrapped);
  */
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
/*
                Intent back = new Intent(this, TrainingProgramListActivity.class);
                Bundle extras = this.getIntent().getExtras();
                back.putExtras(extras);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, TrainingProgramListActivity.class);
*/

                //getSupportFragmentManager().popBackStack("detailStack", 0);
                this.onBackPressed();
                return true;
            case R.id.action_home:
                Intent homeIntent = new Intent(this, NavigationDrawer.class);
                Bundle extras2 = this.getIntent().getExtras();
                homeIntent.putExtras(extras2);
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
            fragmentManager.beginTransaction().replace(R.id.trainingprogram_detail_container, fragment).commit();

            //   fragment.getActivity().supportInvalidateOptionsMenu();

        }
        return super.onOptionsItemSelected(item);
    }
}