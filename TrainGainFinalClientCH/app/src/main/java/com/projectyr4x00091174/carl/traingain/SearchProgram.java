package com.projectyr4x00091174.carl.traingain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

//import com.google.android.gms.internal.js;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SearchProgram extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button search;
    String Sport = "";
    String Goal = "";
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    Spinner dropdown;
    Spinner dropdown2;
    private List exampleListItemList;
    ArrayList<String> sports = new ArrayList<String>();
    ArrayList<String> goals = new ArrayList<String>();
    SportProgram programN;
    String[][] resultGoals;
    String[][] goalsTemp;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<SportProgram> programs = new ArrayList<SportProgram>();
    String userName;
    String access_token;
    RESTurl rest = new RESTurl();
    boolean show;

    public SearchProgram()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().supportInvalidateOptionsMenu();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_training_program, container, false);
        setHasOptionsMenu(true);
        dropdown = (Spinner) rootView.findViewById(R.id.spinner1);
        dropdown2 = (Spinner) rootView.findViewById(R.id.spinner2);
        search = (Button) rootView.findViewById(R.id.search);
        userName = getArguments().getString("username");
        access_token = getArguments().getString("accessToken");
        show = getArguments().getBoolean("userShow");
        System.out.println("SEARCHFRAG TEST USERNAME: " + userName);
        System.out.println("SEARCHFRAG TEST ACCESS_TOKEN: " + access_token);
        dropdown.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        dropdown2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        search.setOnClickListener(this);
        dropdown2.setEnabled(false);
        return rootView;
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        switch (arg0.getId()) {
            case R.id.spinner1:
                dropdown2.setEnabled(true);
                ArrayList<String> dropdown2Results = new ArrayList<String>();
                for (int i = 0; i < resultGoals.length; i++) {
                    String selectedValue = arg0.getSelectedItem().toString();
                    if (dropdown.getSelectedItem().equals(resultGoals[i][0])) {
                        dropdown2Results.add(resultGoals[i][1]);
                        Sport = dropdown.getSelectedItem().toString();
                        System.out.println("Sport!!!!!!!: " + Sport);
                    } else {
                        System.out.println("NOT EQUAL TO ANYTHING");
                    }
                }
                ArrayAdapter<String> goalAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dropdown2Results);
                dropdown2.setAdapter(goalAdapter);
                break;
            case R.id.spinner2:
                Goal = dropdown2.getSelectedItem().toString();
                Log.e("Goal", Goal);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().supportInvalidateOptionsMenu();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                new HttpAsyncTaskPrograms().execute(rest.getREST_LOAD_PROGRAMS());
            }
        };
        mHandler.postDelayed(mTimer1, 100);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
    }

    public String getTrainingPrograms(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                Log.d("OK", "So far so GOOD!!!");
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("getTrainingPrograms", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    public void onClick(View v) {
        System.out.println(Sport);
        System.out.println(Goal);
        switch ((v.getId())) {
            case R.id.search:
                String u = rest.getREST_GET_PROGRAMS() + Sport + "/" + Goal;
                URI uri = null;
                try {
                    uri = new URI(u.replaceAll(" ", "%20"));
                    System.out.println("TITLE!!!!!!!!: " + uri.toString());
                    new HttpAsyncTask().execute(uri.toString());
                    break;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String POST(String url, String sport, String goal) {
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //make POST request to this url
            HttpPost httpPost = new HttpPost(url);
            //HttpGet httpGet = new HttpGet(url);
            String json = "";
            //build json object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Sport:", sport);
            jsonObject.put("Goal:", goal);
            //convert json object to JSON to String
            json = jsonObject.toString();
            //set json to StringEntity
            StringEntity se = new StringEntity(json);
            //set HttpPost entity
            httpPost.setEntity(se);
            //set some headers to inform server about the type of content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            //execute POST request to the specified URL
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String onLoadPrograms(String URL) {
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
            Log.d("Exception", e.getLocalizedMessage());
        }
        return result;
    }

    public static String[][] removeDuplicateRow(String[][] testArr) {
        /**
         * This method will remove the duplicate ROW from 2-D array and replace with {0,0,0}
         * However i assume array rows  =5 and coloums =3 ;) You can have the solution for that I assume
         */

        HashSet<String> hashSet = new HashSet<String>();
        int size = testArr.length;
        String[][] result = new String[size][2];
        int i = 0;
        for (String[] a : testArr) {
            System.out.println(Arrays.toString(a));
            System.out.println(hashSet.contains(a));
            if (!hashSet.contains(Arrays.toString(a))) {
                hashSet.add(Arrays.toString(a));
                result[i] = a;
            }
            i++;
        }

        System.out.println("old array : " + Arrays.deepToString(testArr));
        System.out.println("new array : " + Arrays.deepToString(result));

        return result;
    }


    private class HttpAsyncTaskPrograms extends AsyncTask<String, Void, String> {
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
            return getTrainingPrograms(urls[0]);
        }

        protected void onPostExecute(String result) {
            System.out.println("RESULT FOR SEARCH PROGRAMS API: " + result);
            try {
                JSONArray jsonObj = new JSONArray(result);
                int size = jsonObj.length();
                for (int i = 0; i < jsonObj.length(); i++) {
                    JSONObject c = jsonObj.getJSONObject(i);
                    String s = c.getString("Sport");
                    String g = c.getString("Goal");
                    System.out.println("jsonObj.length: " + jsonObj.length());
                    System.out.println("SSSSSSS: " + s);
                    System.out.println("ggggggg: " + g);
                    sports.add(s);
                    goals.add(g);

                }
                goalsTemp = new String[goals.size()][2];
                for (int j = 0; j < goals.size(); j++) {
                    goalsTemp[j][0] = sports.get(j);
                    goalsTemp[j][1] = goals.get(j);
                    System.out.println("SPORTS!!!!!!!JJJJJ: " + sports.get(j));
                    System.out.println("GOALS!!!!!!!JJJJJ: " + goals.get(j));
                }
                resultGoals = removeDuplicateRow(goalsTemp);
                for (String[] res : resultGoals) {
                    System.out.println(Arrays.toString(res));
                }
                ArrayList<String> unique = removeDuplicates(sports);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, unique);
                dropdown.setAdapter(adapter);
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        // Store unique items in result.
        ArrayList<String> result = new ArrayList();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
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
            String result =  onLoadPrograms(urls[0]);
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
            Bundle extras = new Bundle();
            String userName = getArguments().getString("username");
            String access_Token = getArguments().getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putBoolean("userShow", show);
            extras.putString("username", userName);
            extras.putString("accessToken", access_Token);
            extras.putParcelableArrayList("programs", programList);
            Intent intent = new Intent(getActivity(), TrainingProgramListActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
            dialog.dismiss();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
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
}
