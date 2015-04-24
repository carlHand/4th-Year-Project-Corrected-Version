package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.google.android.gms.internal.js;

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
import java.util.TimeZone;


public class Profile extends Fragment implements View.OnClickListener {

    ImageButton userAccount;
    //ImageButton userProgress;
    Button userProgress;
    Button userPrograms;
    //ImageButton userPrograms;
    ArrayList<SportProgram> programs = new ArrayList<SportProgram>();
    SportProgram programN;
    private List exampleListItemList;
    String userName;
    String access_token;
    Bundle extras;
    public Profile()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getArguments();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        setHasOptionsMenu(true);
        userProgress = (Button) rootView.findViewById(R.id.graphBtn);
        userPrograms = (Button) rootView.findViewById(R.id.programBtn);
        userName = getArguments().getString("username");
        access_token = getArguments().getString("accessToken");
//        userAccount.setOnClickListener(this);
        userProgress.setOnClickListener(this);
        userPrograms.setOnClickListener(this);
        return rootView;
    }




    private class HttpAsyncTask2 extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
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
                        //contactList.add(program);
                    }
/*
                    TrainingProgram pp = new TrainingProgram();
                    JSONArray objArray = new JSONArray(result);
                    for(int i = 0; i < objArray.length(); i++) {
                        JSONObject objTitle = objArray.getJSONObject(i);
                        JSONObject objProgram = new JSONObject(result);
                        title = objTitle.getString("Title");
                        p = objProgram.getString("Program");
                        pp.addItem(new TrainingProgram.ProgramContent(title, p));
                        //exampleListItemList.add(new TrainingProgram.ProgramContent(title, p));
                        // mAdadpter = new TrainingProgramAdapter(getActivity(), exampleListItemList);
                        Log.d("OK", "So far so GOOD!!!");
                        Intent intent = new Intent(SearchProgram.this, TrainingProgramListActivity.class);
                        intent.putExtra("program", result);
                        startActivity(intent);
                    }
*/
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
            else {
                System.out.println("Error");
            }

            Bundle extras = new Bundle();
            String userName = getArguments().getString("username");
            String access_Token = getArguments().getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putString("username", userName);
            extras.putString("accessToken", access_Token);
/*
            Intent intent2 = getActivity().getIntent();
            Bundle extras = intent2.getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
  */
            removeDuplicates(programs);
            extras.putParcelableArrayList("programs", programs);
            //Bundle bundle = new Bundle();
            // bundle.putParcelableArrayList("programs", programs);
            Intent intent = new Intent(getActivity(), GraphListActivity.class);
            //intent.putParcelableArrayListExtra("programs", programs);
            //intent.putExtra("email", userEmail);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }





    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        String result = "";
        try {
            httpGet.setHeader("Accept", "application/json");
            // httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                Log.d("OK", "So far so GOOD!!!");
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
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<SportProgram>>
    {
        @Override
        protected ArrayList<SportProgram> doInBackground(String... urls) {
            String result =  readJSONFeed(urls[0]);
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
            Bundle extras = new Bundle();
            String userName = getArguments().getString("username");
            String access_Token = getArguments().getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putString("username", userName);
            extras.putString("accessToken", access_Token);
            extras.putParcelableArrayList("programs", programList);
            //Bundle bundle = new Bundle();
            // bundle.putParcelableArrayList("programs", programs);
            Intent intent = new Intent(getActivity(), PersonalProgramListActivity.class);
            //intent.putParcelableArrayListExtra("programs", programs);
            //intent.putExtra("email", userEmail);
            intent.putExtras(extras);
            intent.putParcelableArrayListExtra("programs", programs);
            startActivity(intent);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeIntent = new Intent(getActivity(), NavigationDrawer.class);
                Bundle extras = getActivity().getIntent().getExtras();
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
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("").commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
            case R.id.account:
                Intent intentAccount = new Intent();
                startActivity(intentAccount);
                break;
                */
            case R.id.graphBtn:
                /*
                Intent intent3 = getIntent();
                Bundle extras3 = intent3.getExtras();
                String userName3 = extras3.getString("username");
                double benchVal = extras3.getDouble("liftValue");
                new HttpAsyncTask2().execute("http://23.102.22.15/api/Account/userPrograms/" + userName3);
                */
                extras = getArguments();
                userName = extras.getString("username");
                new HttpAsyncTask2().execute("http://23.102.22.15/api/Account/userPrograms/" + userName);
                break;
            case R.id.programBtn:
                //Intent intent2 = getIntent();
                extras = getArguments();
                userName = extras.getString("username");
                new HttpAsyncTask().execute("http://23.102.22.15/api/Account/userPrograms/" + userName);
                break;
        }
    }
}
