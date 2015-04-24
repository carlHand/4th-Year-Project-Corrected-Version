package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.Normalizer;

//Fragment representing health metric calculator allowing users to determine their own values for the various measures listed below
public class CalcHealthMetrics extends Fragment {
    ExpandableListView expandableListView;
    Bundle extras;
    String userName;
    String access_token;
    ApplicationUser user;
    CheckBox showHelp;
    boolean show;
    RESTurl rest;


    public CalcHealthMetrics()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        rest = new RESTurl();
        if(getArguments().containsKey("username"))
        {
            userName = getArguments().getString("username");
        }
        if (getArguments().containsKey("userShow")) {
            show = getArguments().getBoolean("userShow");
            System.out.println("Program results has userShow" + show);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_calc_health_metrics, container, false);
        userName = getArguments().getString("username");
        access_token = getArguments().getString("accessToken");
        if (show == true) {
            System.out.println("IN CALCS TRUE");
            final Dialog myDialog = new Dialog(getActivity());
            myDialog.setContentView(R.layout.custom_help_calcs);
            TextView textView = (TextView) myDialog.findViewById(R.id.calcsInfo);
            showHelp = (CheckBox) myDialog.findViewById(R.id.showHide);
            Button myBtnOk = (Button) myDialog.findViewById(R.id.dialogBtn);
            myBtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showHelp.isChecked()) {
                        show = false;
                        new HelpAsyncTask().execute(rest.getREST_UPDATE_USER_HELP_OPTION() + "/" + userName + "/" + show);
                        myDialog.dismiss();
                    } else {
                        show = true;
                        myDialog.dismiss();
                    }
                }
            });
            myDialog.show();
        }
        user = new ApplicationUser();
        setHasOptionsMenu(true);
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListCalc);
        final ExampleAdapter adapter = new ExampleAdapter(getActivity().getBaseContext(), getActivity().getLayoutInflater(), getActivity().getSupportFragmentManager(), userName);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            public void onGroupExpand(int groupPosition) {
                int len = adapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        return rootView;
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
}

//Adapter enabling CalcHealthMetrics.class to perform its core functionality through the use of the various methods listed below
class ExampleAdapter implements ExpandableListAdapter {
    //BMI Layout
    EditText weight;
    EditText heightFt;
    EditText heightIn;
    EditText bmiResult;
    Button bmi;
    //BMR Layout
    EditText BMRweight;
    EditText BMRheightFt;
    EditText BMRheightIn;
    EditText BMRage;
    Spinner dropdown;
    RadioButton male;
    RadioButton female;
    EditText bmrResult;
    Button bmr;
    String ActivityLevel;
    //Calorie Intake Layout
    EditText bmrEdit;
    EditText calorieResult;
    Spinner dropdownCalories;
    Button calories;
    //Heart Rate Layout
    TextView heartRateAgeL;
    EditText heartRateAge;
    TextView maxL;
    EditText heartMaxResult;
    TextView rangeL;
    EditText heartRangeResult;
    Button calcHeartRate;
    Button save;
    Context context;
    LayoutInflater layoutInflater;
    private FragmentManager fragmentManager;
    String userName;
    ApplicationUser user;
    double myBMI;
    double myBMR;
    double myCaloreIntake;
    double myHeartRange1;
    double myHeartRange2;
    double myHeartMax;
    RESTurl rest = new RESTurl();           //Necessary to call various API Strings to connect to database
    public ExampleAdapter(Context context, LayoutInflater layoutInflater, FragmentManager fm, String userName) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.fragmentManager = fm;          //FragmentManager necessary to pass into constructor as I need to start a dialogFragment in getGroupView() method below
        this.userName = userName;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = null;
        user = new ApplicationUser();
        user.setUserName(userName);
        if(groupPosition == 0) {
            v = View.inflate(context, R.layout.child_1_bmi_layout, null);
            myBMI = 0;
            weight = (EditText) v.findViewById(R.id.editTextWeight);
            heightFt = (EditText) v.findViewById(R.id.editTextHeightFt);
            heightIn = (EditText) v.findViewById(R.id.editTextHeightIn);
            bmiResult = (EditText) v.findViewById(R.id.ResultBmi);
            bmiResult.setFocusable(false);
            bmi = (Button) v.findViewById(R.id.calcBMI);
            save = (Button) v.findViewById(R.id.saveBtn);
            bmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateBMI()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    }
                    else {
                        bmi();
                    }
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateBMI()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        myBMI = bmi();
                        if (myBMI != 0) {
                            user.setBMI(myBMI);
                            new StatsAsyncTask().execute(rest.getREST_UPDATE_BMI(), user);

                        }
                    }
                }
            });
        }
        if(groupPosition == 1) {
            v = View.inflate(context, R.layout.child_2_metabolism_layout, null);
            myBMR = 0;
            BMRweight = (EditText) v.findViewById(R.id.weightBMR);
            BMRheightFt = (EditText) v.findViewById(R.id.heightFtBMR);
            BMRheightIn = (EditText) v.findViewById(R.id.heightInBMR);
            BMRage = (EditText) v.findViewById(R.id.ageBMR);
            male = (RadioButton) v.findViewById(R.id.radioButtonMale);
            female = (RadioButton) v.findViewById(R.id.radioButtonFemale);
            bmrResult = (EditText) v.findViewById(R.id.ResultBmr);
            bmrResult.setFocusable(false);
            bmr = (Button) v.findViewById(R.id.calcBMR);
            save = (Button) v.findViewById(R.id.saveBMR);
            bmr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateBMR()) {                                                           //Validation
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        BMR();
                    }
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateBMR()) {                                                           //Validation
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        myBMR = BMR();
                        if (myBMR != 0) {
                            user.setBMR(myBMR);
                            new StatsAsyncTask().execute(rest.getREST_UPDATE_BMR(), user);
                        }
                    }
                }
            });
        }
        if(groupPosition == 2)
        {
            v = View.inflate(context, R.layout.child_3_calorie_intake_layout, null);
            myCaloreIntake = 0;
            bmrEdit = (EditText) v.findViewById(R.id.calorieBMI);
            dropdownCalories = (Spinner) v.findViewById(R.id.spinnerCal);
            calories = (Button) v.findViewById(R.id.calcCalories);
            calorieResult = (EditText) v.findViewById(R.id.resultCalories);
            calorieResult.setFocusable(false);
            save = (Button) v.findViewById(R.id.saveCalories);
            String[] activityLevel = {"Sedentary", "Mild", "Moderate", "Heavy", "Extreme"};
            ArrayAdapter<String> adapterActivityLevel = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, activityLevel);
            dropdownCalories.setAdapter(adapterActivityLevel);
            calories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateCalorieIntake()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        calorieIntake();
                    }
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateCalorieIntake()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        myCaloreIntake = calorieIntake();
                        if (myCaloreIntake != 0) {
                            user.setCalories(myCaloreIntake);
                            new StatsAsyncTask().execute(rest.getREST_UPDATE_CALORIES(), user);
                        }
                    }
                }
            });
            dropdownCalories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (parent.getId()) {
                        case R.id.spinnerCal:
                            if (dropdownCalories.getSelectedItem().equals("Sedentary")) {
                                ActivityLevel = "Sedentary";
                            }
                            else if (dropdownCalories.getSelectedItem().equals("Mild")) {
                                ActivityLevel = "Mild";
                            } else if (dropdownCalories.getSelectedItem().equals("Moderate")) {
                                ActivityLevel = "Moderate";
                            } else if (dropdownCalories.getSelectedItem().equals("Heavy")) {
                                ActivityLevel = "Heavy";
                            }
                            else if (dropdownCalories.getSelectedItem().equals("Extreme")) {
                                ActivityLevel = "Extreme";
                            }
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(groupPosition == 3) {
            v = View.inflate(context, R.layout.child_4_target_heart_rate_layout, null);
            myHeartMax = 0;
            myHeartRange1 = 0;
            myHeartRange2 = 0;
            heartRateAgeL = (TextView) v.findViewById(R.id.ageL);
            heartRateAge = (EditText) v.findViewById(R.id.ageHeartRate);
            maxL = (TextView) v.findViewById(R.id.maxRate);
            heartMaxResult = (EditText) v.findViewById(R.id.resultMaxRate);
            heartMaxResult.setFocusable(false);
            rangeL = (TextView) v.findViewById(R.id.targetRate);
            heartRangeResult = (EditText) v.findViewById(R.id.resultTargetRate);
            heartRangeResult.setFocusable(false);
            save = (Button) v.findViewById(R.id.saveHeartRate);
            calcHeartRate = (Button) v.findViewById(R.id.calcHeartRate);
            calcHeartRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateHeartRate()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        maxHeartRate();
                    }
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateHeartRate()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        myHeartRange1 = sendHeartRange1();
                        myHeartRange2 = sendHeartRange2();
                        myHeartMax = sendMaxHeartRate();
                        user.setHeartMax(myHeartMax);
                        user.setHeartRange1(myHeartRange1);
                        user.setHeartRange2(myHeartRange2);
                        new StatsAsyncTask().execute(rest.getREST_UPDATE_HEART_RATE(), user);
                    }
                }
            });
        }
        v.invalidate();
        return v;
    }

    public double bmi() {
        double bmi = 0;
        double bmiFormat = 0;
        try {
            double userWeightLb = Double.parseDouble(weight.getText().toString());
            double userHeightFt = Double.parseDouble(heightFt.getText().toString());
            double userHeightIn = Double.parseDouble(heightIn.getText().toString());
            //converting height in ft and In to centimetres
            double heightResult = (userHeightFt * 12) + userHeightIn;
            double heightSquared = Math.pow(heightResult, 2);
            bmi = (userWeightLb / heightSquared) * 703;
            bmi = Math.round(bmi);
            String resultNew = String.format("%.0f", bmi);
            bmiResult.setText(resultNew);
        } catch (NumberFormatException n) {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
        return bmi;
    }

    public double BMR() {
        double bmr = 0;
        try{
        double userWeightLb = Double.parseDouble(BMRweight.getText().toString());
        double userHeightFt = Double.parseDouble(BMRheightFt.getText().toString());
        double userHeightIn = Double.parseDouble(BMRheightIn.getText().toString());
        double heightResult = (userHeightFt * 12) + userHeightIn;
        double age = Double.parseDouble(BMRage.getText().toString());
        double bmrWeight = 0;
        if (male.isChecked()) {
            bmrWeight = 66 + (6.23 * userWeightLb) + (12.7 * heightResult) - (6.8 * age);
        } else if (female.isChecked()) {
            bmrWeight = 655 + (4.35 * userWeightLb) + (4.7 * heightResult) - (4.7 * age);
        }
            bmr = bmrWeight;
            bmr = Math.round(bmr);
            Math.floor(bmrWeight);
            String resultNew = String.format("%.0f", bmrWeight);
            bmrResult.setText(resultNew);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
        return bmr;
    }

    public double calorieIntake() {
        double calories = 0;
        try {
            double bmr = Double.parseDouble(bmrEdit.getText().toString());
            if (ActivityLevel.equals("Sedentary")) {
                bmr = bmr * 1.2;
            } else if (ActivityLevel.equals("Mild")) {
                bmr = bmr * 1.375;
            } else if (ActivityLevel.equals("Moderate")) {
                bmr = bmr * 1.55;
            } else if (ActivityLevel.equals("Heavy")) {
                bmr = bmr * 1.725;
            } else if (ActivityLevel.equals("Extreme")) {
                bmr = bmr * 1.9;
            }
            calories = bmr;
            calories = Math.round(calories);
            String resultNew = String.format("%.0f", calories);
            calorieResult.setText(resultNew);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
        return calories;
    }

    public void maxHeartRate()
    {
        try {
            double age = Double.parseDouble(heartRateAge.getText().toString());
            double maxRate = 220 - age;
            maxRate = Math.round(maxRate);
           // String max = String.valueOf(maxRate);
            String max = String.format("%.0f", maxRate);
            heartMaxResult.setText(max);
            double range1 = maxRate / 2;
            double range2 = (maxRate / 20) * 17;
            range1 = Math.round(range1);
            range2 = Math.round(range2);
            String targetRange1 = String.valueOf(range1);
            String targetRange2 = String.valueOf(range2);
            String targetRange1New = String.format("%.0f", range1);
            String targetRange2New = String.format("%.0f", range2);
            String targetRangeOverall = targetRange1New + "-" + targetRange2New;
            heartRangeResult.setText(targetRangeOverall);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
    }

    public double sendMaxHeartRate()
    {
        double maxRate = 0;
        try {
            double age = Double.parseDouble(heartRateAge.getText().toString());
            maxRate = 220 - age;
            maxRate = Math.round(maxRate);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
        return maxRate;
    }

    public double sendHeartRange1()
    {
        double range1 = 0;
        try {
            double age = Double.parseDouble(heartRateAge.getText().toString());
            double maxRate = 220 - age;
            range1 = maxRate / 2;
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
        range1 = Math.round(range1);
        return range1;
    }

    public double sendHeartRange2()
    {
        double maxRate = 0;
        double range2 = 0;
        try {
            double age = Double.parseDouble(heartRateAge.getText().toString());
            maxRate = 220 - age;
            range2 = (maxRate / 20) * 17;
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
        range2 = Math.round(range2);
        return range2;
    }

    private boolean validateBMI() {
        boolean empty = true;
        if(weight.getText().toString().trim().equals("")){
            empty = false;
        } if (heightFt.getText().toString().trim().equals("")) {
            empty = false;
        }if (heightIn.getText().toString().trim().equals("")) {
            empty = false;
        }
        return empty;
    }

    private boolean validateBMR() {
        if (BMRweight.getText().toString().trim().equals("")) {
            return false;
        } else if (BMRheightFt.getText().toString().trim().equals("")) {
            return false;
        } else if (BMRheightIn.getText().toString().trim().equals("")) {
            return false;
        }else if (BMRage.getText().toString().trim().equals("")) {
            return false;
        }else if(male.isChecked() && !female.isChecked()){
            return true;
        }else if(!male.isChecked() && female.isChecked()){
            return true;
        }else{
            return true;
        }
    }

    private boolean validateCalorieIntake() {
        if (bmrEdit.getText().toString().trim().equals("")) {
            return false;
        }else {
            return true;
        }
    }

    private boolean validateHeartRate() {
        if (heartRateAge.getText().toString().trim().equals("")) {
            return false;
        }else {
            return true;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return 4;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView.inflate(context, R.layout.expandable_group_layout, null);
        TextView txtView = (TextView) v.findViewById(R.id.txt1);
        final ImageButton info = (ImageButton) v.findViewById(R.id.infoButton);
        info.setFocusable(false);
        info.setFocusableInTouchMode(false);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupPosition == 0) {
                    BMIDialog dialog1 = new BMIDialog();
                    dialog1.show(fragmentManager, "MyDialog1");
                } else if (groupPosition == 1) {
                    BMRDialog dialog2 = new BMRDialog();
                    dialog2.show(fragmentManager, "MyDialog2");
                }
                else if (groupPosition == 2) {
                    CalorieDialog dialog3 = new CalorieDialog();
                    dialog3.show(fragmentManager, "MyDialog3");
                }
                else if (groupPosition == 3) {
                    HeartRateDialog dialog4 = new HeartRateDialog();
                    dialog4.show(fragmentManager, "MyDialog4");
                }
            }
        });

        if(groupPosition == 0) {
            txtView.setText("Body Mass Index");
            txtView.setTextSize(15f);
        }
        if(groupPosition == 1) {
            txtView.setText("Basic Metabolic Rate");
            txtView.setTextSize(15f);
        }
        if(groupPosition == 2) {
            txtView.setText("Calorie Intake");
            txtView.setTextSize(15f);
        }
        if(groupPosition == 3) {
            txtView.setText("Heart Rate");
            txtView.setTextSize(15f);
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

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    public String updateUserStats(String url, ApplicationUser mUser)
    {
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //make POST request to this url
            HttpPut httpPut = new HttpPut(url);
            String json = "";
            Gson gson = new Gson();
            json = gson.toJson(mUser);
            System.out.println("JSON: " + json);
            //set json to StringEntity
            StringEntity se = new StringEntity(json);
            //set HttpPut entity
            httpPut.setEntity(se);
            httpPut.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPut);
            //receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                result = inputStreamToString(inputStream);
            } else {
                result = "Failed";
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private class StatsAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            user = (ApplicationUser) params[1];
            return updateUserStats(url, user);
        }

        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();
            }
            System.out.println("Result: " + result);
        }
    }

    private static String inputStreamToString(InputStream myInputStream) throws IOException {
        BufferedReader myBufferedReader = new BufferedReader(new InputStreamReader(myInputStream));
        String myLine = "";
        String myResult = "";
        while ((myLine = myBufferedReader.readLine()) != null)
            myResult += myLine;
        myInputStream.close();
        return myResult;
    }
}
