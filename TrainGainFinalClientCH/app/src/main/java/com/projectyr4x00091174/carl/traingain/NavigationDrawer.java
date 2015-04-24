package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.socialize.Socialize;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class NavigationDrawer extends ActionBarActivity implements OnShowHelpListener{

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavItems;
    Bundle extras;
    String userName;
    String access_token;
    ArrayList<SportProgram> programs = new ArrayList<SportProgram>();
    SportProgram programN;
    private List exampleListItemList;
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    boolean userShow;
    RESTurl rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        rest = new RESTurl();
        Intent intent2 = getIntent();
        extras = intent2.getExtras();
        userName = extras.getString("username");
        access_token = extras.getString("accessToken");
        userShow = extras.getBoolean("userShow");
        System.out.println("NAVFRAG TEST USERNAME: " + userName);
        System.out.println("NAVFRAG TEST ACCESS_TOKEN: " + access_token);
        mTitle = mDrawerTitle = "Train & Gain";
        mNavItems = getResources().getStringArray(R.array.nav_slider_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_nav_drawer);

        // set up the drawer's list view with items using a custom adapter and click listener
        NavDrawerAdapter adapter = new NavDrawerAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // enable ActionBar App icon to behave as action to toggle navigation drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



		/*
		 * ActionBarDrawerToggle ties together the the proper interactions
		 * between the sliding drawer and the action bar app icon
		 */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            /*
             * called when drawer is closed
             */
            public void onDrawerClosed(View view) {
                // change action bar title
                getSupportActionBar().setTitle(mTitle);
                // re-draws action menu items
                supportInvalidateOptionsMenu();
            }

            /*
             * called when drawer is opened
             */
            public void onDrawerOpened(View drawerView) {
                // change action bar title
                getSupportActionBar().setTitle(mDrawerTitle);
                // re-draws action menu items
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final  HttpAsyncTaskUserHelp httpAsyncTask = new HttpAsyncTaskUserHelp();
        httpAsyncTask.setOnShowHelpListener(this);
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                httpAsyncTask.execute(rest.getREST_GET_USER_HELP_OPTION() + userName);
            }
        };
        mHandler.postDelayed(mTimer1, 100);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
    }
    @Override
    public boolean OnShowHelpListener(boolean show) {
        return show;
    }

    private class HttpAsyncTaskUserHelp extends AsyncTask<String, Void, Boolean> {
        private OnShowHelpListener listener;

        public void setOnShowHelpListener(OnShowHelpListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            String trueString = "true";
            String result = getUserPrograms(urls[0]);
            if (result.toUpperCase().equals(trueString.toUpperCase())) {
                userShow = true;
                System.out.println("Hit if: " + result);
            } else {
                System.out.println("Hit else: " + result);
                userShow = false;
            }
            return userShow;
        }

        protected void onPostExecute(boolean userShow) {
            super.onPostExecute(userShow);
            System.out.println("USERSHOW NAV: " + userShow);
            listener.OnShowHelpListener(userShow);
        }
    }

    public void enableNavDrawer(boolean enabled)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        getSupportActionBar().setHomeButtonEnabled(enabled);
        if(enabled) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        else{
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            // handle home button click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        Intent intent  = new Intent();
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomePageNew();
                break;
            case 1:
                fragment = new SearchProgram();
                break;
            case 2:
                //Graph
                extras = this.getIntent().getExtras();
                access_token = extras.getString("accessToken");
                userName = extras.getString("username");
                System.out.println("NAV NEW FINAL TEST GRAPH: " + userName);
                new HttpAsyncTask2().execute(rest.getREST_GET_USER_PROGRAMS() + userName);
                break;
            case 3:
                //Profile
                extras = this.getIntent().getExtras();
                userName = extras.getString("username");
                new HttpAsyncTask().execute(rest.getREST_GET_USER_PROGRAMS() + userName);
                break;
            case 4:
                fragment = new CalcHealthMetrics();
                break;
            case 5:
                fragment = new EditProfile();
            default:
                break;
        }
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(HomeFragment.EXTRA_ITEM_INDEX, position);
            bundle.putString("username", userName);
            bundle.putString("access_token", access_token);
            bundle.putBoolean("userShow", userShow);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavItems[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        /*
        // create and set fragment arguments
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(HomeFragment.EXTRA_ITEM_INDEX, position);
        fragment.setArguments(bundle);
        // set the fragment to be the current view
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
*/
    }

    public String getUserPrograms(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        String result = "";
        try {
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                Log.d("OK", "200 Ok");
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    result = stringBuilder.toString();
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Could not download file");
            }
        } catch (Exception e) {
            Log.d("getUserPrograms", e.getLocalizedMessage());
        }
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<SportProgram>>
    {
        @Override
        protected ArrayList<SportProgram> doInBackground(String... urls) {
            String result =  getUserPrograms(urls[0]);
            String Name = "";
            String LiftValueTemp = "";
            String IDexerciseTemp = "";
            String step = "";
            String IDstepTemp = "";
            int IDstep = 0;
            List<Step> stepsList = new ArrayList<Step>();
            int IDexercise = 0;
            double LiftValue = 0.0;
            Exercise e1 = new Exercise();
            Step s1 = new Step();
            List<Exercise> Exercises = new ArrayList<Exercise>();
            List<ExerciseImage> exerciseImgList = new ArrayList<ExerciseImage>();
            ExerciseImage eImage = new ExerciseImage();
            if(result != null) {
                System.out.println("Result: " + result);
//                Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
                exampleListItemList = new ArrayList();
                programs.clear();
                System.out.println("CLEAR TEST");
                Date date = new Date();
                try {

                    JSONArray jsonObj = new JSONArray(result);
                    for(int i = 0; i < jsonObj.length(); i++)
                    {
                        if(!Exercises.isEmpty())
                        {
                            System.out.println("HIT CLEAR");
                            //Exercises.clear();
                        }
                        JSONObject c = jsonObj.getJSONObject(i);
                        String idTemp = c.getString("IDsport");
                        System.out.println("SEARCHHHHHHHHHHHHHHHHHHHHH!!!");
                        System.out.println("BEFORE");
                        int id = Integer.parseInt(idTemp);
                        System.out.println("ID: " + id);
                        String sport = c.getString("Sport");
                        System.out.println("SPORT: " + sport);
                        String goal = c.getString("Goal");
                        System.out.println("GOAL: " + goal);
                        String title = c.getString("Title");
                        System.out.println("TITLE: " + title);
                        String programDetail = c.getString("Program");
                        System.out.println("programDetail: " + programDetail);
                        String intensity = c.getString("Intensity");
                        System.out.println("Intensity: " + intensity);
                        String hrsPerDayTemp = c.getString("HrsPerDay");
                        double hrsPerDay = Double.parseDouble(hrsPerDayTemp);
                        System.out.println("hrsPerDay: " + hrsPerDay);
                        String sessionsPerDayTemp = c.getString("SessionsPerDay");
                        int sessionsPerDay = Integer.parseInt(sessionsPerDayTemp);
                        System.out.println("sessionperday: " + sessionsPerDay);
                        String genre = c.getString("Genre");
                        System.out.println("GENRE: " + genre);
                        String preInfo = c.getString("PreTrainingNotes");
                        System.out.println("PreTrainingNotes: " + preInfo);
                        String durationInfo = c.getString("DurationInfo");
                        System.out.println("ID: " + durationInfo);
                        JSONArray jArray = c.getJSONArray("Exercises");
                        JSONArray imgJSON = null;
                        for(int j = 0; j < jArray.length(); j++) {
                            IDexerciseTemp = jArray.getJSONObject(j).getString("IDexercise");
                            Name = jArray.getJSONObject(j).getString("Name");
                            LiftValueTemp = jArray.getJSONObject(j).getString("LiftValue");
                            String dateTemp = jArray.getJSONObject(j).getString("DateLogged");

                            JSONArray stepsArray = jArray.getJSONObject(j).getJSONArray("StepL");
                            for(int z = 0; z < stepsArray.length(); z++)
                            {
                                step = stepsArray.getJSONObject(z).getString("StepDes");
                                IDstepTemp = stepsArray.getJSONObject(z).getString("IDstep");
                                System.out.println("STEPDESC: " + step);
                                s1 = new Step();
                                IDstep = Integer.parseInt(IDstepTemp);
                                s1.IDstep = IDstep;
                                s1.StepDes = step;
                                stepsList.add(s1);
                            }

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            //String dd = "2015-03-30T14:10:38.47";
                            // format.setLenient(false);
                            //format.setTimeZone(TimeZone.getDefault());
                            try{
                                date = format.parse(dateTemp);
                            }
                            catch(ParseException p)
                            {
                                p.printStackTrace();
                            }
                            IDexercise = Integer.parseInt(IDexerciseTemp);
                            LiftValue = Double.parseDouble(LiftValueTemp);
                            e1 = new Exercise();
                            e1.setName(Name);
                            e1.setLiftValue(LiftValue);
                            e1.setDateLogged(date);
                            e1.StepL = stepsList;

                            e1.ExerciseImageL = exerciseImgList;
                            Exercises.add(e1);
                            System.out.println("IIDEXERCISE: " + Exercises.get(j).getIDexercise());
                            System.out.println("NAME: " + Exercises.get(j).getName());
                            System.out.println("LIFTVALUE: " + Exercises.get(j).getLiftValue());
                            System.out.println("DATELOGGEDDD: " + Exercises.get(j).getDateLogged());

                        }
                        programN = new SportProgram(id, sport, goal, title, programDetail, intensity, hrsPerDay, sessionsPerDay, genre, preInfo, durationInfo, Exercises);
                        programs.add(programN);
                        //Exercises = new ArrayList<Exercise>();
                        stepsList = new ArrayList<Step>();
                        exerciseImgList = new ArrayList<ExerciseImage>();
                        programN = new SportProgram();
                        System.out.println("ADDED");

                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }
            SportProgram e = new SportProgram();

            for(int i = 0; i < programs.size(); i++) {
                e = programs.get(i);
                System.out.println("AFTER");
                System.out.println("ID22: " + e.getIDsport() + "\nSport22: " + e.getSport() + "\nGoal22: " + e.getGoal()
                        + "\nTitle22: " + e.getTitle() + "\nProgram: " + e.getProgram() + "\nIntensity: " + e.getIntensity()
                        + "\nHrsPerDay: " + e.getHrsPerDay() + "\nSessionsPerDay: " + e.getSessionsPerDay() + "\n");

                for (int j = 0; j < programs.get(i).getExercises().size(); j++) {
                    System.out.println("NAME22: " + e.getExercises().get(j).getName());
                    System.out.println("LIFT22: " + e.getExercises().get(j).getLiftValue());
                    /*
                    for(int k = 0; k < programs.get(i).getExercises().get(j).Steps.size(); k++)
                    {
                        System.out.println("STEPS TEST: " + e.getExercises().get(k).Steps.get(k));
                    }
                    */
                }
            }
            return programs;
        }
        protected void onPostExecute(ArrayList<SportProgram> programList)
        {
            Bundle extras = getIntent().getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putString("username", userName);
            extras.putString("accessToken", access_Token);
            extras.putParcelableArrayList("programs", programList);
            extras.putBoolean("userShow", userShow);
            Fragment fragment = new PersonalProgramListFragment();
            fragment.setArguments(extras);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            mDrawerList.setItemChecked(3, true);
            mDrawerList.setSelection(3);
            setTitle(mNavItems[3]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String>
    {
        MyProgressDialog dialog;
        Context context;
        protected void onPreExecute() {
            dialog = new MyProgressDialog(NavigationDrawer.this, R.drawable.progress_dialog_img);
            dialog.setTitle("Warming Up...");
            dialog.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... urls) {
            return getUserPrograms(urls[0]);
        }
        protected void onPostExecute(String result)
        {
            String Name = "";
            String LiftValueTemp = "";
            String IDexerciseTemp = "";
            int IDexercise = 0;
            double LiftValue = 0.0;
            Date date = new Date();
            List<Exercise> Exercises = new ArrayList<Exercise>();
            if(result != null) {
                System.out.println("Result: " + result);
                // Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
                try {
                    programs.clear();

                    JSONArray jsonObj = new JSONArray(result);
                    for(int i = 0; i < jsonObj.length(); i++) {
                        JSONObject c = jsonObj.getJSONObject(i);
                        String idTemp = c.getString("IDsport");
                        int id = Integer.parseInt(idTemp);
                        String sport = c.getString("Sport");
                        String goal = c.getString("Goal");
                        String title = c.getString("Title");
                        String programDetail = c.getString("Program");
                        String intensity = c.getString("Intensity");
                        String hrsPerDayTemp = c.getString("HrsPerDay");
                        double hrsPerDay = Double.parseDouble(hrsPerDayTemp);
                        String sessionsPerDayTemp = c.getString("SessionsPerDay");
                        String genre = c.getString("Genre");
                        String preInfo = c.getString("PreTrainingNotes");
                        String durationInfo = c.getString("DurationInfo");
                        JSONArray jArray = c.getJSONArray("Exercises");
                        for(int j = 0; j < jArray.length(); j++) {
                            JSONObject jsonObject = jArray.getJSONObject(j);
                            /*
                            IDexerciseTemp = jArray.getJSONObject(j).getString("IDexercise");
                            Name = jArray.getJSONObject(j).getString("Name");
                            LiftValueTemp = jArray.getJSONObject(j).getString("LiftValue");
                            IDexercise = Integer.parseInt(IDexerciseTemp);
                            LiftValue = Double.parseDouble(LiftValueTemp);
                            */
                            IDexercise = jsonObject.getInt("IDexercise");
                            Name = jsonObject.getString("Name");
                            LiftValue = jsonObject.getDouble("LiftValue");
                            String dateTemp = jsonObject.getString("DateLogged");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            //String dd = "2015-03-30T14:10:38.47";
                            // format.setLenient(false);
                            //format.setTimeZone(TimeZone.getDefault());
                            try{
                                date = format.parse(dateTemp);
                            }
                            catch(ParseException p)
                            {
                                p.printStackTrace();
                            }
                            Exercise e = new Exercise();
                            e.setIDexercise(IDexercise);
                            e.setName(Name);
                            e.setLiftValue(LiftValue);
                            e.setDateLogged(date);

                            Exercises.add(e);
                            System.out.println("Profile IDExercise: " + IDexercise);
                            System.out.println("Profile Name: " + Name);
                            System.out.println("Profile LiftValue: " + LiftValue);
                        }

                        int sessionsPerDay = Integer.parseInt(sessionsPerDayTemp);
                        String blank = "";
                        System.out.println("Title detail: " + title);
                        System.out.println("Program detail: " + programDetail);
                        HashMap<String, String> program = new HashMap<String, String>();
                        program.put("id", idTemp);
                        program.put("title", title);
                        program.put("program", programDetail);

                        System.out.println("ID: " + id + "\nSport: " + sport + "\nGoal: " + goal
                                + "\nTitle: " + title + "\nProgram: " + program + "\nIntensity: " + intensity
                                + "\nHrsPerDay: " + hrsPerDay + "\nSessionsPerDay: " + sessionsPerDay + "\n");
                        //    p = new TrainingProgram.ProgramContent(title,programDetail);
                        // pp.addItem(new TrainingProgram.ProgramContent(p.title, p.program));

                        SportProgram programN = new SportProgram(id, sport, goal, title, programDetail, intensity, hrsPerDay, sessionsPerDay, genre, preInfo, durationInfo, Exercises);//,programDetail);
                        programs.add(programN);
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }

            Bundle extras = getIntent().getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            removeDuplicates(programs);
            extras.putParcelableArrayList("programs", programs);
            extras.putBoolean("userShow", userShow);
            Fragment fragment = new GraphListFragment();
            fragment.setArguments(extras);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            mDrawerList.setItemChecked(2, true);
            mDrawerList.setSelection(2);
            setTitle(mNavItems[2]);
            mDrawerLayout.closeDrawer(mDrawerList);
            dialog.dismiss();
        }
    }

    static ArrayList<SportProgram> removeDuplicates(ArrayList<SportProgram> list) {

        // Store unique items in result.
        ArrayList<SportProgram> result = new ArrayList();

        // Record encountered Strings in HashSet.
        HashSet<SportProgram> set = new HashSet();

        // Loop over argument list.
        for (SportProgram item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call
     * it during onPostCreate() and onConfigurationChanged()
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}