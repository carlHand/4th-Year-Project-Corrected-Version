package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchProgram extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button search;
    String Sport = "";
    String Goal = "";

    Spinner dropdown;
    Spinner dropdown2;
    private List exampleListItemList;
    String[] sports = {"Boxing", "Basketball", "Tennis", "Rugby"};

    String[] boxing = {"Fast hands", "Slipping", "FootWork"};
    String[] basketball = {"Agility", "Dribbling skills", "Jumping"};
    String[] tennis = {"Serve", "Back Serve", "FootWork"};
    //String[] thaiboxing = {"Explosive legs", "Strength", "Checking"};
    String[] rugby = {"Strength"};

    private static final String TAG_VALUES = "Value";
   // JSONArray programs = null;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<SportProgram> programs = new ArrayList<SportProgram>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program);
        dropdown = (Spinner) findViewById(R.id.spinner1);
        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        search = (Button) findViewById(R.id.search);

        contactList = new ArrayList<HashMap<String, String>>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sports);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        dropdown2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        search.setOnClickListener(this);
        dropdown2.setEnabled(false);
    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        switch (arg0.getId()) {
            case R.id.spinner1:
                dropdown2.setEnabled(true);
                String selectedValue = arg0.getSelectedItem().toString();
                // System.out.println("Initial Goal arg: " + arg2);
                if (dropdown.getSelectedItem().equals("Boxing")) {
                    ArrayAdapter<String> boxingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, boxing);
                    // ArrayTypeAdapter<String> firstAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, boxing);
                    dropdown2.setAdapter(boxingAdapter);
                    Sport = "Boxing";
                   // goal = boxingAdapter.getItem(arg2).toString();
                    // System.out.println("Boxing Goal arg: " + arg2);

                } else if (dropdown.getSelectedItem().equals("Basketball")) {
                    ArrayAdapter<String> basketballAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, basketball);
                    dropdown2.setAdapter(basketballAdapter);
                    Sport = "Basketball";//adapter.getItem(arg2).toString();
                    //goal = basketballAdapter.getItem(arg2).toString();
                    //System.out.println("Basketball Goal arg: " + arg2);
                } else if (dropdown.getSelectedItem().equals("Tennis")) {
                    ArrayAdapter<String> tennisAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tennis);
                    dropdown2.setAdapter(tennisAdapter);
                    Sport = "Tennis";
                } else if (dropdown.getSelectedItem().equals("Rugby")) {
                    ArrayAdapter<String> thaiboxingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rugby);
                    dropdown2.setAdapter(thaiboxingAdapter);
                    Sport = "Rugby";
                }
                break;
            case R.id.spinner2:
                Goal = dropdown2.getSelectedItem().toString();
                Log.e("Goal", Goal);
                //     System.out.println("Sport: " + s + " Arg: " + arg2);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onClick(View v) {
        System.out.println(Sport);
        System.out.println(Goal);
        switch ((v.getId())) {
            case R.id.search:
                // new HttpAsyncTask().execute("http://137.135.189.41/api/Account/P/" + name + "/" + email + "/" + password);// + "/" + email + "/" + password);//P/" + name); //"http://traingain.cloudapp.net/api/Account/Register");
                new HttpAsyncTask().execute("http://23.102.22.15/api/SportPrograms/SearchTest/" + Sport + "/" + Goal);
                break;
        }
    }

    public static String POST(String url, String sport, String goal) {//String u){
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
            // httpGet.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            //httpGet.setHeader("Content-type", "application/json");

            //execute POST request to the specified URL
            HttpResponse httpResponse = httpClient.execute(httpPost);
            // HttpResponse httpResponse = httpClient.execute(httpGet);
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
               // String title = "";
                //String p = "";
                //if(Sport.equals("Boxing") && Goal.equals("Slipping")) {

                exampleListItemList = new ArrayList();

               // TrainingProgram pp = new TrainingProgram();
                //TrainingProgram.ProgramContent p;
                try {

                    JSONArray jsonObj = new JSONArray(result);
                   // programs = jsonObj.getJSONArray(TAG_VALUES);
                    for(int i = 0; i < jsonObj.length(); i++)
                    {

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
                        int sessionsPerDay = Integer.parseInt(sessionsPerDayTemp);
                        JSONArray jArray = c.getJSONArray("Exercises");
                        for(int j = 0; j < jArray.length(); j++) {
                            IDexerciseTemp = jArray.getJSONObject(j).getString("IDexercise");
                            Name = jArray.getJSONObject(j).getString("Name");
                            LiftValueTemp = jArray.getJSONObject(j).getString("LiftValue");
                            IDexercise = Integer.parseInt(IDexerciseTemp);
                            LiftValue = Double.parseDouble(LiftValueTemp);
                            Exercise e = new Exercise();
                            //e.setIDexercise(IDexercise);
                            e.setName(Name);
                            e.setLiftValue(LiftValue);
                            Exercises.add(e);
                            System.out.println("IDEXERCISE: " + IDexercise);
                            System.out.println("NAME: " + Name);
                            System.out.println("LIFTVALUE: " + LiftValue);
                        }
                        String blank = "";
                        System.out.println("Title detail: " + title);
                        System.out.println("Program detail: " + programDetail);


                        HashMap<String, String> program = new HashMap<String, String>();
                        program.put("id", idTemp);
                        program.put("title", title);
                        program.put("program", programDetail);

                    //    p = new TrainingProgram.ProgramContent(title,programDetail);
                       // pp.addItem(new TrainingProgram.ProgramContent(p.title, p.program));
                        SportProgram programN = new SportProgram(id, sport, goal, title, programDetail, intensity, hrsPerDay, sessionsPerDay, Exercises);//,programDetail);
                        programs.add(programN);
                        contactList.add(program);
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
            Intent intent = new Intent(SearchProgram.this, TrainingProgramListActivity.class);
            //intent.putParcelableArrayListExtra("programs", programs);
            //intent.putExtra("email", userEmail);
            intent.putExtras(extras);
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

    public static String search(String sport, String goal)
    {
        return "Hello";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_program, menu);
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
}
