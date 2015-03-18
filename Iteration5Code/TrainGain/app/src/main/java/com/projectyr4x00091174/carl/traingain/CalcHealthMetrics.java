package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CalcHealthMetrics extends ExpandableListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_health_metrics);

        ExampleAdapter adapter = new ExampleAdapter(this, getLayoutInflater());
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calc_health_metrics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent2 = getIntent();
            Bundle extras = intent2.getExtras();
            String userName = extras.getString("username");
            String access_token = extras.getString("accessToken");
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtras(extras);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
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
    EditText heartRateAge;
    EditText heartMaxResult;
    EditText heartRangeResult;
    Button calcHeartRate;

    Context context;
    LayoutInflater layoutInflater;
    public ExampleAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
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
        /*
        if(groupPosition == 0) {
            v = View.inflate(context, R.layout.expandable_child_layout, null);
            TextView txtView = (TextView) v.findViewById(R.id.txtChld1);
            txtView.setText("Green");
            txtView.setTextSize(15f);
            //txtView.setBackgroundColor(Color.GREEN);
        }
        */
        if(groupPosition == 0) {
            v = View.inflate(context, R.layout.child_1_bmi_layout, null);
            weight = (EditText) v.findViewById(R.id.editTextWeight);
            heightFt = (EditText) v.findViewById(R.id.editTextHeightFt);
            heightIn = (EditText) v.findViewById(R.id.editTextHeightIn);
            bmiResult = (EditText) v.findViewById(R.id.ResultBmi);
            bmiResult.setFocusable(false);
            bmi = (Button) v.findViewById(R.id.calcBMI);
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
        }
        if(groupPosition == 1) {
            v = View.inflate(context, R.layout.child_2_metabolism_layout, null);
            BMRweight = (EditText) v.findViewById(R.id.weightBMR);
            BMRheightFt = (EditText) v.findViewById(R.id.heightFtBMR);
            BMRheightIn = (EditText) v.findViewById(R.id.heightInBMR);
            BMRage = (EditText) v.findViewById(R.id.ageBMR);
            male = (RadioButton) v.findViewById(R.id.radioButtonMale);
            female = (RadioButton) v.findViewById(R.id.radioButtonFemale);
            bmrResult = (EditText) v.findViewById(R.id.ResultBmi);
            bmrResult.setFocusable(false);
            bmr = (Button) v.findViewById(R.id.calcBMR);
            bmr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validateBMR()) {
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        BMR();
                    }
                }
            });
        }
        if(groupPosition == 2)
        {
            v = View.inflate(context, R.layout.child_3_calorie_intake_layout, null);
            bmrEdit = (EditText) v.findViewById(R.id.calorieBMI);
            dropdownCalories = (Spinner) v.findViewById(R.id.spinnerCal);
            calories = (Button) v.findViewById(R.id.calcCalories);
            calorieResult = (EditText) v.findViewById(R.id.resultCalories);
            calorieResult.setFocusable(false);
            String[] activityLevel = {"Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extra Active"};
            ArrayAdapter<String> adapterActivityLevel = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, activityLevel);
            dropdownCalories.setAdapter(adapterActivityLevel);
            calories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!validateCalorieIntake()){
                        Toast.makeText(context, "Please Enter Your BMI", Toast.LENGTH_LONG).show();
                    }else{
                        calorieIntake();
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
                            else if (dropdownCalories.getSelectedItem().equals("Lightly Active")) {
                                ActivityLevel = "Lightly Active";
                            } else if (dropdownCalories.getSelectedItem().equals("Moderately Active")) {
                                ActivityLevel = "Moderately Active";
                            } else if (dropdownCalories.getSelectedItem().equals("Very Active")) {
                                ActivityLevel = "Very Active";
                            }
                            else if (dropdownCalories.getSelectedItem().equals("Extra Active")) {
                                ActivityLevel = "Extra Active";
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
            heartRateAge = (EditText) v.findViewById(R.id.ageHeartRate);
            heartMaxResult = (EditText) v.findViewById(R.id.resultMaxRate);
            heartMaxResult.setFocusable(false);
            heartRangeResult = (EditText) v.findViewById(R.id.resultTargetRate);
            heartRangeResult.setFocusable(false);
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
        }



        /*
        if(groupPosition == 3) {
            v = View.inflate(context, R.layout.expandable_child_layout, null);
            TextView txtView = (TextView) v.findViewById(R.id.txtChld1);
            txtView.setText("Purple");
            txtView.setTextSize(15f);

        }
        */
        v.invalidate();
        return v;
    }
/*
    public void onClick(View view)
    {
        switch ((view.getId()))
        {
            case R.id.calcBMI:
                bmi();
                break;
            case R.id.calcBMR:
                BMR();
                break;
        }
    }
*/
    public void bmi() {
        try {
            double userWeightLb = Double.parseDouble(weight.getText().toString());
            double userHeightFt = Double.parseDouble(heightFt.getText().toString());
            double userHeightIn = Double.parseDouble(heightIn.getText().toString());
            double heightResult = (userHeightFt * 12) + userHeightIn;
            double heightSquared = Math.pow(heightResult, 2);
            double bmi = (userWeightLb / heightSquared) * 703;
            String result = String.valueOf(bmi);
            bmiResult.setText(result);
        } catch (NumberFormatException n) {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
    }

    public void BMR() {
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
        String result = String.valueOf(bmrWeight);
        bmrResult.setText(result);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
    }

    public void calorieIntake() {
        try {
            double bmi = Double.parseDouble(bmrEdit.getText().toString());
            if (ActivityLevel.equals("Sedentary")) {
                bmi = bmi * 1.2;
            } else if (ActivityLevel.equals("Lightly Active")) {
                bmi = bmi * 1.375;
            } else if (ActivityLevel.equals("Moderately Active")) {
                bmi = bmi * 1.55;
            } else if (ActivityLevel.equals("Very Active")) {
                bmi = bmi * 1.725;
            } else if (ActivityLevel.equals("Extra Active")) {
                bmi = bmi * 1.9;
            }
            String result = String.valueOf(bmi);
            calorieResult.setText(result);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
    }

    public void maxHeartRate()
    {
        try {
            double age = Double.parseDouble(heartRateAge.getText().toString());
            double maxRate = 220 - age;
            String max = String.valueOf(maxRate);
            heartMaxResult.setText(max);
            double range1 = maxRate / 2;
            double range2 = (maxRate / 20) * 17;
            String targetRange1 = String.valueOf(range1);
            String targetRange2 = String.valueOf(range2);
            String targetRangeOverall = targetRange1 + "-" + targetRange2;
            heartRangeResult.setText(targetRangeOverall);
        }catch(NumberFormatException n)
        {
            Toast.makeText(context, "Please Enter a Number", Toast.LENGTH_LONG).show();
        }
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView.inflate(context, R.layout.expandable_group_layout, null);
        TextView txtView = (TextView) v.findViewById(R.id.txt1);
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

}
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc_health_metrics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/
