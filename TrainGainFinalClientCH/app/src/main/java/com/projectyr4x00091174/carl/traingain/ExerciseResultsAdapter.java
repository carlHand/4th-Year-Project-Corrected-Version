package com.projectyr4x00091174.carl.traingain;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by carl on 28/03/2015.
 */
public class ExerciseResultsAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    EditText resultText;
    String r;
    Button saveB;
    Exercise e;
    SportProgram programN;
    List<Exercise> eList;
    String username;
    RESTurl rest;

    public ExerciseResultsAdapter(Context context, List<String> listDataHeader, SportProgram programN, String username) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.programN = programN;
        this.username = username;
        eList = new ArrayList<Exercise>();
        rest = new RESTurl();
    }


    @Override
    public String getChild(int groupPosition, int childPosition) {
        return _listDataHeader.get(groupPosition).toString();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View v = null;
        v = View.inflate(_context, R.layout.activity_exercise_result, null);
        resultText = (EditText) v.findViewById(R.id.result);
        saveB = (Button) v.findViewById(R.id.saveR);
        System.out.println("TESTLISTDATAHEADER: " + _listDataHeader.get(groupPosition).toString());
        System.out.println("GROUPpos : " + groupPosition + " CHILDpos: " + childPosition);
        r = _listDataHeader.get(groupPosition).toString();
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double val = 0;
                String curr = r;
                try {

                    val = Double.parseDouble(resultText.getText().toString());
                    if (resultText.getText().toString() != null) {
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
                        Toast.makeText(_context, "Please Enter a Number", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException n) {
                    Toast.makeText(_context, "Please Enter a Number", Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).toString();
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /*
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandable_list_program_search, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }
    */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView.inflate(_context, R.layout.expandable_group_programs_layout, null);
        TextView txtView = (TextView) v.findViewById(R.id.txt1);
        for(int i = 0; i < _listDataHeader.size(); i++) {
                txtView.setText(_listDataHeader.get(groupPosition).toString());
                System.out.println("LISTDATA GETGROUP" + _listDataHeader.get(i).toString());

        }
        v.invalidate();
        return v;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ProgramAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            //username = (String) params[1];
            e = (Exercise) params[1];
            System.out.println("Username test doInBackground: " + username);
            System.out.println("Program test doInBackground: " + programN.getProgram() + " Title: " + programN.getTitle());
            String result = saveProgram(url, username, e);//u);
            double benchVal = 0;
            try {

                JSONObject jsonObj = new JSONObject(result);
                JSONArray jArray = jsonObj.getJSONArray("ExerciseCollection");
                //need to set my own list to exerciseCollection array and then extract LiftValue variable
                double resultVal;
                for (int i = 0; i < jArray.length(); i++) {
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
            return result;
        }

        protected void onPostExecute(String result) {
            if(result != null)
            {
                Toast.makeText(_context, "Saved", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(_context, "Sorry please try again", Toast.LENGTH_LONG).show();
            }
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
}