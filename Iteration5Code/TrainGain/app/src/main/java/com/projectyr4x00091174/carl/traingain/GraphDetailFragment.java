package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

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

/**
 * A fragment representing a single Graph detail screen.
 * This fragment is either contained in a {@link GraphListActivity}
 * in two-pane mode (on tablets) or a {@link GraphDetailActivity}
 * on handsets.
 */

public class GraphDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GraphDetailFragment() {
    }

    SportProgram programN;
    Bundle extras;
    Double benchVal;
    int i = 1;
    double start = 0;
    double end = 0;
    Spinner dropdown;

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph2LastXValue = 5d;

    String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extras = getActivity().getIntent().getExtras();
        if (getArguments().containsKey("program")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // mItem = TrainingProgram.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            // program1 = getArguments().getParcelable("program");
            programN = extras.getParcelable("program");
            //s.Program = programN.Program;
            Log.v("tpdf", "hit if, arguments contains program");
        } else {
            Log.v("No program", "argument does not have key");
        }
        if (getArguments().containsKey("liftValue")) {
            benchVal = extras.getDouble("liftValue");
            Log.v("YES", "argument contains LiftValue");
        } else {
            Log.v("No Lift Value", "argument does not have key");
            benchVal = 0.0;
        }
        if (getArguments().containsKey("start") && getArguments().containsKey("end")) {
            start = extras.getDouble("start");
            end = extras.getDouble("end");
            Log.v("GraphDetailFragment has keys", "Start: " + start + " end: " + end);
        } else {
            Log.v("NO!", "GraphDetailFragment does not have start and end, setting them to 0");
            start = 0;
            end = 0;
        }
        if(getArguments().containsKey("username"))
        {
            Log.v("Hit if", "argument contains username");
            username = extras.getString("username");
            new UserLiftsAsyncTask().execute("http://23.102.22.15/api/Account/userLifts/" + username);
        }
        else{
            Log.v("No username", "argument does not contain key");
        }
    }

    public interface OnDataPass {
        public void onDataPass(double data, double data2);
    }

    OnDataPass dataPasser;


    public void passData(double data, double data2) {
        dataPasser.onDataPass(data, data2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_detail, container, false);

        dropdown = (Spinner) rootView.findViewById(R.id.spinner2);
        int size = programN.Exercises.size();
        List<Exercise> exercises = new ArrayList<Exercise>();
        String[] exerciseDropDown = new String[size];
        for (int i = 0; i < programN.Exercises.size(); i++) {
            exercises.add(programN.Exercises.get(i));
        }
        for (int j = 0; j < exercises.size(); j++) {
            Exercise e = exercises.get(j);
            exerciseDropDown[j] = e.getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, exerciseDropDown);
        dropdown.setAdapter(adapter);

        // Show the dummy content as text in a TextView.
        /*
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.graph_detail)).setText(mItem.content);
        }
        */
/*
        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        System.out.println("I: " + i + " benchVAL: " + benchVal);
        System.out.println("start: " + start + " end: " + end);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, 3)
                /*,
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)

        });
        graph.addSeries(series);

        i++;
        start = i;
        if(benchVal == null)
        {
            benchVal = 0.0;
            end = benchVal;
        }
        else {
            end = benchVal;
        }
        extras.putDouble("start", start);
        extras.putDouble("end", end);
       // passData(start, end);
        Log.v("Data Points", "i: " + i + "Start: " + start + "End: " + end + " benchVal: " + benchVal);
        */
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                //new UserLiftsAsyncTask().execute("http://23.102.22.15/api/Account/userLifts/" + username);
               // mSeries1.resetData(generateData());
                //mHandler.postDelayed(this, 300);
            }
        };
        mHandler.postDelayed(mTimer1, 300);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
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

    private class UserLiftsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return getUserLifts(urls[0]);
        }

        protected void onPostExecute(String result) {
            String Name = "";
            int IDexercise = 0;
            double LiftValue = 0.0;
            List<Exercise> Exercises = new ArrayList<Exercise>();
            if (result != null) {
                System.out.println("Result: " + result);
                //Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
                try {

                    JSONArray jsonObj = new JSONArray(result);
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject jObject = jsonObj.getJSONObject(i);
                            IDexercise = jObject.getInt("IDexercise");
                            Name = jObject.getString("Name");
                            LiftValue = jObject.getDouble("LiftValue");
                            Exercise e = new Exercise();
                            e.setIDexercise(IDexercise);
                            e.setName(Name);
                            e.setLiftValue(LiftValue);
                            Exercises.add(e);
                            System.out.println("Profile IDExercise: " + IDexercise);
                            System.out.println("Profile Name: " + Name);
                            System.out.println("Profile LiftValue: " + LiftValue);
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
            } else {
                System.out.println("Error");
            }
            for(int j = 0; j < Exercises.size(); j++)
            {
                System.out.println("Exercises: " + Exercises.get(j));
            }
        }
    }
}