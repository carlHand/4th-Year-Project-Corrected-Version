package com.projectyr4x00091174.carl.traingain;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.socialize.ActionBarUtils;
import com.socialize.Socialize;
import com.socialize.entity.Entity;
import com.socialize.entity.Like;
import com.socialize.entity.Share;
import com.socialize.ui.actionbar.ActionBarListener;
import com.socialize.ui.actionbar.ActionBarOptions;
import com.socialize.ui.actionbar.ActionBarView;
import com.socialize.ui.actionbar.OnActionBarEventListener;

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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A fragment representing a single Training Program detail screen.
 * This fragment is either contained in a {@link TrainingProgramListActivity}
 * in two-pane mode (on tablets) or a {@link TrainingProgramDetailActivity}
 * on handsets.
 */
public class TrainingProgramDetailFragment extends Fragment implements OnExerciseImageLoaderFinListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    SportProgram programN;
    ApplicationUser p = new ApplicationUser();
    String username;
    String accessToken;
    ArrayList<Exercise> eList;
    List<ExerciseImage> eImgList;
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    ArrayList<Exercise> Exercises = new ArrayList<Exercise>();
    String saveBtnText = "Add Program";
    protected Entity entity;
    RESTurl rest = new RESTurl();
    boolean show;
    Context myContext;
    HttpAsyncTask httpAsyncTask;
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
        Bundle fragExtras = getArguments();
        if (getArguments().containsKey("program")) {
            programN = extras.getParcelable("program");
            eList = new ArrayList<Exercise>();
            eImgList = new ArrayList<ExerciseImage>();
            System.out.println("DETAILFRAGMENT!!! ID: " + programN.getIDsport() + "\nSport: " + programN.getSport() + "\nGoal: " + programN.getGoal()
                    + "\nTitle: " + programN.getTitle() + "\nProgram: " + programN.getProgram() + "\nIntensity: " + programN.getIntensity()
                    + "\nHrsPerDay: " + programN.getHrsPerDay() + "\nSessionsPerDay: " + programN.getSessionsPerDay() + "\n");
            for (int i = 0; i < programN.getExercises().size(); i++) {
                eList.add(programN.getExercises().get(i));
                System.out.println("EXERCISES TEST: " + programN.getExercises().get(i).getName());
                for (int j = 0; j < programN.getExercises().get(i).ExerciseImageL.size(); j++) {
                    System.out.println("IMAGE TESTING initial" + programN.getExercises().get(i).ExerciseImageL.get(j).URLimg.toString());
                }
            }
            for(int i = 0; i < eList.size(); i++)
            {
                System.out.println("exerciseResult oncreate NAme: " + eList.get(i).getName());
                for(int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {
                    System.out.println("exerciseResult oncreate Image: " + eList.get(i).ExerciseImageL.get(j).URLimg);
                }
                for(int k = 0; k < eList.get(i).StepL.size(); k++) {
                    System.out.println("exerciseResult oncreate STEP: " + eList.get(i).StepL.get(k).StepDes);
                }
            }
            Log.v("tpdf", "hit if, arguments contains program");
        } else {
            Log.v("No program", "argument does not have key");
        }
        if (getArguments().containsKey("username")) {
            username = extras.getString("username");
            System.out.println("TrainingProgramDetailFragment Test userName: " + username);
        } else {
            Log.v("No Email", "argument does not have key");
        }
        if (getArguments().containsKey("accessToken")) {
            accessToken = extras.getString("accessToken");
            System.out.println("TrainingProgramDetailFragment Test access_token: " + accessToken);
        } else {
            Log.v("No access_token", "argument does not have key");
        }
        if (getArguments().containsKey("userShow")) {
            show = extras.getBoolean("userShow");
            System.out.println("TrainingProgramDetailFragment Test userShow: " + show);
        } else {
            Log.v("No show", "argument does not have key");
        }


        // Call Socialize in onCreate

/*
        StictModeUtils.enableDefaults();

        Socialize.onCreate(getActivity(), savedInstanceState);

        final SafeProgressDialog dialog = SafeProgressDialog.show(getActivity());

        // NOTE:
        // THIS call to initAsync is NOT required by normal apps.  We are just
        // doing this in our demo to disable some analytics reporting.
        Socialize.initAsync(getActivity(), new SocializeInitListener() {
            @Override
            public void onInit(Context context, IOCContainer container) {
                SocializeConfig config = ConfigUtils.getConfig(context);

                config.setProperty(SocializeConfig.SOCIALIZE_EVENTS_AUTH_ENABLED, "false");
                config.setProperty(SocializeConfig.SOCIALIZE_EVENTS_SHARE_ENABLED, "false");

                String entityKey = config.getProperty("entity.key");
                String entityName = config.getProperty("entity.name");

                entity = Entity.newInstance(entityKey, entityName);
                entity.setType("article");

                // Standard GCM Registration
                // This is simply to verify that SmartAlerts work where there is already a GCM implementation
                // If you are not already using GCM you can ignore this.


                onCreate();

                dialog.dismiss();
            }


            @Override
            public void onError(SocializeException error) {
                // Handle error
                error.printStackTrace();

                dialog.dismiss();
            }
        });


        // Set a listener for SocializeUIActivity events
        Socialize.setSocializeActivityLifecycleListener(new DefaultSocializeActivityLifecycleListener() {

            @Override
            public boolean onActivityResult(SocializeUIActivity activity, int requestCode, int resultCode, Intent data) {
                toast(activity, "onActivityResult");
                return super.onActivityResult(activity, requestCode, resultCode, data);
            }

            @Override
            public boolean onBackPressed(SocializeUIActivity activity) {
                toast(activity, "onBackPressed");
                return super.onBackPressed(activity);
            }

            @Override
            public boolean onContextItemSelected(SocializeUIActivity activity, MenuItem item) {
                toast(activity, "onContextItemSelected");
                return super.onContextItemSelected(activity, item);
            }

            @Override
            public boolean onContextMenuClosed(SocializeUIActivity activity, Menu menu) {
                toast(activity, "onContextMenuClosed");
                return super.onContextMenuClosed(activity, menu);
            }

            @Override
            public void onCreate(SocializeUIActivity activity, Bundle savedInstanceState) {
                toast(activity, "onCreate");
                super.onCreate(activity, savedInstanceState);
            }

            @Override
            public boolean onCreateContextMenu(SocializeUIActivity activity, ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                toast(activity, "onCreateContextMenu");
                return super.onCreateContextMenu(activity, menu, v, menuInfo);
            }

            @Override
            public Dialog onCreateDialog(SocializeUIActivity activity, int id, Bundle args) {
                toast(activity, "onCreateDialog");
                return super.onCreateDialog(activity, id, args);
            }

            @Override
            public boolean onCreateOptionsMenu(SocializeUIActivity activity, Menu menu) {
                toast(activity, "onCreateOptionsMenu");
                return super.onCreateOptionsMenu(activity, menu);
            }

            @Override
            public void onDestroy(SocializeUIActivity activity) {
                toast(activity, "onDestroy");
                super.onDestroy(activity);
            }

            @Override
            public boolean onMenuItemSelected(SocializeUIActivity activity, int featureId, MenuItem item) {
                toast(activity, "onMenuItemSelected");
                return super.onMenuItemSelected(activity, featureId, item);
            }

            @Override
            public boolean onMenuOpened(SocializeUIActivity activity, int featureId, Menu menu) {
                toast(activity, "onMenuOpened");
                return super.onMenuOpened(activity, featureId, menu);
            }

            @Override
            public void onNewIntent(SocializeUIActivity activity, Intent intent) {
                toast(activity, "onNewIntent");
                super.onNewIntent(activity, intent);
            }

            @Override
            public boolean onOptionsItemSelected(SocializeUIActivity activity, MenuItem item) {
                toast(activity, "onOptionsItemSelected");
                return super.onOptionsItemSelected(activity, item);
            }

            @Override
            public boolean onOptionsMenuClosed(SocializeUIActivity activity, Menu menu) {
                toast(activity, "onOptionsMenuClosed");
                return super.onOptionsMenuClosed(activity, menu);
            }

            @Override
            public void onPause(SocializeUIActivity activity) {
                toast(activity, "onPause");
                super.onPause(activity);
            }

            @Override
            public void onRestart(SocializeUIActivity activity) {
                toast(activity, "onRestart");
                super.onRestart(activity);
            }

            @Override
            public void onResume(SocializeUIActivity activity) {
                toast(activity, "onResume");
                super.onResume(activity);
            }

            @Override
            public void onStart(SocializeUIActivity activity) {
                toast(activity, "onStart");
                super.onStart(activity);
            }

            @Override
            public void onStop(SocializeUIActivity activity) {
                toast(activity, "onStop");
                super.onStop(activity);
            }
        });

    }

    protected void onCreate() {

    }

    private void toast(Activity activity, String text) {
        Log.i("Socialize", activity.getClass().getSimpleName() +  ":SocializeActivityLifecycleListener:" + text);
    }
*/
    }
    @Override
    public void onResume() {
        super.onResume();
        Socialize.onResume(getActivity());
        final OnExerciseImageLoaderFinListener cb = this;
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                String u = rest.getREST_GET_EXERCISE_IMAGES() + programN.getTitle();
                URI uri = null;
                try {
                    uri = new URI(u.replaceAll(" ", "%20"));
                    System.out.println("TITLE!!!!!!!!: " + uri.toString());
                    for (int k = 0; k < eList.size(); k++) {
                        if (eList.get(k).ExerciseImageL.isEmpty()) {
                            httpAsyncTask = new HttpAsyncTask();
                            httpAsyncTask.setOnExerciseImageLoaderFinListener(cb);
                            httpAsyncTask.execute(uri.toString());
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

        };
        mHandler.postDelayed(mTimer1, 100);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
        Socialize.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        // Call Socialize in onDestroy before the activity is destroyed
        Socialize.onDestroy(getActivity());
        super.onDestroy();
    }

    public String readJSONFeed(String URL) {
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
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }

        return result;
    }

    @Override
    public ArrayList<Exercise> OnExerciseImageLoaderFinListener(ArrayList<Exercise> results) {
        return results;
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<Exercise>>
    {
        private OnExerciseImageLoaderFinListener listener;
        public void setOnExerciseImageLoaderFinListener(OnExerciseImageLoaderFinListener listener)
        {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Exercise> doInBackground(String... urls) {
            String result =  readJSONFeed(urls[0]);
            String Name = "";
            Exercise e1 = new Exercise();

            ArrayList<ExerciseImage> exerciseImgList = new ArrayList<ExerciseImage>();
            ExerciseImage eImage = new ExerciseImage();
            if (result != null) {
                System.out.println("Result: " + result);
                System.out.println("CLEAR TEST");
                Date date = new Date();
                try {
                    for (int k = 0; k < eList.size(); k++) {
                        System.out.println("ELIST SIZE ! : " + eList.size());
                        if (eList.get(k).ExerciseImageL.isEmpty()) {
                            JSONArray jsonObj = new JSONArray(result);
                            for (int i = 0; i < jsonObj.length(); i++) {
                                if (!Exercises.isEmpty()) {
                                    System.out.println("HIT CLEAR");
                                }
                                JSONObject c = jsonObj.getJSONObject(i);

                                JSONArray jArray = c.getJSONArray("Exercises");
                                JSONArray imgJSON = null;
                                for (int j = 0; j < jArray.length(); j++) {
                                    Name = jArray.getJSONObject(j).getString("Name");

                                    JSONArray imageArray = jArray.getJSONObject(j).getJSONArray("ExerciseImageL");
                                    int er = imageArray.length();
                                    //byte[] eBytes = new byte[er];
                                    System.out.println("ER: " + er);
                                    for (int a = 0; a < imageArray.length(); a++) {
                                        String imageS = imageArray.getJSONObject(a).getString("URLimg");
                                        /*
                                        byte[] eBytes = Base64.decode(imageS.getBytes(), Base64.DEFAULT);
                                        eImage = new ExerciseImage();
                                        eImage.Image = eBytes.clone();
                                        */
                                        eImage = new ExerciseImage();
                                        eImage.URLimg = imageS;
                                        System.out.println("eImage: " + eImage.URLimg);
                                        exerciseImgList.add(eImage);
                                    }
                                    e1 = new Exercise();
                                    e1.setName(Name);
                                    e1.ExerciseImageL = exerciseImgList;
                                    Exercises.add(e1);
                                    eList.get(j).ExerciseImageL = exerciseImgList;
                                    System.out.println("NAME:e " + Exercises.get(j).getName());
                                    System.out.println("EXERCISES SIZEE: " + Exercises.get(j).ExerciseImageL.size());
                                    exerciseImgList = new ArrayList<ExerciseImage>();
                                }
                            }
                        }
                        else{
                            System.out.println("ELIST IS ALREADY DONE");
                        }
                    }
                }catch (JSONException j) {
                    j.printStackTrace();
                }
            } else {
                System.out.println("Error");
            }
            System.out.println("EXERCISES SIZE: " + Exercises.size());
            for (int i = 0; i < Exercises.size(); i++) {
                for(int j = 0; j < Exercises.get(i).ExerciseImageL.size(); j++) {
                    System.out.println("ADDING: EXERCISES: " + Exercises.get(i).ExerciseImageL.get(j).URLimg + " to ELIST");
                    System.out.println("EXERCISE: size name:" + Exercises.get(i).getName() + " Size:" + Exercises.get(i).ExerciseImageL.size());
                    // eList.get(i).ExerciseImageL = Exercises.get(i).ExerciseImageL;
                    System.out.println("ELIST SIZE: " + eList.get(i).ExerciseImageL.size());
                    System.out.println("EXER SIZE: " + Exercises.get(i).ExerciseImageL.size());
                }
            }
            return Exercises;
        }
        protected void onPostExecute(ArrayList<Exercise> exerciseResult)
        {
            super.onPostExecute(exerciseResult);
            for(int i = 0; i < exerciseResult.size(); i++)
            {
                System.out.println("exerciseResult NAme: " + exerciseResult.get(i).getName());
                for(int j = 0; j < exerciseResult.get(i).ExerciseImageL.size(); j++) {
                    System.out.println("exerciseResult Image: " + exerciseResult.get(i).ExerciseImageL.get(j).URLimg);
                }
                for(int k = 0; k < eList.get(i).StepL.size(); k++) {
                    System.out.println("exerciseResult STEP: " + eList.get(i).StepL.get(k).StepDes);
                }
            }
            listener.OnExerciseImageLoaderFinListener(exerciseResult);
        }
    }

    public String saveProgram(String url, String username, SportProgram programN)
    {
        Log.d("tag","SaveProgram Test Program: " + programN.getProgram() + " Title: " + programN.getTitle());
        System.out.println("DETAILFRAGMENT!!! ID: " + programN.getIDsport() + "\nSport: " + programN.getSport() + "\nGoal: " + programN.getGoal()
                + "\nTitle: " + programN.getTitle() + "\nProgram: " + programN.getProgram() + "\nIntensity: " + programN.getIntensity()
                + "\nHrsPerDay: " + programN.getHrsPerDay() + "\nSessionsPerDay: " + programN.getSessionsPerDay() + "\n");
        for (int l = 0; l < programN.getExercises().size(); l++) {
            eList.add(programN.getExercises().get(l));
            System.out.println("EXERCISES TEST: " + programN.getExercises().get(l).getName());
            for (int j = 0; j < programN.getExercises().get(l).ExerciseImageL.size(); j++) {
                System.out.println("IMAGE TESTING initial" + programN.getExercises().get(l).ExerciseImageL.get(j).URLimg.toString());
            }
        }
        InputStream inputStream = null;
        String result = "";
        for (int i = 0; i < eList.size(); i++) {
            for (int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {

                System.out.println("IMAGEBEFORE" + eList.get(i).ExerciseImageL.get(j).URLimg.toString());
            }
        }
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //make PUT request to this url
            HttpPut httpPut = new HttpPut(url);
            String json = "";
            String pNew = programN.getProgram();
            pNew = Normalizer.normalize(pNew, Normalizer.Form.NFD);
            String normalizedProgram = pNew.replaceAll("[^\\x00-\\x7F]", "");
            programN.setProgram(normalizedProgram);
            JSONObject jsonObject = new JSONObject();
            Gson gson = new Gson();
            jsonObject.put("Username:", username);
            String t = jsonObject.toString();
            json = gson.toJson(programN) + t;
            System.out.println("JSON: " + json);
            //set json to StringEntity
            StringEntity se = new StringEntity(json);
            //set HttpPot entity
            httpPut.setEntity(se);
            //set some headers to inform server about the type of content
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setHeader("Authorization", "Bearer " + accessToken);
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

    private class ProgramAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
           String url = (String) params[0];
           //username = (String) params[1];
           programN = (SportProgram) params[1];
            System.out.println("Username test doInBackground: " + username);
            //System.out.println("Program test doInBackground: " + programN.Program + " Title: " + programN.Title);
            return saveProgram(url, username,  programN);//u);
        }

        protected void onPostExecute(String result) {
            if(result == null) {
                Toast.makeText(getActivity().getBaseContext(), "This Program has already been added to your Profile", Toast.LENGTH_LONG).show();
            }
            else{
                Bundle b = new Bundle();
                b.putParcelable("program", programN);
                b.putBoolean("userShow", show);
                b.putString("username", username);
                //Toast.makeText(getActivity().getBaseContext(), exerciseText.getText().toString() + " Clicked!", Toast.LENGTH_LONG).show();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                final ProgramAddSuccessDialog dialog = new ProgramAddSuccessDialog();
                dialog.setArguments(b);
                dialog.show(manager, "MyDialog");
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        timer.cancel();
                    }
                }, 1000);
            }
            System.out.println("Result: " + result);
            for (int i = 0; i < eList.size(); i++) {
                for (int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {
                    System.out.println("IMAGET AFTER" + eList.get(i).ExerciseImageL.get(j).URLimg.toString());
                }
            }
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

    public static Bitmap getBitmap(String URLsrc)
    {
        try {
            URL url = new URL(URLsrc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(IOException e)
        {
            return null;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trainingprogram_detail, container, false);
        Socialize.onCreate(getActivity(), savedInstanceState);
        myContext = getActivity();
        // Your entity key. May be passed as a Bundle parameter to your activity
        String entityKey = programN.getTitle();

        // Create an entity object including a name
        // The Entity object is Serializable, so you could also store the whole object in the Intent
        Entity entity = Entity.newInstance(entityKey, "Socialize");

        ActionBarOptions options = new ActionBarOptions();
        // Wrap your existing view with the action bar.
        // your_layout refers to the resource ID of your current layout.
        View actionBarWrapped = ActionBarUtils.showActionBar(getActivity(), rootView, entity, options, new ActionBarListener() {

            @Override
            public void onCreate(ActionBarView actionBar) {

                actionBar.setOnActionBarEventListener(new OnActionBarEventListener() {

                    @Override
                    public void onUpdate(ActionBarView actionBar) {
                        // Called when the action bar has its data updated
                    }

                    @Override
                    public void onPostUnlike(ActionBarView actionBar) {
                        // Called AFTER a user has removed a like
                    }

                    @Override
                    public void onPostShare(ActionBarView actionBar, Share share) {
                        // Called AFTER a user has posted a share
                    }

                    @Override
                    public void onPostLike(ActionBarView actionBar, Like like) {
                        // Called AFTER a user has posted a like
                        Bundle b = new Bundle();
                        b.putParcelable("program", programN);
                        b.putString("username", username);
                        b.putBoolean("userShow", show);
                        //Toast.makeText(getActivity().getBaseContext(), exerciseText.getText().toString() + " Clicked!", Toast.LENGTH_LONG).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        final LikedDialog dialog = new LikedDialog();
                        dialog.setArguments(b);
                        dialog.show(manager, "MyDialog");
                        final Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                timer.cancel();
                            }
                        }, 1000);
                    }

                    @Override
                    public void onLoad(ActionBarView actionBar) {
                        // Called when the action bar is loaded
                    }

                    @Override
                    public void onLoadFail(Exception error) {
                        // Called when the action bar load failed
                    }

                    @Override
                    public void onGetLike(ActionBarView actionBar, Like like) {
                        // Called when the action bar retrieves the like for the
                        // current user
                    }

                    @Override
                    public void onGetEntity(ActionBarView actionBar, Entity entity) {
                        // Called when the action bar retrieves the entity data
                    }

                    @Override
                    public boolean onClick(ActionBarView actionBar, OnActionBarEventListener.ActionBarEvent evt) {
                        // Called when the user clicks on the action bar
                        // Return true to indicate you do NOT want the action to continue
                        return false;
                    }
                });
            }
        });
        System.out.println("Username test onCreateView: " + username);
        TextView detailTitle = (TextView) rootView.findViewById(R.id.trainingprogram_detail_title);
        TextView durationTitle = (TextView) rootView.findViewById(R.id.trainingprogram_duration_title);
        TextView durationInfo = (TextView) rootView.findViewById(R.id.trainingprogram_duration_info);
        TextView exercisesTitle = (TextView) rootView.findViewById(R.id.trainingprogram_exercises);
        TextView exerciseHint = (TextView) rootView.findViewById(R.id.trainingprogram_hint);
        if (programN != null) {
            detailTitle.setText(programN.getTitle());
            TextView programD = (TextView) rootView.findViewById(R.id.trainingprogram_detail);
            programD.setText(programN.getProgram());
            programD.setTextSize(18);
            durationTitle.setText(getResources().getString(R.string.durTitle));
            durationInfo.setText(programN.getDurationInfo());
            durationInfo.setTextSize(18);
            exercisesTitle.setText(getResources().getString(R.string.exerTitle));
            exerciseHint.setText(getResources().getString(R.string.exerHint));
            exerciseHint.setTextSize(18);
            final LinearLayout l = (LinearLayout) rootView.findViewById(R.id.linearDetail);
            for(int i = 0; i < programN.getExercises().size(); i++)
            {
                final TextView exerciseText = new TextView(getActivity().getBaseContext());
                LinearLayout.LayoutParams layoutExer = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutExer.setMargins(28,0,0,0);
                exerciseText.setLayoutParams(layoutExer);
                exerciseText.setText(programN.getExercises().get(i).getName());
                exerciseText.setTextSize(18);
               // exerciseText.setTextColor(0);
                exerciseText.setPadding(0, 4, 0, 0);
                l.addView(exerciseText);
               // layoutExer.setMargins(20,0,0,0);
                exerciseText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MyProgressDialog myProgressDialog = new MyProgressDialog(getActivity(), R.drawable.progress_dialog_img);
                        myProgressDialog.show();
                        new Thread(new Runnable() {
                            public void run() {
                                try {

                                    Thread.sleep(10000);
                                } catch (Exception e) {

                                }
                                Bundle args = new Bundle();
                                for (int i = 0; i < eList.size(); i++) {
                                    for (int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {
                                        System.out.println("ONCLICK ELIST SIZE: " + eList.size());
                                        System.out.println("ONCLICK ELIST IMAGEL SIZE: " + eList.get(i).ExerciseImageL.size());
                                        System.out.println("IMAGET" + eList.get(i).ExerciseImageL.get(j).URLimg.toString());
                                        // exerImg = eList.get(i).ExerciseImageL.get(i).Image;
                                    }
                                }
                                for (int i = 0; i < eList.size(); i++) {
                                    System.out.println("exerciseResult TP NAme: " + eList.get(i).getName());
                                    for (int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {
                                        System.out.println("exerciseResult TP Image: " + eList.get(i).ExerciseImageL.get(j).URLimg);
                                    }
                                    for (int k = 0; k < eList.get(i).StepL.size(); k++) {
                                        System.out.println("exerciseResult TP STEPL: " + eList.get(i).StepL.get(k).StepDes);
                                    }
                                }
                                if (getArguments().containsKey("userShow")) {
                                    args.putBoolean("userShow", show);
                                }
                                args.putString("exercise", exerciseText.getText().toString());
                                args.putParcelableArrayList("exerciseList", eList);
                                //Toast.makeText(getActivity().getBaseContext(), exerciseText.getText().toString() + " Clicked!", Toast.LENGTH_LONG).show();
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                ExerciseSteps dialog = new ExerciseSteps();
                                dialog.setArguments(args);
                                dialog.show(manager, "MyDialog");
                                myProgressDialog.dismiss();
                            }
                        }).start();
                    }
                });
            }

            Button savePrg = new Button(getActivity().getBaseContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            savePrg.setGravity(Gravity.BOTTOM);
            layoutParams.setMargins(28, 10, 0, 0);
            savePrg.setLayoutParams(layoutParams);
            savePrg.setText(getResources().getString(R.string.addP));//"+");
            savePrg.setBackgroundColor(getResources().getColor(R.color.btnBackground));
            l.addView(savePrg);
            savePrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ProgramAsyncTask().execute(rest.getREST_SAVE_PROGRAM() + username, programN);
                    for (int i = 0; i < programN.getExercises().size(); i++) {
                        System.out.println("[i]: " + i + programN.getExercises().get(i).getName());
                    }
                }
            });
            TextView ref = new TextView(getActivity().getBaseContext());
            ref.setText(getResources().getString(R.string.refsTitle));
            ref.setGravity(Gravity.BOTTOM);
            ref.setLayoutParams(layoutParams);
            ref.setTextColor(getResources().getColor(R.color.black));
            ref.setTextSize(18);
            l.addView(ref);

            TextView refD = new TextView(getActivity().getBaseContext());
            refD.setGravity(Gravity.BOTTOM);
            refD.setLayoutParams(layoutParams);
            refD.setTextColor(getResources().getColor(R.color.black));
            l.addView(refD);
        }
        else{
            ((TextView)  rootView.findViewById(R.id.trainingprogram_detail)).setText("Welcome to Train Droid");
        }

        return actionBarWrapped;
    }
}

