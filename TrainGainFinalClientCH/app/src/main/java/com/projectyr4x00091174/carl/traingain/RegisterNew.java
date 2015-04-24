package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterNew extends ActionBarActivity implements View.OnClickListener {

    RESTurl rest = new RESTurl();
    TextView userN;
    EditText name;
    TextView emailN;
    AutoCompleteTextView email;
    TextView passwordN;
    EditText password;
    TextView confirmPassN;
    EditText confirmPassword;
    TextView dobN;
    EditText dobEdit;
    TextView genderN;
    RadioButton m;
    RadioButton f;
    Button register;
    ApplicationUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setHomeButtonEnabled(true);
        }
        userN = (TextView) findViewById(R.id.usernameLabel);
        name = (EditText) findViewById(R.id.personName);
        emailN = (TextView) findViewById(R.id.emailLabel);
        email = (AutoCompleteTextView) findViewById(R.id.emailAddress);
        passwordN = (TextView) findViewById(R.id.passwordLabel);
        password = (EditText) findViewById(R.id.password);
        confirmPassN = (TextView) findViewById(R.id.confirmPasswordLabel);
        confirmPassword = (EditText) findViewById(R.id.passwordConfirm);
        dobN = (TextView) findViewById(R.id.dobLabel);
        dobEdit = (EditText) findViewById(R.id.dateEditT);
        genderN = (TextView) findViewById(R.id.genderLab);
        m = (RadioButton) findViewById(R.id.radioButtonM);
        f = (RadioButton) findViewById(R.id.radioButtonF);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        m.setOnClickListener(this);
        f.setOnClickListener(this);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Toast.makeText(getBaseContext(), "Password must be at least eight characters long and include one capital letter and symbol", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean validatePassword()
    {
        boolean r = false;
        boolean uppercase = false;
        boolean lowercase = false;
        boolean digit = false;
        boolean lengthP = false;
        //boolean symbol = false;
        String pass = password.getText().toString();
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher matcher = pattern.matcher(pass);
        boolean symbol = matcher.find();
        if(pass.length() >= 8)
        {
            lengthP = true;
        }
        for(char p : pass.toCharArray())
        {
            if(Character.isUpperCase(p))
            {
                uppercase = true;
            }
            else if(Character.isLowerCase(p))
            {
                lowercase = true;
            }
            else if(Character.isDigit(p))
            {
                digit = true;
            }
        }
        if(symbol == true && lengthP == true && uppercase == true && lowercase == true && digit == true)
        {
            r = true;
        }
        if(uppercase == false)
        {
            Toast.makeText(getBaseContext(), "Password must contain at least one uppercase letter", Toast.LENGTH_LONG).show();
        }
        else if(lowercase == false)
        {
            Toast.makeText(getBaseContext(), "Password must contain at least one lowercase letter", Toast.LENGTH_LONG).show();
        }
        else if(digit == false)
        {
            Toast.makeText(getBaseContext(), "Password must contain at least one number", Toast.LENGTH_LONG).show();
        }
        else if(lengthP == false) {
            Toast.makeText(getBaseContext(), "Password must be at least 8 characters long", Toast.LENGTH_LONG).show();
        }
        else if(symbol == false)
        {
            Toast.makeText(getBaseContext(), "Password must contain a symbol", Toast.LENGTH_LONG).show();
        }
        return r;
    }
    @Override
    public void onClick(View view) {
        String nameUser = name.getText().toString();
        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();
        user = new ApplicationUser();
        user.setUserName(name.getText().toString());
        String dateTemp = dobEdit.getText().toString();
        DateFormat format;
        Date date = new Date();
        format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(dateTemp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (m.isChecked()) {
            user.setGender("Male");
        } else if (f.isChecked()) {
            user.setGender("Female");
        }
        user.setEmail(email.getText().toString());
        user.setDob(date);
        boolean val1 = true;
        boolean val2 = true;
        switch ((view.getId())) {
            case R.id.register:
                if(!validate())
                {
                    val1 = false;
                    val2 = false;
                }
                else if(!validatePassword())
                {
                    val2 = false;
                    val1 = false;
                }
            if(val1 == true && val2 == true) {
                new HttpAsyncTask().execute(rest.getREST_REGISTER() + passwordUser, user);
            }
        }
    }

    public boolean validate() {
        if (name.getText().toString().trim().equals("")) {
            Toast.makeText(getBaseContext(), "Please enter Username", Toast.LENGTH_LONG).show();
            return false;
        } else if (email.getText().toString().trim().equals("")) {
            Toast.makeText(getBaseContext(), "Please enter an Email", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.getText().toString().trim().equals("")) {
            Toast.makeText(getBaseContext(), "Please enter a Password", Toast.LENGTH_LONG).show();
            return false;
        } else if (!email.getText().toString().contains("@")) {
            Toast.makeText(getBaseContext(), "Email address must contain '@'", Toast.LENGTH_LONG).show();
            return false;
        } else if (!email.getText().toString().contains(".")) {
            Toast.makeText(getBaseContext(), "Email address must contain '.'", Toast.LENGTH_LONG).show();
            return false;
        } else if (confirmPassword.getText().toString().trim().equals("")) {
            Toast.makeText(getBaseContext(), "Please Confirm Password", Toast.LENGTH_LONG).show();
            return false;
        } else if (!m.isChecked() && !f.isChecked()) {
            Toast.makeText(getBaseContext(), "Please select a Gender", Toast.LENGTH_LONG).show();
            return false;
        } else if(dobEdit.getText().toString().trim().equals(""))
        {
            Toast.makeText(getBaseContext(), "Please enter a Date of Birth", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    public static String POST(String url, ApplicationUser user) {
        InputStream inputStream = null;
        String result = "";
        String s = null;
        int statusCode = 0;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            Gson gson = new Gson();
            json = gson.toJson(user);
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        s = Integer.toString(statusCode);
        return s;
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

    private class HttpAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = (String) params[0];
            user = (ApplicationUser) params[1];
            return POST(url, user);
        }

        protected void onPostExecute(String result) {
            if(result != null)
            {
                System.out.println("Result: " + result);
                Intent intent = new Intent(RegisterNew.this, TestAdding.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Registered", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_register_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

