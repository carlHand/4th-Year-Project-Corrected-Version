package com.projectyr4x00091174.carl.traingain;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A fragment representing a single Training Program detail screen.
 * This fragment is either contained in a {@link TrainingProgramListActivity}
 * in two-pane mode (on tablets) or a {@link TrainingProgramDetailActivity}
 * on handsets.
 */
public class TrainingProgramDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

   // public static final String ARG_ITEM_ID = "item_title";

    /**
     * The dummy content this fragment is presenting.
     */


    SportProgram programN;
    Person p = new Person();
    String username;
    String accessToken;
    //private TrainingProgram.ProgramContent mItem;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrainingProgramDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        if (getArguments().containsKey("program")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
           // mItem = TrainingProgram.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
           // program1 = getArguments().getParcelable("program");
            programN = extras.getParcelable("program");
            //s.Program = programN.Program;
            Log.v("tpdf", "hit if, arguments contains program");
        }
        else{
            Log.v("No program", "argument does not have key");
        }
        if(getArguments().containsKey("username"))
        {
           // userEmail = getArguments().getString("email");
            username = extras.getString("username");
            System.out.println("TrainingProgramDetailFragment Test userName: " + username);
        }
        else{
            Log.v("No Email", "argument does not have key");
        }
        if(getArguments().containsKey("accessToken"))
        {
            // userEmail = getArguments().getString("email");
            accessToken = extras.getString("accessToken");
            System.out.println("TrainingProgramDetailFragment Test access_token: " + accessToken);
        }
        else{
            Log.v("No access_token", "argument does not have key");
        }
    }
    /*
    public String saveProgram(String url, String userN, SportProgram programN)
    {
        Log.d("tag","SaveProgram Test Program: " + programN.Program + " Title: " + programN.Title);
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

            //JSONObject jsonObjectProgram = new JSONObject();
            //jsonObjectProgram.put("Program:", program.Program);
            //jsonObjectProgram.put("Title:", program.Title);

            jsonObject.put("Username:", userN);

            jsonObject.put("Title:", programN.Title);
            jsonObject.put("Program:", programN.Program);
            //convert json object to JSON to String
            json = jsonObject.toString();

            //set json to StringEntity
            StringEntity se = new StringEntity(json);

            //set HttpPost entity
            httpPut.setEntity(se);

            //set some headers to inform server about the type of content
            //httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setHeader("Authorization", "Bearer " + accessToken);

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
        */

    public String saveProgram(String url, String username, SportProgram programN)
    {
        Log.d("tag","SaveProgram Test Program: " + programN.Program + " Title: " + programN.Title);
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
            json = gson.toJson(programN) + t;
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
            httpPut.setHeader("Authorization", "Bearer " + accessToken);

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
           programN = (SportProgram) params[1];
            System.out.println("Username test doInBackground: " + username);
            System.out.println("Program test doInBackground: " + programN.Program + " Title: " + programN.Title);
            return saveProgram(url, username,  programN);//u);
        }

        protected void onPostExecute(String result) {
            System.out.println("Result: " + result);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trainingprogram_detail, container, false);
        //p.programT = program1;
        System.out.println("Username test onCreateView: " + username);
        Button savePrg = (Button) rootView.findViewById(R.id.saveProgram);
        System.out.println("ID: " + programN.IDsport + "\nSport: " + programN.Sport + "\nGoal: " + programN.Goal
                            + "\nTitle: " + programN.Title + "\nProgram: " + programN.Program + "\nIntensity: " + programN.Intensity
                            + "\nHrsPerDay: " + programN.HrsPerDay + "\nSessionsPerDay: " + programN.SessionsPerDay);
        savePrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.saveProgram:
                        new ProgramAsyncTask().execute("http://23.102.22.15/api/Account/updateUser/" + username, programN);
                        break;
                }
            }
        });
        // Show the dummy content as text in a TextView.
        if (programN != null) {
            ((TextView) rootView.findViewById(R.id.trainingprogram_detail)).setText(programN.Program + " Details");

        }
        else{
            ((TextView)  rootView.findViewById(R.id.trainingprogram_detail)).setText("Welcome to Train Droid");
        }

        return rootView;
    }
}
