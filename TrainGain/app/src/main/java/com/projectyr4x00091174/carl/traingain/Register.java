package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class Register extends Activity {
    private RegisterTask myRegisterTask = null;
    private Person myPerson = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText pName;
    private EditText pSport;
    private RadioButton pMale;
    private RadioButton pFemale;
    private EditText pWeight;
    private EditText pHeight;
    private EditText pDateOfBirth;
    private RadioButton pActivityLow;
    private RadioButton pActivityMedium;
    private RadioButton pActivityHigh;
    private Button registerB;
    MyHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        pName = (EditText) findViewById(R.id.personName);
        pSport = (EditText) findViewById(R.id.sport);
        pMale = (RadioButton) findViewById(R.id.radioButtonMale);
        pFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        pWeight = (EditText) findViewById(R.id.weight);
        pHeight = (EditText) findViewById(R.id.height);
        pDateOfBirth = (EditText) findViewById(R.id.dob);
        pActivityLow = (RadioButton) findViewById(R.id.radioButtonActivityLow);
        pActivityMedium = (RadioButton) findViewById(R.id.radioButtonActivityMedium);
        pActivityHigh = (RadioButton) findViewById(R.id.radioButtonActivityHigh);
        registerB = (Button) findViewById(R.id.register);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }

    public void attemptLogin() {
        if (myPerson != null) {
            return;

        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = pName.getText().toString();
        String sport = pSport.getText().toString();
        String gender = " ";
        if (pMale.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        double weight = Double.parseDouble(pWeight.getText().toString());
        int height = Integer.parseInt(pHeight.getText().toString());
        String pDob = pDateOfBirth.getText().toString();
        String activityLevel = " ";
        if (pActivityLow.isChecked()) {
            activityLevel = "Low";
        } else if (pActivityMedium.isChecked()) {
            activityLevel = "Medium";
        } else {
            activityLevel = "High";
        }
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            //        showProgress(true);
            myRegisterTask = new RegisterTask(name, email, password, pDob, weight, height, sport,  gender, activityLevel, this);
            myRegisterTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if(email.contains("@")) {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if(password.length() > 4) {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    public void RegisterUser(View view) {
        //MyHelper dbTools = null;

        MyHelper dbTools=null;
            dbTools = new MyHelper(mContext);
            myPerson = dbTools.getUser(mEmailView.getText().toString());
        if (myPerson.userId < 0) {
            Message.message(this, "Unsuccessful");
        } else {
            Intent myIntent = new Intent(Register.this, HomePage.class);
            Register.this.startActivity(myIntent);
        }
/*
        myPerson.setName(pName.getText().toString());
        myPerson.setEmail(mEmailView.getText().toString());
        myPerson.setPassword(mPasswordView.getText().toString());
        myPerson.setAge(pDateOfBirth.getText().toString());
        if (pMale.isChecked()) {
            myPerson.setGender(pMale.getText().toString());
        } else {
            myPerson.setGender(pFemale.getText().toString());
        }
        myPerson.setWeight(Double.parseDouble(pWeight.getText().toString()));
        myPerson.setHeight(Integer.parseInt(pHeight.getText().toString()));
        if (pActivityLow.isChecked()) {
            myPerson.setActivityLevel(pActivityLow.getText().toString());
        } else if (pActivityMedium.isChecked()) {
            myPerson.setActivityLevel(pActivityMedium.getText().toString());
        } else {
            myPerson.setActivityLevel(pActivityHigh.getText().toString());
        }
        myPerson.setSport(pSport.getText().toString());
        //SQLiteDatabase db = myHelper.getWritableDatabase();
        myPerson = myHelper.insertUser(myPerson);
        if (myPerson.userId < 0) {
            Message.message(this, "Unsuccessful");
        } else {
            Intent myIntent = new Intent(Register.this, HomePage.class);
            Register.this.startActivity(myIntent);
        }
*/


    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mName;
        private final String mEmail;
        private final String mPassword;
        private final String mDob;
        private final double mWeight;
        private final double mHeight;
        private final String mSport;
        private final String mGender;
        private final String mActivityLevel;
        private final Context mContext;

        RegisterTask(String name, String email, String password, String dob, double weight, double height, String sport, String gender, String activityLevel, Context context)
        {
            mName = name;
            mEmail = email;
            mPassword = password;
            mDob = dob;
            mWeight = weight;
            mHeight = height;
            mSport = sport;
            mGender = gender;
            mActivityLevel = activityLevel;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            MyHelper dbTools = null;
            try {
                dbTools = new MyHelper(mContext);
                myPerson = dbTools.getUser(mEmail);


                    // Account does not exists, check password.
                   // Message.message(mContext, "This Account Already exists");
                    myPerson.setPassword(mPassword);
                    myPerson.setEmail(mEmail);
                    myPerson.setName(mName);
                    myPerson.setAge(mDob);
                    myPerson.setWeight(mWeight);
                    myPerson.setHeight(mHeight);
                    myPerson.setGender(mGender);
                    myPerson.setSport(mSport);
                    myPerson.setActivityLevel(mActivityLevel);
                if(isEmailValid(mEmail)) {
                    if (isPasswordValid(mPassword))
                        return true;
                    else
                        return false;
                } else {
                    return true;
                }
            } finally {
                if (dbTools != null)
                    dbTools.close();
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            myRegisterTask = null;
            //showProgress(false);

            if (success) {
                if (myPerson.userId > 0) {
                    //finish();
                    MyHelper dbTools = null;
                }
                    else
                    {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        MyHelper dbTools=null;
                                        try{
                        finish();
                        dbTools = new MyHelper(mContext);
                        myPerson = dbTools.insertUser(myPerson);
                        Toast myToast = Toast.makeText(mContext, R.string.updatingReport, Toast.LENGTH_SHORT);
                        myToast.show();
                        Intent myIntent = new Intent(Register.this, HomePage.class);
                        Register.this.startActivity(myIntent);
                    } finally {
                        if (dbTools != null)
                            dbTools.close();
                    }

                                    break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                                        mPasswordView.requestFocus();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                        builder.setMessage(R.string.complete_registration).setPositiveButton(R.string.yes, dialogClickListener)
                                .setNegativeButton(R.string.no, dialogClickListener).show();
                    }

                }
                  else {
                    Toast myToast = Toast.makeText(mContext, R.string.unsuccessful, Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }


        @Override
        protected void onCancelled() {
            myRegisterTask = null;
            //showProgress(false);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
