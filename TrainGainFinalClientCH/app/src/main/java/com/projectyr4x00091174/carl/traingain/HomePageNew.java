package com.projectyr4x00091174.carl.traingain;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by carl on 10/04/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomePageNew extends Fragment implements View.OnClickListener, OnShowHelpListener{

    RESTurl rest = new RESTurl();
    Bundle extras;
    String userName;
    String access_token;
    ArrayList<SportProgram> programs = new ArrayList<SportProgram>();
    SportProgram programN;
    private List exampleListItemList;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    ImageView news1, news2, news3, news4, news5;
    Button myMap;
    boolean userShow;
    private final GestureDetector detector = new GestureDetector((GestureDetector.OnGestureListener) new SwipeGestureDetector());
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;

    public HomePageNew() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
        extras = getActivity().getIntent().getExtras();
        userName = extras.getString("username");
        access_token = extras.getString("accessToken");
        userShow = extras.getBoolean("userShow");
        System.out.println("HOMEFRAG TEST USERNAME: " + userName);
        System.out.println("HOMEFRAG TEST ACCESS_TOKEN: " + access_token);
        System.out.println("HOMEFRAG TEST USERSHOW: " + userShow);
        Button prof = (Button) rootView.findViewById(R.id.profileBtn);
        Button findPrograms = (Button) rootView.findViewById(R.id.programFinderBtn);
        Button calcB = (Button) rootView.findViewById(R.id.calcBtn);
        Button graphB = (Button) rootView.findViewById(R.id.graphBtn);
        Button settingsB = (Button) rootView.findViewById(R.id.settingsBtn);
        myMap = (Button) rootView.findViewById(R.id.mapH);
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper_home);
        news1 = (ImageView) rootView.findViewById(R.id.home1);
        news2 = (ImageView) rootView.findViewById(R.id.home2);
        news3 = (ImageView) rootView.findViewById(R.id.home3);
        news4 = (ImageView) rootView.findViewById(R.id.home4);
        prof.setOnClickListener(this);
        findPrograms.setOnClickListener(this);
        calcB.setOnClickListener(this);
        graphB.setOnClickListener(this);
        settingsB.setOnClickListener(this);
        myMap.setOnClickListener(this);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
        return rootView;
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.profileBtn:
                extras = getArguments();
                userName = extras.getString("username");
                System.out.println("HOME NEW FINAL TEST PROFILE: " + userName);
                new HttpAsyncTask().execute(rest.getREST_GET_USER_PROGRAMS() + userName);
                break;
            case R.id.programFinderBtn:
                fragment = new SearchProgram();
            break;
            case R.id.graphBtn:
                extras = getArguments();
                userName = extras.getString("username");
                access_token = extras.getString("accessToken");
                System.out.println("HOME NEW FINAL TEST GRAPH: " + userName);
                new HttpAsyncTask2().execute(rest.getREST_GET_USER_PROGRAMS() + userName);
                break;
            case R.id.calcBtn:
                fragment = new CalcHealthMetrics();
                break;
            case R.id.settingsBtn:
                fragment = new EditProfile();
                break;
            case R.id.mapH:
                Intent mapIntent = new Intent(getActivity(), MapsActivity.class);
                startActivity(mapIntent);
        }
        if (fragment != null) {
            extras.putBoolean("userShow", userShow);
            fragment.setArguments(extras);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<SportProgram>> {
        MyProgressDialog dialog;
        Context context;
        protected void onPreExecute() {
            dialog = new MyProgressDialog(getActivity(), R.drawable.progress_dialog_img);
            dialog.setTitle("Warming Up...");
            dialog.show();
            super.onPreExecute();
        }
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
                            System.out.println("NEW SEARCH EX NAME: " + Name);
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
                            stepsList = new ArrayList<Step>();
                        }
                        programN = new SportProgram(id, sport, goal, title, programDetail, intensity, hrsPerDay, sessionsPerDay, genre, preInfo, durationInfo, Exercises);
                        programs.add(programN);
                        //Exercises = new ArrayList<Exercise>();
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
                }
            }
            return programs;
        }

        protected void onPostExecute(ArrayList<SportProgram> programList) {

            for(int i = 0; i < programList.size(); i++) {
                for (int j = 0; j < programList.get(i).getExercises().size(); j++) {
                    System.out.println("search !! Image: " + programList.get(i).getExercises().get(j).getName());
                    for (int k = 0; k < programList.get(i).getExercises().get(j).StepL.size(); k++) {
                        System.out.println("search !! STEP: " + programList.get(i).getExercises().get(j).StepL.get(k).StepDes);
                    }
                }
            }
            Bundle extras = getActivity().getIntent().getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putString("username", userName);
            extras.putString("accessToken", access_Token);
            extras.putBoolean("userShow", userShow);
            extras.putParcelableArrayList("programs", programList);
            Fragment fragment = new PersonalProgramListFragment();
            fragment.setArguments(extras);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            dialog.dismiss();
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
                System.out.println("HOME Hit if: " + result);
            } else {
                System.out.println("HOME Hit else: " + result);
                userShow = false;
            }
            return userShow;
        }

        protected void onPostExecute(boolean userShow) {
            super.onPostExecute(userShow);
            System.out.println("USERSHOW HOME: " + userShow);
            listener.OnShowHelpListener(userShow);
        }
    }


    private class HttpAsyncTask2 extends AsyncTask<String, Void, String>
    {
        MyProgressDialog dialog;
        Context context;
        protected void onPreExecute() {
            dialog = new MyProgressDialog(getActivity(), R.drawable.progress_dialog_img);
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
                        //contactList.add(program);
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }
            Bundle extras = getActivity().getIntent().getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            removeDuplicates(programs);
            extras.putParcelableArrayList("programs", programs);
            extras.putBoolean("userShow", userShow);
            Fragment fragment = new GraphListFragment();
            fragment.setArguments(extras);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            dialog.dismiss();
            }
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
}
