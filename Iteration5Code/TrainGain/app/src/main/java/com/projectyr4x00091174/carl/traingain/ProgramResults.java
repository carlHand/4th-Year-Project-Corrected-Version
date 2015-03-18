package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProgramResults extends Activity implements View.OnClickListener {

    List<Exercise> eList;
    TextView benchTxt;
    TextView squatTxt;
    TextView deadliftTxt;
    TextView chinUpsTxt;
    TextView shoulderPressTxt;

    EditText benchEdit;
    EditText squatEdit;
    EditText deadliftEdit;
    EditText chinUpsEdit;
    EditText shoulderPressEdit;
    Button saveB;
    String username;
    SportProgram programN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_results);

        Intent intent2 = getIntent();
        Bundle extras = intent2.getExtras();
        username = extras.getString("username");
        String access_token = extras.getString("accessToken");
        programN = extras.getParcelable("program");

        eList = new ArrayList<Exercise>();
        benchTxt = (TextView) findViewById(R.id.benchLabel);
        squatTxt = (TextView) findViewById(R.id.squatLabel);
        deadliftTxt = (TextView) findViewById(R.id.deadliftLabel);
        chinUpsTxt = (TextView) findViewById(R.id.chinUpsLabel);
        shoulderPressTxt = (TextView) findViewById(R.id.shoulderPressLabel);
        benchEdit = (EditText) findViewById(R.id.benchValue);
        squatEdit = (EditText) findViewById(R.id.squatValue);
        deadliftEdit = (EditText) findViewById(R.id.deadliftValue);
        chinUpsEdit = (EditText) findViewById(R.id.chinUpsValue);
        shoulderPressEdit = (EditText) findViewById(R.id.shoulderPressValue);
        saveB = (Button) findViewById(R.id.save);
        saveB.setOnClickListener(this);
    }


    public String saveProgram(String url, String username, List<Exercise> elist) {
        // Log.d("tag", "SaveProgram Test Program: " + programN.Program + " Title: " + programN.Title);
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make POST request to this url
            HttpPut httpPut = new HttpPut(url);
            //HttpGet httpGet = new HttpGet(url);
            String json = "";

            //build json object
            //p1.programT = program1;
            JSONObject jsonObject = new JSONObject();
            Gson gson = new Gson();
            jsonObject.put("Username:", username);
            String t = jsonObject.toString();
            json = gson.toJson(eList) + t;
            //System.out.println("JSON: " + json);
            //JSONObject jsonObjectProgram = new JSONObject();
            //jsonObjectProgram.put("Program:", program.Program);
            //jsonObjectProgram.put("Title:", program.Title);


            //       jsonObject.put("SportProgram:", programN);
            //jsonObject.put("Title", programN.Title);
            //jsonObject.put("Program:", programN.Program);
            //convert json object to JSON to String
            //json = jsonObject.toString();
            System.out.println("JSON: " + json);
            //set json to StringEntity
            StringEntity se = new StringEntity(json);

            //set HttpPost entity
            httpPut.setEntity(se);

            //set some headers to inform server about the type of content
            //httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            // httpPut.setHeader("Authorization", "Bearer " + accessToken);

            //execute POST request to the specified URL
            HttpResponse httpResponse = httpClient.execute(httpPut);
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

    private class ProgramAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            //username = (String) params[1];
            eList = (List<Exercise>) params[1];
            System.out.println("Username test doInBackground: " + username);
            System.out.println("Program test doInBackground: " + programN.Program + " Title: " + programN.Title);
            return saveProgram(url, username, eList);//u);
        }

        protected void onPostExecute(String result) {
            System.out.println("Result: " + result);
            double benchVal = 0;
            try {

                JSONObject jsonObj = new JSONObject(result);
                JSONArray jArray = jsonObj.getJSONArray("ExerciseCollection");
                //need to set my own list to exerciseCollection array and then extract LiftValue variable
                for(int i = 0; i < jArray.length(); i++) {
                    String benchValTemp = jArray.getJSONObject(i).getString("LiftValue");
                    Log.d("Index 2", "benchValTemp: " + benchValTemp);
                    benchVal = Double.parseDouble(benchValTemp);
                    System.out.println("Lift Value: " + benchVal);
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
            Intent intent2 = getIntent();
            Bundle bundle = intent2.getExtras();
            String userName = bundle.getString("username");
            String accessToken = bundle.getString("accessToken");
            String t = benchEdit.getText().toString();
            double tt = Double.parseDouble(t);
            bundle.putDouble("liftValue", tt);
            Intent intent = new Intent(ProgramResults.this, PersonalProgramDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
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

    public void onClick(View v) {
        double bench = Double.parseDouble(benchEdit.getText().toString());
        double squat = Double.parseDouble(squatEdit.getText().toString());
        double chinUps = Double.parseDouble(chinUpsEdit.getText().toString());
        double deadlift = Double.parseDouble(deadliftEdit.getText().toString());
        double shoulderPress = Double.parseDouble(shoulderPressEdit.getText().toString());
        String exercise = benchTxt.getText().toString();
        Exercise benchPressEx = new Exercise();
        benchPressEx.setName(exercise);
        benchPressEx.setLiftValue(bench);
        System.out.println("EXERCISE NAME: " + exercise);

        Exercise squatEx = new Exercise();
        squatEx.setName(squatTxt.getText().toString());
        squatEx.setLiftValue(squat);
       // programN.eList.add(e);
        eList.add(benchPressEx);
            switch ((v.getId())) {
                case R.id.save:
                    new ProgramAsyncTask().execute("http://23.102.22.15/api/Account/updateUserExercise/" + username, eList);
                    break;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_program_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
