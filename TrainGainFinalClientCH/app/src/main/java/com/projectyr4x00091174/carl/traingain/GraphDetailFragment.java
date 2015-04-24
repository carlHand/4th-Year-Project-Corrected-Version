package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


//import com.google.android.gms.internal.ge;
import com.google.gson.Gson;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * A fragment representing a single Graph detail screen.
 * This fragment is either contained in a {@link GraphListActivity}
 * in two-pane mode (on tablets) or a {@link GraphDetailActivity}
 * on handsets.
 */

public class GraphDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener {
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

    SportProgram program;
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
    GraphView graph;
    private double graph2LastXValue = 5d;
    double pointX;
    double pointY;
    String username;
    String title;
    String titleTemp;
    StaticLabelsFormatter staticLabelsFormatter;
    List<Exercise> Exercises = new ArrayList<Exercise>();
    TextView gTitle;
   // boolean first;
    boolean initial;
    boolean newFirst;
    boolean newInitial;
    boolean empty;
    RESTurl rest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Exercise> exercises = new ArrayList<Exercise>();
        extras = getActivity().getIntent().getExtras();
       // first = false;
        rest = new RESTurl();
        if (getArguments().containsKey("program")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // mItem = TrainingProgram.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            // program1 = getArguments().getParcelable("program");
            program = extras.getParcelable("program");
            // program.Intensity = "High";
            //s.Program = programN.Program;
            Log.v("tpdf", "hit if, arguments contains program");
            System.out.println("ID: " + program.getIDsport() + "\nSport: " + program.getSport() + "\nGoal: " + program.getGoal()
                    + "\nTitle: " + program.getTitle() + "\nProgram: " + program.getProgram() + "\nIntensity: " + program.getIntensity()
                    + "\nHrsPerDay: " + program.getHrsPerDay() + "\nSessionsPerDay: " + program.getSessionsPerDay());
            title = program.getTitle();
            titleTemp = program.getTitle();

            for (int i = 0; i < program.getExercises().size(); i++) {
                exercises.add(program.getExercises().get(i));
            }
            for (int j = 0; j < exercises.size(); j++) {
                Exercise e = exercises.get(j);
                System.out.println("Exercise ID: " + e.getIDexercise());
                System.out.println("Exercise name: " + e.getName());
                System.out.println("Exercise Lift Value: " + e.getLiftValue());
                System.out.println("Exercise date:" + e.getDateLogged());
            }
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
        if (getArguments().containsKey("username")) {
            Log.v("Hit if", "argument contains username");
            username = extras.getString("username");
            // new UserLiftsAsyncTask().execute("http://23.102.22.15/api/Account/userLifts/" + username);
        } else {
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
        //gTitle = (TextView) rootView.findViewById(R.id.graphTitle);
        graph = (GraphView) rootView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        mSeries1 = new LineGraphSeries<DataPoint>();
        //graph.addSeries(mSeries1);
        staticLabelsFormatter = new StaticLabelsFormatter(graph);
        mSeries1.setDrawDataPoints(true);
        mSeries1.setDataPointsRadius(15f);
        mSeries1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series mSeries1, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Value: " + Exercises.get((int) dataPoint.getX()-1).getLiftValue() + "\nLogged on: " + Exercises.get((int) dataPoint.getX()-1).getDateLogged().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // gTitle.setTextColor(Color.GREEN);
        //gTitle.setText("GTITLE");
        // mSeries1.setTitle("HELLO");
        // mSeries1.setBackgroundColor(Color.BLUE);
        // graph.addSeries(mSeries1);


        Exercise temp = new Exercise();
        temp.setName("");
        program.getExercises().add(temp);
        int size = program.getExercises().size();
        List<Exercise> exercises = new ArrayList<Exercise>();
        String[] exerciseDropDown = new String[size];

        for (int i = 0; i < program.getExercises().size(); i++) {
            exercises.add(program.getExercises().get(i));
        }
        for (int j = 0; j < exercises.size(); j++) {
            Exercise e = exercises.get(j);
            exerciseDropDown[j] = e.getName();
            // System.out.println("Lifts!!!: " + e.getLiftValue());
            series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(j, e.getLiftValue())
            });
            //System.out.println("Point x: " + j + " LiftValue: " + e.getLiftValue());
        }
        exerciseDropDown[size - 1] = exerciseDropDown[0];
        exerciseDropDown[0] = "";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, exerciseDropDown);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mTimer1 = new Runnable() {
            @Override
            public void run() {
//                graph.removeSeries(mSeries1);
                              String u = rest.getREST_GET_USER_PROGRAM_RESULTS() + username + "/" + title;
                              URI uri = null;
                              try {
                                  uri = new URI(u.replaceAll(" ", "%20"));
                                  // title = titleTemp.trim();
                                  //title = URLEncoder.encode(title,"UTF-8");
                                  System.out.println("TITLE!!!!!!!!: " + uri.toString());
                                  new UserLiftsAsyncTask().execute(uri.toString());//carl/Program for Strength");
                              } catch (URISyntaxException e) {
                                  e.printStackTrace();
                              }

                              System.out.println("TESTING NULL NAME: " + username);
                System.out.println("ID: " + program.getIDsport() + "\nSport: " + program.getSport() + "\nGoal: " + program.getGoal()
                        + "\nTitle: " + program.getTitle() + "\nProgram: " + program.getProgram() + "\nIntensity: " + program.getIntensity()
                        + "\nHrsPerDay: " + program.getHrsPerDay() + "\nSessionsPerDay: " + program.getSessionsPerDay());
                          }

        };
        mHandler.postDelayed(mTimer1, 100);
    }

    @Override
    public void onPause() {
       // initial = true;
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
    //    graph.removeSeries(mSeries1);
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        switch (arg0.getId()) {
            case R.id.spinner2:
                graph.removeAllSeries();
                mSeries1.resetData(new DataPoint[]{});
                boolean first = false;
                // mSeries1.appendData(new DataPoint(0, 0), true, 40);
                for (int i = 0; i < Exercises.size(); i++) {
                    if (dropdown.getSelectedItem().equals(Exercises.get(i).getName())) {
                        Exercise e = new Exercise();
                        e.setName(Exercises.get(i).getName());
                        e.setDateLogged(Exercises.get(i).getDateLogged());
                        graph.setTitle(e.getName());
                        if (!first) {
                            e.setLiftValue(0);
                            mSeries1.appendData(new DataPoint(i, e.getLiftValue()), true, 40);
                            first = true;
                        }

                        e.setLiftValue(Exercises.get(i).getLiftValue());
                        /*
                        mSeries1.resetData(new DataPoint[]{
                                        new DataPoint(i, e.getLiftValue())
                                });
                        */
                        mSeries1.setTitle(e.getName());

                        System.out.println("LIFT VAL EXERCISE: " + Exercises.get(0).getLiftValue());

                        mSeries1.appendData(new DataPoint(i + 1, e.getLiftValue()), true, 40);
                        System.out.println("LIFT VAL EXERCISE ELSE: " + Exercises.get(0).getLiftValue());

                        System.out.println("EXERCISE SIZE: " + Exercises.size());
                        System.out.println("DROPDOWN.getSelectedItem(): " + dropdown.getSelectedItem().toString());
                        System.out.println("EXERCISES NAME: " + Exercises.get(i).getName());
                        System.out.println("EXERCISES LiftValue: " + Exercises.get(i).getLiftValue());
                        System.out.println("EXERCISES DATE: " + Exercises.get(i).getDateLogged());

                    } else {
                        System.out.println("NOT EQUAL TO EXERCISE");
                    }

                }

                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            return super.formatLabel(value, isValueX);
                        } else {
                            // show currency for y values
                            return super.formatLabel(value, isValueX) + " KG";
                        }
                    }
                });

                    graph.addSeries(mSeries1);


                // mSeries1.resetData(new DataPoint[]{});
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    private DataPoint[] generateData() {
        DataPoint[] values = new DataPoint[Exercises.size()];
        for (int j = 0; j < Exercises.size(); j++)
        {
            Exercise e1 = new Exercise();
            e1.setName(Exercises.get(j).getName());
            e1.setLiftValue(Exercises.get(j).getLiftValue());
            if (e1.getName().equals(Exercises.get(0).getName())) {
                mSeries1.appendData(new DataPoint(j, e1.getLiftValue()), true, 40);
                DataPoint v = new DataPoint(j, e1.getLiftValue());
                values[j] = v;
            } else {
                System.out.println(e1.getName() + " IS NOT FIRST IN DROPDOWN");
                System.out.println(Exercises.get(0).getName() + " IS FIRST IN DROPDOWN");

            }
            graph.getGridLabelRenderer().

                    setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                // show normal x values
                                return super.formatLabel(value, isValueX);
                            } else {
                                // show currency for y values
                                return super.formatLabel(value, isValueX) + " KG";
                            }
                        }
                    });
        }
        return values;
    }

    private class UserLiftsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return getUserLifts(urls[0]);
        }

        protected void onPostExecute(String result) {
            String Name = "";
            int IDexercise = 0;
            Date date = new Date();
            double LiftValue = 0.0;

            if (result != null) {
                System.out.println("Result: " + result);
                try {
                    if(Exercises.isEmpty())
                    {
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
                        String dateTemp = jObject.getString("DateLogged");

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try{
                            date = format.parse(dateTemp);
                        }
                        catch(ParseException p)
                        {
                            p.printStackTrace();
                        }
                        e.setDateLogged(date);
                        Exercises.add(e);
                        System.out.println("Profile IDExercise: " + IDexercise);
                        System.out.println("Profile Name: " + Name);
                        System.out.println("IIIIIIIIIIIIIIIIIIIII: " + i);
                        System.out.println("DATE TTT: " + date);
                        System.out.println("DATETEMP: " + dateTemp);
                        System.out.println("Profile LiftValue: " + LiftValue);
                    }
                }
                    else{
                        System.out.println("EXERCISES IS ALREADY FULL " + Exercises.size());
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            } else {
                System.out.println("Error");
            }
        }
    }
}