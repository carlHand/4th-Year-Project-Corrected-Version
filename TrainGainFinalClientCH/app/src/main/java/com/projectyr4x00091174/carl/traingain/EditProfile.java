package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

//Fragment where user can see the detail they registered with and also update certain personal attributes
public class EditProfile extends Fragment implements OnEditProfileListener{
    RESTurl rest = new RESTurl();                                       //object to call various connection Strings to database
    ImageView profPic;
    TextView userLabel;
    TextView userN;
    TextView emailLabel;
    AutoCompleteTextView emailN;
    TextView passwordLabel;
    EditText passwordN;
    TextView dobLabel;
    EditText dobN;
    TextView genderLabel;
    RadioButton m;
    RadioButton f;
    TextView bmiLabel;
    TextView bmiN;
    TextView bmrLabel;
    TextView bmrN;
    TextView heartLabel;
    TextView heartRange1;
    TextView heartRange2;
    TextView mHeartMaxLabel;
    TextView mHeartMaxEdit;
    TextView dash;
    TextView caloriesLabel;
    TextView caloriesN;
    Button saveB;
    Button logoutB;
    TextView toggleL;
    ToggleButton toggleB;
    String userName;
    String access_token;
    private final Handler mHandler = new Handler();                         //handle onResume event
    private Runnable mTimer1;
    ApplicationUser user;
    String userTemp;
    String emailTemp;
    String passwordTemp;
    double bmiTemp;
    double bmrTemp;
    double heartTempRange1;
    double heartTempRange2;
    double maxHeart;
    double caloriesTemp;
    String gender;
    boolean show;
    public EditProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new ApplicationUser();
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setHasOptionsMenu(true);
        userName = getArguments().getString("username");
        access_token = getArguments().getString("accessToken");
        System.out.println("EDIT TEST USERNAME: " + userName);
        System.out.println("EDIT TEST ACCESS_TOKEN: " + access_token);
        profPic = (ImageView) rootView.findViewById(R.id.profilePic);
        userLabel = (TextView) rootView.findViewById(R.id.userNid);
        userN = (TextView) rootView.findViewById(R.id.userNameT);
        emailLabel = (TextView) rootView.findViewById(R.id.userEmailText);
        emailN = (AutoCompleteTextView) rootView.findViewById(R.id.userEmailEdit);
        passwordLabel = (TextView) rootView.findViewById(R.id.passText);
        passwordN = (EditText) rootView.findViewById(R.id.passEdit);
        dobLabel = (TextView) rootView.findViewById(R.id.userDOBtext);
        dobN = (EditText) rootView.findViewById(R.id.dateEdit);
        genderLabel = (TextView) rootView.findViewById(R.id.genderText);
        m = (RadioButton) rootView.findViewById(R.id.radioButtonM);
        f = (RadioButton) rootView.findViewById(R.id.radioButtonF);
        bmiLabel = (TextView) rootView.findViewById(R.id.BMItext);
        bmiN = (TextView) rootView.findViewById(R.id.BMIedit);
        bmrLabel = (TextView) rootView.findViewById(R.id.BMRtext);
        bmrN = (TextView) rootView.findViewById(R.id.BMRedit);
        mHeartMaxLabel = (TextView) rootView.findViewById(R.id.heartMaxTextL);
        mHeartMaxEdit = (TextView) rootView.findViewById(R.id.heartMaxEdit);
        heartLabel = (TextView) rootView.findViewById(R.id.heartRangeText);
        heartRange1 = (TextView) rootView.findViewById(R.id.heartRangeEdit);
        heartRange2 = (TextView) rootView.findViewById(R.id.heartRangeEdit2);
        dash = (TextView) rootView.findViewById(R.id.dashText);
        caloriesLabel = (TextView) rootView.findViewById(R.id.caloriesText);
        caloriesN = (TextView) rootView.findViewById(R.id.calorieEdit);
        saveB = (Button) rootView.findViewById(R.id.saveEdit);
        logoutB = (Button) rootView.findViewById(R.id.logoutButton);
        toggleL = (TextView) rootView.findViewById(R.id.toggleLabel);
        toggleB = (ToggleButton) rootView.findViewById(R.id.toggleButtonHelp);
        gender = "";
        toggleB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    show = true;
                }
                else{
                    show = false;
                }
            }
        });
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTemp = userN.getText().toString();
                emailTemp = emailN.getText().toString();
                // String passwordTemp = passwordN.getText().toString();
                passwordTemp = "Password-1";
                try {
                    bmiTemp = Double.parseDouble(bmiN.getText().toString());
                }
                catch(NumberFormatException n)
                {
                    bmiTemp = 0.0;
                }
                try{
                    bmrTemp = Double.parseDouble(bmrN.getText().toString());
                }
                catch(NumberFormatException n)
                {
                    bmrTemp = 0.0;
                }
                try{
                    maxHeart = Double.parseDouble(mHeartMaxEdit.getText().toString());
                }
                catch(NumberFormatException n)
                {
                    maxHeart = 0.0;
                }
                try{
                    heartTempRange1 = Double.parseDouble(heartRange1.getText().toString());
                }
                catch(NumberFormatException n)
                {
                    heartTempRange1 = 0.0;
                }
                try{
                    heartTempRange2 = Double.parseDouble(heartRange2.getText().toString());
                }
                catch(NumberFormatException n)
                {
                    heartTempRange2 = 0.0;
                }
                try{
                    caloriesTemp = Double.parseDouble(caloriesN.getText().toString());
                }
                catch(NumberFormatException n){
                    caloriesTemp = 0.0;
                }
                if (m.isChecked()) {
                    gender = "Male";
                } else if (f.isChecked()) {
                    gender = "Female";
                }
                if(toggleB.isChecked())
                {
                    show = true;
                }
                else if(!toggleB.isChecked())
                {
                    show = false;
                }
                String dateTemp = dobN.getText().toString();
                DateFormat format;
                Date date = new Date();
                format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = format.parse(dateTemp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                user = new ApplicationUser(userTemp, emailTemp, passwordTemp, date, gender, show, heartTempRange1, heartTempRange2, maxHeart, bmiTemp, bmrTemp, caloriesTemp);
                new ProfileAsyncTask().execute(rest.getREST_UPDATE_USER_PROFILE(), user);
            }
        });
        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncTaskLogout().execute(rest.getREST_LOGOUT());
            }
        });
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
                break;
        }
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("username", userName);
            bundle.putString("access_token", access_token);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("").commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                mSeries1.resetData(generateData());
                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(mTimer1, 100);
*/

        final  HttpAsyncTaskUserProfile httpAsyncTaskUserProfile = new HttpAsyncTaskUserProfile();
        httpAsyncTaskUserProfile.setOnEditProfileListener(this);
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                 httpAsyncTaskUserProfile.execute(rest.getREST_EDIT_PROFILE() + userName);
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

    public static String Logout(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make POST request to this url
            HttpPost httpPost = new HttpPost(url);
            String json = "";


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

    private class HttpAsyncTaskLogout extends AsyncTask<String, Void, String> {

        MyProgressDialog dialog;
        protected void onPreExecute() {
            dialog = new MyProgressDialog(getActivity(), R.drawable.progress_dialog_img);
            dialog.setTitle("Warming Up...");
            dialog.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... urls) {
            return Logout(urls[0]);
        }

        protected void onPostExecute(String result) {
            Intent logOutIntent = new Intent(getActivity(), TestAdding.class);
            logOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logOutIntent);
            dialog.dismiss();
            System.out.println("RESULT LOGOUT: " + result);
        }
    }


    public String getUserProfile(String URL) {
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
            Log.d("getUserProfileFeed", e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public ApplicationUser OnEditProfileListener(ApplicationUser user) {
        return user;
    }

    private class HttpAsyncTaskUserProfile extends AsyncTask<String, Void, ApplicationUser> {

        private OnEditProfileListener listener;
        public void setOnEditProfileListener(OnEditProfileListener listener)
        {
            this.listener = listener;
        }

        MyProgressDialog dialog;
        Context context;
        protected void onPreExecute() {
            dialog = new MyProgressDialog(getActivity(), R.drawable.progress_dialog_img);
            dialog.setTitle("Warming Up...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected ApplicationUser doInBackground(String... urls) {
            String result = getUserProfile(urls[0]);
            boolean userShow = false;
            String trueString = "true";
            if (result != null) {
                System.out.println("CLEAR TEST");
                try {
                 //   if (user == null) {
                    JSONObject c = new JSONObject(result);
                    String userNameTemp = c.getString("UserName");
                    String email = c.getString("Email");
                    String password = c.getString("Password");
                    String gender = c.getString("Gender");
                    String userShowTemp = c.getString("ShowHelp");
                    if (userShowTemp.toUpperCase().equals(trueString.toUpperCase())) {
                        userShow = true;
                        System.out.println("Hit if: " + userShowTemp);
                    } else {
                        System.out.println("Hit else: " + userShowTemp);
                        userShow = false;
                    }
                    String BMItemp = c.getString("BMI");
                    double BMI = Double.parseDouble(BMItemp);
                    String BMRtemp = c.getString("BMR");
                    double BMR = Double.parseDouble(BMRtemp);
                    String heartMaxTemp = c.getString("HeartMax");
                    double heartMax = Double.parseDouble(heartMaxTemp);
                    String range1Temp = c.getString("HeartRange1");
                    double range1 = Double.parseDouble(range1Temp);
                    String range2Temp = c.getString("HeartRange2");
                    double range2 = Double.parseDouble(range2Temp);
                        String caloriesTemp = c.getString("Calories");
                        double calories = Double.parseDouble(caloriesTemp);
                        String dateTemp = c.getString("Dob");
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                    try {
                        date = format.parse(dateTemp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("DATE TEST: "+ date);
                    System.out.println("username: " + userNameTemp + "\nemail: " + email + "\npassword: " + password + "\nGender: " + gender + "\nDOB: " + dateTemp + "\nDate: " + date + "\nBMI: " + BMI + "\nBMR: " + BMR + "\nheartRate: " + maxHeart + "\ncalories: " + calories);
                    user = new ApplicationUser(userNameTemp, email, password, date, gender, userShow, range1, range2, heartMax, BMI, BMR, calories);
                   // } else {
                     //   System.out.println("USER IS ALREADY USED");
                    //}
                }
                catch (JSONException j) {
                    j.printStackTrace();
                }
            } else {
                System.out.println("Error");
            }
            return user;
        }

        protected void onPostExecute(ApplicationUser user) {
            userN.setText(user.getUserName());
            emailN.setText(user.getEmail());
            passwordN.setText(user.getPassword());
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = user.getDob();
            String dateNew = formatter.format(date);
            System.out.println("POST USER GETDATE: " + user.getDob());
            System.out.println("POST dateNew: " + dateNew);
            dobN.setText(dateNew);
            String mGender = user.getGender();
            if(mGender.equals("Male"))
            {
                m.setChecked(true);
            }
            else
            {
                f.setChecked(true);
            }
            boolean userShow = user.getShowHelp();
            if(userShow == true)
            {
                toggleB.setChecked(true);
            }
            else
            {
                toggleB.setChecked(false);
            }
          //  String bmi = String.valueOf(user.getBMI());
            String bmi = String.format("%.0f", user.getBMI());
            bmiN.setText(bmi);
           // String bmr = String.valueOf(user.getBMR());
            String bmr = String.format("%.0f", user.getBMR());
            bmrN.setText(bmr);

            //String heartM = String.valueOf(user.getHeartMax());
            String heartM = String.format("%.0f", user.getHeartMax());
            mHeartMaxEdit.setText(heartM);
            //String range1M = String.valueOf(user.getHeartRange1());
            String range1M = String.format("%.0f", user.getHeartRange1());
            heartRange1.setText(range1M);
            //String range2M = String.valueOf(user.getHeartRange2());
            String range2M = String.format("%.0f", user.getHeartRange2());
            heartRange2.setText(range2M);
          //  String calories = String.valueOf(user.getCalories());
            String calories = String.format("%.0f", user.getCalories());
            caloriesN.setText(calories);
            dialog.dismiss();
            listener.OnEditProfileListener(user);
        }
    }

    private class ProfileAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            user = (ApplicationUser) params[1];
            return updateProfile(url, user);
        }
        protected void onPostExecute(String result) {
            if(result != null)
            {
                System.out.println("RESULT: " + result);
            }
        }
    }

    public String updateProfile(String url, ApplicationUser userN)
    {
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //make PUT request to this url
            HttpPut httpPut = new HttpPut(url);
            String json = "";
            Gson gson = new Gson();
            json = gson.toJson(userN);
            System.out.println("JSON: " + json);
            //set json to StringEntity
            StringEntity se = new StringEntity(json);
            //set HttpPost entity
            httpPut.setEntity(se);
            //set some headers to inform server about the type of content
            httpPut.setHeader("Content-type", "application/json");
            //httpPut.setHeader("Authorization", "Bearer " + accessToken);

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }
}
