package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


public class RegisterNew extends Activity implements View.OnClickListener {

    EditText name;
    AutoCompleteTextView email;
    EditText password;
    EditText confirmPassword;
    Button register;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        name = (EditText) findViewById(R.id.personName);
        email = (AutoCompleteTextView) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.passwordConfirm);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String nameUser = name.getText().toString();
        String emailUser = email.getText().toString();
        String passwordUser = password.getText().toString();
        switch ((view.getId())) {
            case R.id.register:
                if (!validatePassword()) {
                    Toast.makeText(getBaseContext(), "Password And Confirm Password Do Not Match", Toast.LENGTH_LONG).show();
                } else {
                    new HttpAsyncTask().execute("http://23.102.22.15/api/Account/Register/" + nameUser + "/" + emailUser + "/" + passwordUser);
                    break;
                }
        }
    }



    public static String POST(String url, Person person) {//String u){
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make POST request to this url
            HttpPost httpPost = new HttpPost(url);
            //HttpGet httpGet = new HttpGet(url);
            String json = "";

            //build json object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username:", person.getPersonName());
            jsonObject.put("Email:", person.getEmail());
            jsonObject.put("Password:", person.getPassword());
            //person.getEmail());
            //jsonObject.accumulate("Password", person.getPassword());

            //convert json object to JSON to String
            json = jsonObject.toString();

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


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            person = new Person();
            EditText confirmPassword;
           // String u = myEmail.getText().toString();
            person.setPersonName(name.getText().toString());
            person.setEmail(email.getText().toString());
            person.setPassword(password.getText().toString());
            return POST(urls[0], person);//u);
        }

        protected void onPostExecute(String result) {
           // if(result.equals("Working")) {
                System.out.println("Result: " + result);
                Intent intent = new Intent(RegisterNew.this, TestAdding.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();

               // Toast.makeText(getBaseContext(), "Sorry that Username/Email is already taken", Toast.LENGTH_LONG).show();

        }
    }

    public boolean validatePassword()
    {
        if(password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_new, menu);
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

