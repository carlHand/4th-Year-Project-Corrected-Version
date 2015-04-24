package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
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
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.projectyr4x00091174.carl.traingain.dummy.DummyContent;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
 * A list fragment representing a list of Graphs. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link GraphDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class GraphListFragment extends Fragment  implements ExpandableListView.OnChildClickListener {

/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
*/

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(SportProgram p);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(SportProgram p) {
        }
    };

    String userName;
    String access_token;
    ListView l;
    private ArrayList<SportProgram> programs = new ArrayList<SportProgram>();
    ExpandableListGenre listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<SportProgram>> listDataChild;
    Bundle extras;
    boolean show;
    CheckBox showHelp;
    RESTurl rest;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GraphListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        rest = new RESTurl();
        extras = getActivity().getIntent().getExtras();
        if(getArguments().containsKey("username") && getArguments().containsKey("accessToken"))
        {
            userName = getArguments().getString("username");
            access_token = getArguments().getString("accessToken");
            System.out.println("TESTING LIST NEW USERNAME: " + userName);
            System.out.println("TESTING LIST NEW ACCESS_TOKEN: " + access_token);
        }
        if (getArguments().containsKey("userShow")) {
            show = getArguments().getBoolean("userShow");
            System.out.println("Program results has userShow" + show);
            if (show == true) {
                final Dialog myDialog = new Dialog(getActivity());
                myDialog.setContentView(R.layout.custom_help_graph);
                TextView textView = (TextView) myDialog.findViewById(R.id.graphInfo);
                showHelp = (CheckBox) myDialog.findViewById(R.id.showHide);
                Button myBtnOk = (Button) myDialog.findViewById(R.id.dialogBtn);
                myBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (showHelp.isChecked()) {
                            show = false;
                            new HelpAsyncTask().execute(rest.getREST_UPDATE_USER_HELP_OPTION() + "/" + userName + "/" +  show);
                            myDialog.dismiss();
                        } else {
                            show = true;
                            myDialog.dismiss();
                        }
                    }
                });
                myDialog.show();
            }
        }
        if (getArguments() != null && getArguments().containsKey("programs")) {
            programs = getArguments().getParcelableArrayList("programs");
            generateData();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
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
            return updateUserHelp(params[0], userName, show);//u);
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

    public String updateUserHelp(String url, String username, boolean show)
    {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username:", username);
            jsonObject.put("ShowHelp:", show);
            json = jsonObject.toString();
            System.out.println("JSON: " + json);
            StringEntity se = new StringEntity(json);
            httpPut.setEntity(se);
            httpPut.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPut);

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        SportProgram p = (SportProgram) listAdapter.getChild(groupPosition, childPosition);
        extras.putParcelable("program", p);
        Intent detailIntent = new Intent(getActivity(), GraphDetailActivity.class);
        detailIntent.putExtras(extras);
        startActivity(detailIntent);
        return true;
    }

    private void generateData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<SportProgram>>();

        // Adding data to list
        listDataHeader.add("Beginner");
        listDataHeader.add("Intermediate");
        listDataHeader.add("Advanced");
        String beginnerS = "Beginner";
        String intermediateS = "Intermediate";
        String advancedS = "Advanced";
        List<SportProgram> beginner = new ArrayList<SportProgram>();
        List<SportProgram> intermediate = new ArrayList<SportProgram>();
        List<SportProgram> advanced = new ArrayList<SportProgram>();

        for (int i = 0; i < programs.size(); i++) {
            SportProgram e = programs.get(i);
            if (e.getGenre().equals(beginnerS)) {
                beginner.add(e);
                programs.get(i).setGenre(beginnerS);
                System.out.println("HIT BEGINNER");

            } else if (e.getGenre().equals(intermediateS)) {
                intermediate.add(e);
                programs.get(i).setGenre(intermediateS);
                System.out.println("HIT INTERMEDIATE");
            } else if (e.getGenre().equals(advancedS)) {
                advanced.add(e);
                programs.get(i).setGenre(advancedS);
                System.out.println("HIT ADVANCED");
            } else {
                System.out.println("NOPE!!!");
            }
        }

        listDataChild.put(listDataHeader.get(0), beginner); // Header, Child data
        listDataChild.put(listDataHeader.get(1), intermediate);
        listDataChild.put(listDataHeader.get(2), advanced);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expandable_list_program_search, container, false);
        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListSearch);
        listAdapter = new ExpandableListGenre(getActivity(), getActivity().getLayoutInflater(), getActivity().getSupportFragmentManager(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(this);
        getActivity().setProgressBarIndeterminate(false);
        return rootView;
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
                Bundle bundle = getActivity().getIntent().getExtras();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("").commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}