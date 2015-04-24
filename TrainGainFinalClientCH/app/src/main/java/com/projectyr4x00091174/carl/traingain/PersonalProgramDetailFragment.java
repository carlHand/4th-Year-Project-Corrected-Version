package com.projectyr4x00091174.carl.traingain;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A fragment representing a single PersonalProgram detail screen.
 * This fragment is either contained in a {@link PersonalProgramListActivity}
 * in two-pane mode (on tablets) or a {@link PersonalProgramDetailActivity}
 * on handsets.
 */
public class PersonalProgramDetailFragment extends Fragment implements OnExerciseImageLoaderFinListener {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonalProgramDetailFragment() {
    }

    SportProgram programN;
    SportProgram pTemp;
    ApplicationUser p = new ApplicationUser();
    Exercise eTemp;
    ArrayList<SportProgram> programLTemp;
    String username;
    String accessToken;
    String strengthGoal;
    String strengthTitle;
    Bundle extras;
    boolean userShow;
    private final Handler handler = new Handler();
    Handler dialogHandler;
    private Runnable timer1;
    ArrayList<Exercise> Exercises = new ArrayList<Exercise>();
    ArrayList<Exercise> eList;
    List<ExerciseImage> eImgList;
    ArrayList<Exercise> exerTemp;
    RESTurl rest = new RESTurl();
    HttpAsyncTask1 httpAsyncTask1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogHandler = new Handler();
        extras = getActivity().getIntent().getExtras();
        if (getArguments().containsKey("program")) {
            programN = extras.getParcelable("program");
            programLTemp = new ArrayList<SportProgram>();
            eList = new ArrayList<Exercise>();
            eImgList = new ArrayList<ExerciseImage>();
            exerTemp = new ArrayList<Exercise>();
            System.out.println("DETAILFRAGMENT!!! ID: " + programN.getIDsport() + "\nSport: " + programN.getSport() + "\nGoal: " + programN.getGoal()
                    + "\nTitle: " + programN.getTitle() + "\nProgram: " + programN.getProgram() + "\nIntensity: " + programN.getIntensity()
                    + "\nHrsPerDay: " + programN.getHrsPerDay() + "\nSessionsPerDay: " + programN.getSessionsPerDay() + "\n");
            for(int i = 0; i < programN.getExercises().size(); i++)
            {
                eList.add(programN.getExercises().get(i));
                pTemp = new SportProgram();
                pTemp.setIDsport(programN.getIDsport());
                pTemp.setSport(programN.getSport());
                pTemp.setGoal(programN.getGoal());
                pTemp.setTitle(programN.getTitle());
                pTemp.setProgram(programN.getProgram());
                pTemp.setIntensity(programN.getIntensity());
                pTemp.setHrsPerDay(programN.getHrsPerDay());
                pTemp.setSessionsPerDay(programN.getSessionsPerDay());
                pTemp.setGenre(programN.getGenre());
                pTemp.setPreTrainingNotes(programN.getPreTrainingNotes());
                pTemp.setDurationInfo(programN.getDurationInfo());
                for(int j = 0; j < programN.getExercises().size(); j++) {
                    eTemp = new Exercise();
                    eTemp.setIDexercise(programN.getExercises().get(j).getIDexercise());
                    eTemp.setName(programN.getExercises().get(j).getName());
                    eTemp.setLiftValue(programN.getExercises().get(j).getLiftValue());
                    eTemp.setDateLogged(programN.getExercises().get(j).getDateLogged());

                    exerTemp.add(eTemp);
                }
                pTemp.setExercises(exerTemp);
                System.out.println("EXERCISES TEST: " + programN.getExercises().get(i).getName());

            }
            Log.v("tpdf", "hit if, arguments contains program");
        }
        else{
            Log.v("No program", "argument does not have key");
        }
        if(getArguments().containsKey("username"))
        {
            username = extras.getString("username");
            System.out.println("TrainingProgramDetailFragment Test userName: " + username);
        }
        else{
            Log.v("No Email", "argument does not have key");
        }
        if(getArguments().containsKey("accessToken"))
        {
            accessToken = extras.getString("accessToken");
            System.out.println("TrainingProgramDetailFragment Test access_token: " + accessToken);
        }
        else{
            Log.v("No access_token", "argument does not have key");
        }
        if(getArguments().containsKey("userShow"))
        {
            userShow = extras.getBoolean("userShow");
            System.out.println("Contains Usershow in Pdetail" + userShow);
        }
        else
        {
            Log.v("No userShow", "argument does not contain keys");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final OnExerciseImageLoaderFinListener cb = this;
        timer1 = new Runnable() {
            @Override
            public void run() {
                String u = rest.getREST_GET_EXERCISE_IMAGES() + programN.getTitle();
                URI uri = null;
                try {
                    uri = new URI(u.replaceAll(" ", "%20"));
                    System.out.println("TITLE!!!!!!!!: " + uri.toString());
                    for (int k = 0; k < eList.size(); k++) {
                        if (eList.get(k).ExerciseImageL.isEmpty()) {
                            httpAsyncTask1 = new HttpAsyncTask1();
                            httpAsyncTask1.setOnExerciseImageLoaderFinListener(cb);
                            httpAsyncTask1.execute(uri.toString());
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

        };
        handler.postDelayed(timer1, 100);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(timer1);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_personalprogram_detail, container, false);

        TextView detailTitle = (TextView) rootView.findViewById(R.id.personalprogram_detail_title);
        TextView durationTitle = (TextView) rootView.findViewById(R.id.personalprogram_duration_title);
        TextView durationInfo = (TextView) rootView.findViewById(R.id.personalprogram_duration_info);
        ScrollView personalScroll = (ScrollView) rootView.findViewById(R.id.programScroll);
        TextView exercisesTitle = (TextView) rootView.findViewById(R.id.personalprogram_exercises);
        TextView exerciseHint = (TextView) rootView.findViewById(R.id.personalprogram_hint);
        System.out.println("ID: " + programN.getIDsport() + "\nSport: " + programN.getSport() + "\nGoal: " + programN.getGoal()
                + "\nTitle: " + programN.getTitle() + "\nProgram: " + programN.getProgram() + "\nIntensity: " + programN.getIntensity()
                + "\nHrsPerDay: " + programN.getHrsPerDay() + "\nSessionsPerDay: " + programN.getSessionsPerDay());

        strengthGoal = "Strength";
        strengthTitle = "Program for Strength";

        // Show the dummy content as text in a TextView.
        if (programN != null) {
            TextView programD = (TextView) rootView.findViewById(R.id.personalprogram_detail);
            programD.setText(programN.getProgram());
            programD.setTextSize(18);
            detailTitle.setText(programN.getTitle());
            //((TextView) rootView.findViewById(R.id.personalprogram_detail)).setText(programN.getProgram());
            durationTitle.setText(getResources().getString(R.string.durTitle));
            durationInfo.setText(programN.getDurationInfo());
            durationInfo.setTextSize(18);
            exercisesTitle.setText(getResources().getString(R.string.exerTitle));
            exerciseHint.setText(getResources().getString(R.string.exerHint));
            exerciseHint.setTextSize(18);
            final LinearLayout l = (LinearLayout) rootView.findViewById(R.id.personalLinear);
            for(int i = 0; i < programN.getExercises().size(); i++) {
                final TextView exerciseText = new TextView(getActivity().getBaseContext());
                LinearLayout.LayoutParams layoutExer = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutExer.setMargins(28, 0, 0, 0);
                exerciseText.setText(programN.getExercises().get(i).getName());
                exerciseText.setLayoutParams(layoutExer);
                exerciseText.setTextSize(18);
                exerciseText.setPadding(0, 2, 0, 0);
                l.addView(exerciseText);

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
                                    }
                                }
                                args.putString("exercise", exerciseText.getText().toString());
                                args.putParcelableArrayList("exerciseList", eList);
                                args.putString("username", username);
                                args.putString("accessToken", accessToken);
                                args.putParcelable("program", programN);
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
            LinearLayout ll = new LinearLayout(getActivity().getBaseContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);

            Button savePrg = new Button(getActivity().getBaseContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            savePrg.setGravity(Gravity.BOTTOM);
            layoutParams.setMargins(28,20,0,0);
            savePrg.setLayoutParams(layoutParams);
            savePrg.setWidth(110);
            savePrg.setText(getResources().getString(R.string.addP));
            savePrg.setBackgroundColor(getResources().getColor(R.color.btnBackground));
            ll.addView(savePrg);
            savePrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    String pr = programN.getTitle();
                    Intent intent = new Intent(getActivity(), ProgramResults.class);
                    bundle.putParcelable("program", pTemp);
                    bundle.putString("username", username);
                    bundle.putBoolean("userShow", userShow);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            Button completePrg = new Button(getActivity().getBaseContext());
            completePrg.setGravity(Gravity.BOTTOM);
            layoutParams.setMargins(5, 0, 0, 0);
            completePrg.setLayoutParams(layoutParams);
            completePrg.setWidth(110);
            completePrg.setText("Delete");
            completePrg.setBackgroundColor(getResources().getColor(R.color.btnBackground));
            ll.addView(completePrg);
            l.addView(ll);
            completePrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(getActivity()).setTitle("Delete Program").setMessage("Are you sure you want to delete this program?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String u =  rest.getREST_DELETE_USER_PROGRAM() + username + "/" + programN.getTitle();
                            URI uri = null;
                            try {
                                uri = new URI(u.replaceAll(" ", "%20"));
                                System.out.println("TITLE!!!!!!!!: " + uri.toString());
                                new HttpAsyncTaskDeleteProgram().execute(uri.toString());
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
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
            ((TextView)  rootView.findViewById(R.id.personalprogram_detail)).setText("Welcome to Train Droid");
        }
        return rootView;
    }

    @Override
    public ArrayList<Exercise> OnExerciseImageLoaderFinListener(ArrayList<Exercise> results) {
        return results;
    }

    public String DeleteUserProgram(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(URL);
        String s = null;
        int statusCode = 0;
        String result = "";
        try {
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpDelete);
            StatusLine statusLine = response.getStatusLine();
            statusCode = statusLine.getStatusCode();
            System.out.println("STATUS CODE: " + statusCode);
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
            Log.d("getUserProfileFeed", e.getLocalizedMessage());
        }
        s = Integer.toString(statusCode);
        return s;
       // return result;
    }

    private class HttpAsyncTaskDeleteProgram extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        Context context;

        private OnEditProfileListener listener;
        public void setOnEditProfileListener(OnEditProfileListener listener)
        {
            this.listener = listener;
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {

            return DeleteUserProgram(urls[0]);
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            int i = Integer.parseInt(result);
            if (i == 200) {
                Bundle b = new Bundle();
                b.putParcelable("program", programN);
                b.putString("username", username);
                //Toast.makeText(getActivity().getBaseContext(), exerciseText.getText().toString() + " Clicked!", Toast.LENGTH_LONG).show();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                final ProgramDeleted dialog = new ProgramDeleted();
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
               // getActivity().finish();
            }
            else{
                Toast.makeText(getActivity(), "Error please try again", Toast.LENGTH_LONG).show();
            }
                System.out.println("RESULT DELETE: " + result);
        }
    }



    private class HttpAsyncTask1 extends AsyncTask<String, Void, ArrayList<Exercise>>
    {
        private OnExerciseImageLoaderFinListener listener;
        public void setOnExerciseImageLoaderFinListener(OnExerciseImageLoaderFinListener listener)
        {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Exercise> doInBackground(String... urls) {
            String result =  getExerImages(urls[0]);
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

    public String getExerImages(String URL) {
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
}
