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


/**
 * An activity representing a single PersonalProgram detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PersonalProgramListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link PersonalProgramDetailFragment}.
 */
public class PersonalProgramDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalprogram_detail);

        // Show the Up button in the action bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
            boolean show = bundle.getBoolean("userShow");
            System.out.println("PersonalProgramDetailActivity Test Username: " + userName);
            System.out.println("PrsonalProgramDetailActivity Test access_token: " + accessToken);
            System.out.println("PrsonalProgramDetailActivity Test userShow: " + show);
            /*
            Bundle arguments = new Bundle();
            arguments.putParcelable("program", getIntent().getParcelableExtra("program"));
            arguments.putString("email", userEmail);
            */
            PersonalProgramDetailFragment fragment = new PersonalProgramDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.personalprogram_detail_container, fragment)
                    .commit();
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
            fragmentManager.beginTransaction().replace(R.id.personalprogram_detail_container, fragment).commit();

            //   fragment.getActivity().supportInvalidateOptionsMenu();

        }
        return super.onOptionsItemSelected(item);
    }
}