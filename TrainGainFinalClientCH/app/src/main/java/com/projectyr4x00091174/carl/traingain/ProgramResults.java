package com.projectyr4x00091174.carl.traingain;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.internal.pr;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class ProgramResults extends ActionBarActivity {

    List<Exercise> eList;
    String username;
    SportProgram programN;
    List<Exercise> exercises;
    RESTurl rest;
    TextView myProgramTitle;
    TextView myProgramDesc;
    ExerciseResultsAdapter listAdapter;
    ExpandableListView expListView;
    Button saveB;
    CheckBox showHelp;
    List<String> listDataHeader;
    HashMap<String, List<SportProgram>> listDataChild;
    Context myContext;
    Bundle extras;
    Context context;
    Exercise e;
    private final Handler handler = new Handler();
    private Runnable timer1;
    boolean show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_results);
        rest = new RESTurl();
        myContext = this;
        /*
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llLP = new LinearLayout.LayoutParams(
                //android:layout_width='match_parent' an in xml
                LinearLayout.LayoutParams.MATCH_PARENT,
                //android:layout_height='wrap_content'
                LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(llLP);

 */
        /*
        final RelativeLayout lm = (RelativeLayout) findViewById(R.id.resultLayout);

        // create the layout params that will be used to define how your
        // button will be displayed

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.ALIGN_END, RelativeLayout.ALIGN_LEFT);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        */
        if (getIntent().getExtras().containsKey("userShow")) {
            show = getIntent().getExtras().getBoolean("userShow");
            System.out.println("Program results has userShow" + show);
            if (show == true) {
                final Dialog myDialog = new Dialog(myContext);
                myDialog.setContentView(R.layout.custom_help);
                TextView textView = (TextView) myDialog.findViewById(R.id.programResultsInfo);
                showHelp = (CheckBox) myDialog.findViewById(R.id.showHide);
                Button myBtnOk = (Button) myDialog.findViewById(R.id.dialogBtn);
                myBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (showHelp.isChecked()) {
                            show = false;
                            new HelpAsyncTask().execute(rest.getREST_UPDATE_USER_HELP_OPTION() + "/" + username + "/" +  show);
                            myDialog.dismiss();
                        } else {
                            show = true;
                            myDialog.dismiss();
                        }
                    }
                });
                myDialog.show();
            }
            Intent intent2 = getIntent();
            Bundle extras = intent2.getExtras();
            username = extras.getString("username");
            String access_token = extras.getString("accessToken");
            //userShowHelp = extras.getString("ShowHelp");
            programN = extras.getParcelable("program");
            eList = new ArrayList<Exercise>();
            exercises = programN.getExercises();
            prepareListData();
            removeDuplicates(listDataHeader);
            expListView = (ExpandableListView) findViewById(R.id.expandableListResults);
            listAdapter = new ExerciseResultsAdapter(this, listDataHeader, programN, username);
            // setting list adapter
            expListView.setAdapter(listAdapter);
            expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                public void onGroupExpand(int groupPosition) {

                    int len = listAdapter.getGroupCount();

                    for (int i = 0; i < len; i++) {

                        if (i != groupPosition) {
                            expListView.collapseGroup(i);
                        }
                    }
                }
            });
        }
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        for(int i = 0; i < exercises.size(); i++)
        {
            if(!listDataHeader.contains(exercises.get(i).getName())) {
                listDataHeader.add(exercises.get(i).getName());
                System.out.println("PREPARE DATA not in: " + exercises.get(i).getName());
            }
            else
            {
                System.out.println("Already in: " + exercises.get(i).getName());
            }
        }
    }

    public String updateUserHelp(String url, String username, boolean show)
    {
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //make PUT request to this url
            HttpPut httpPut = new HttpPut(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username:", username);
            jsonObject.put("ShowHelp:", show);
            json = jsonObject.toString();
            System.out.println("JSON: " + json);
            //set json to StringEntity
            StringEntity se = new StringEntity(json);
            //set HttpPot entity
            httpPut.setEntity(se);
            //set some headers to inform server about the type of content
            httpPut.setHeader("Content-type", "application/json");
            //execute PUT request to the specified URL
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

   private class HelpAsyncTask extends AsyncTask<String, Void, String> {
       @Override
       protected String doInBackground(String... params) {
           if(showHelp.isChecked())
           {
               show = true;
           }
           else{
               show = false;
           }
           return updateUserHelp(params[0], username, show);//u);
       }
       protected void onPostExecute(String result) {
           try {
               JSONObject j = new JSONObject(result);
               String sh = j.getString("ShowHelp");
               System.out.println("SH: " + sh);
           }
           catch(JSONException j)
           {
               j.printStackTrace();
           }
           System.out.println("Result: " + result);
       }
   }

/*
    @Override
    public void onResume() {
        super.onResume();
        timer1 = new Runnable() {
            @Override
            public void run() {
                if(show == true) {
                    final Dialog myDialog = new Dialog(myContext);
                    myDialog.setContentView(R.layout.custom_help);
                    TextView textView = (TextView) myDialog.findViewById(R.id.programResultsInfo);
                    final CheckBox showHelp = (CheckBox) myDialog.findViewById(R.id.showHide);
                    Button myBtnOk = (Button) myDialog.findViewById(R.id.dialogBtn);
                    myBtnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (showHelp.isChecked()) {
                                Toast.makeText(myContext, "CHECKED", Toast.LENGTH_LONG).show();
                                show = false;
                                //HelpAsyncTask().execute(rest.getREST_UPDATE_USER_HELP_OPTION() + "/" + username + "/" +  show);
                                myDialog.dismiss();
                            } else {
                                Toast.makeText(myContext, "UnChecked", Toast.LENGTH_LONG).show();
                                show = true;
                                myDialog.dismiss();
                            }
                        }
                    });
                    myDialog.show();
                }
                else{
                    System.out.println("NOT showing help as user set it to false");
                }
            }
        };
        handler.postDelayed(timer1, 100);
    }

    @Override
    public void onPause() {
        // initial = true;
        handler.removeCallbacks(timer1);
        super.onPause();
    }
    */
    static List<String> removeDuplicates(List<String> list) {

        // Store unique items in result.
        List<String> result = new ArrayList();

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

    public String getUserLifts(String URL) {
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
            System.out.println("STATUSCODE: " + statusCode);
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

    public String saveProgram(String url, String username, Exercise e) {
        // Log.d("tag", "SaveProgram Test Program: " + programN.Program + " Title: " + programN.Title);
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make PUT request to this url
            HttpPut httpPut = new HttpPut(url);
            //HttpGet httpGet = new HttpGet(url);
            String json = "";

            //build json object
            //p1.programT = program1;
            JSONObject jsonObject = new JSONObject();
            Gson gson = new Gson();
            jsonObject.put("Username:", username);
            String t = jsonObject.toString();
            json = gson.toJson(e) + t;
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
        } catch (Exception ex) {
            Log.d("InputStream", ex.getLocalizedMessage());
        }
        return result;
    }

    private class ProgramAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            //username = (String) params[1];
            e = (Exercise) params[1];
            System.out.println("Username test doInBackground: " + username);
            System.out.println("Program test doInBackground: " + programN.getProgram() + " Title: " + programN.getTitle());
            return saveProgram(url, username, e);//u);
        }

        protected void onPostExecute(String result) {
            System.out.println("Result: " + result);
            double benchVal = 0;
            try {

                JSONObject jsonObj = new JSONObject(result);
                JSONArray jArray = jsonObj.getJSONArray("ExerciseCollection");
                //need to set my own list to exerciseCollection array and then extract LiftValue variable
                double resultVal;
                for(int i = 0; i < jArray.length(); i++) {
                    String resultsTemp = jArray.getJSONObject(i).getString("LiftValue");
                    String eName = jArray.getJSONObject(i).getString("Name");
                    Log.d("Index 2", "eName: " + eName);
                    Log.d("Index 2", "ValTemp: " + resultsTemp);
                    resultVal = Double.parseDouble(resultsTemp);
                    System.out.println("Lift Value: " + resultsTemp);
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
            Intent intent2 = getIntent();
            Bundle bundle = intent2.getExtras();
            String userName = bundle.getString("username");
            String accessToken = bundle.getString("accessToken");
            bundle.putParcelable("program", programN);
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
/*
    public void onClick(View v) {
        double val = 0;
        String curr = listAdapter.r;
        try {

            val = Double.parseDouble(listAdapter.resultText.getText().toString());
            if (listAdapter.resultText.getText().toString() != null) {
                programN.setExercises(new ArrayList<Exercise>());
                e = new Exercise();
                e.setName(curr);
                e.setLiftValue(val);
                e.SportProgramCollectionE.add(programN);
                eList.add(e);
                System.out.println("CURR GROUP IS " + curr);
                System.out.println("VAL CHILD IS " + val);
                System.out.println("NAME E: " + e.getName());
                for (int i = 0; i < eList.size(); i++) {
                    System.out.println("ELIST NAME: " + eList.get(i).getName());
                    System.out.println("ELIST VAL: " + eList.get(i).getLiftValue());
                }
                new ProgramAsyncTask().execute(rest.getREST_UPDATE_USER_RESULTS() + username, e);
            } else {
                Toast.makeText(this, "Please Enter a Number", Toast.LENGTH_LONG).show();
            }
        }catch(NumberFormatException n)
        {
            Toast.makeText(this, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
    }
*/
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
                Intent back = new Intent(this, PersonalProgramDetailActivity.class);
                Bundle extras = this.getIntent().getExtras();
                back.putExtras(extras);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                NavUtils.navigateUpTo(this);
                */
                finish();
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
