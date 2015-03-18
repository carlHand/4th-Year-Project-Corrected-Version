package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Profile extends Activity implements View.OnClickListener {

    ImageButton userAccount;
    //ImageButton userProgress;
    Button userProgress;
    Button userPrograms;
    //ImageButton userPrograms;
    ArrayList<SportProgram> programs = new ArrayList<SportProgram>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //userAccount = (ImageButton) findViewById(R.id.account);
        userProgress = (Button) findViewById(R.id.graphBtn);
        userPrograms = (Button) findViewById(R.id.programBtn);

//        userAccount.setOnClickListener(this);
        userProgress.setOnClickListener(this);
        userPrograms.setOnClickListener(this);
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
            List<Exercise> Exercises = new ArrayList<Exercise>();
            if(result != null) {
                System.out.println("Result: " + result);
                Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
                try {

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
                            Exercise e = new Exercise();
                            e.setIDexercise(IDexercise);
                            e.setName(Name);
                            e.setLiftValue(LiftValue);
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
                        SportProgram programN = new SportProgram(id, sport, goal, title, programDetail, intensity, hrsPerDay, sessionsPerDay, Exercises);//,programDetail);
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
            Intent intent2 = getIntent();
            Bundle extras = intent2.getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putParcelableArrayList("programs", programs);
            //Bundle bundle = new Bundle();
            // bundle.putParcelableArrayList("programs", programs);
            Intent intent = new Intent(Profile.this, GraphListActivity.class);
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

    private class HttpAsyncTask extends AsyncTask<String, Void, String>
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
            List<Exercise> Exercises = new ArrayList<Exercise>();
            if(result != null) {
                System.out.println("Result: " + result);
                Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
                try {

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
                            Exercise e = new Exercise();
                            e.setIDexercise(IDexercise);
                            e.setName(Name);
                            e.setLiftValue(LiftValue);
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
                        SportProgram programN = new SportProgram(id, sport, goal, title, programDetail, intensity, hrsPerDay, sessionsPerDay, Exercises);//,programDetail);
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
            Intent intent2 = getIntent();
            Bundle extras = intent2.getExtras();
            String userName = extras.getString("username");
            String access_Token = extras.getString("accessToken");
            System.out.println("Search Program Test Username: " + userName);
            System.out.println("Search Program Test Access_Token: " + access_Token);
            extras.putParcelableArrayList("programs", programs);
            //Bundle bundle = new Bundle();
            // bundle.putParcelableArrayList("programs", programs);
            Intent intent = new Intent(Profile.this, PersonalProgramListActivity.class);
            //intent.putParcelableArrayListExtra("programs", programs);
            //intent.putExtra("email", userEmail);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
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
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtras(extras);
            NavUtils.navigateUpTo(this, intent);
            return true;
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
                Intent intent3 = getIntent();
                Bundle extras3 = intent3.getExtras();
                String userName3 = extras3.getString("username");
                double benchVal = extras3.getDouble("liftValue");
                new HttpAsyncTask2().execute("http://23.102.22.15/api/Account/userPrograms/" + userName3);
                break;
            case R.id.programBtn:
                Intent intent2 = getIntent();
                Bundle extras = intent2.getExtras();
                String userName = extras.getString("username");
                new HttpAsyncTask().execute("http://23.102.22.15/api/Account/userPrograms/" + userName);
                break;
        }
    }
}
