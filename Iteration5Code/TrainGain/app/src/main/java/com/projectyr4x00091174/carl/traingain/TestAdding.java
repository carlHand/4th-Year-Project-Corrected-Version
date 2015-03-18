package com.projectyr4x00091174.carl.traingain;

import org.apache.commons.codec.binary.Base64;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.microsoft.windowsazure.mobileservices.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class TestAdding extends Activity implements View.OnClickListener {

    private MobileServiceClient mClient;

    private MobileServiceTable<Item> mToDotable;

    private MobileServiceTable<Person> mPerson;//= mClient.getTable(Person.class);
    //private ToDoItemAdapter mAdapter;

    private EditText mTextNewToDo;

    private ProgressBar mProgressBar;

    private static final String BASIC_AUTH = "BASIC";
    private static final String TOKEN_JSON_KEY = "Token";


    private ProgressDialog progressDialog;
    private static final String tokenUrl = "api/Values";

    private EditText myUsername;
    private EditText myPassword;
    private Button sign;
    private Button signup;
    private TextView con;
    Person person;
    String urlTest = "%5B0%5Dapi/Account/Register";

    private TextView outputTextView;

    private static String TAG = "HelloRESTfullClient";

    public static final String Values = "http://23.102.22.15/api/Values";

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adding);


        final StringBuilder jsonData = new StringBuilder();

        //outputTextView = (TextView) findViewById(R.id.outputTextView);
// mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        myUsername = (EditText) findViewById(R.id.username);
        myPassword = (EditText) findViewById(R.id.password);
        sign = (Button) findViewById(R.id.signI);
        signup = (Button) findViewById(R.id.signUp);
        /*
        con = (TextView) findViewById(R.id.connected);
        if (isConnected()) {
            con.setBackgroundColor(0xff00CC00);
            con.setText("You are Connected");
        } else {
            con.setText("You are NOT connected");
        }
        */
        sign.setOnClickListener(this);
        signup.setOnClickListener(this);
        //    info.setOnClickListener(this);
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }


    private void sendPostRequest(String givenUsername, String givenPassword) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramPassword = params[1];
                System.out.println("***doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://191.237.219.156/api/Account/SignUp");

                BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("paramUsername", paramUsername);
                BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("paramPassword", paramPassword);

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

                nameValuePairList.add(usernameBasicNameValuePair);
                nameValuePairList.add(passwordBasicNameValuePair);

                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String bufferedStrChunk = null;
                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            stringBuilder.append(bufferedStrChunk);
                        }
                        return stringBuilder.toString();
                    } catch (ClientProtocolException cpe) {
                        System.out.println("first Exception caz of HttpResponse :" + cpe);
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        System.out.println("Second Exception caz of HttpResponse :" + ioe);
                        ioe.printStackTrace();
                    }
                } catch (UnsupportedEncodingException uee) {
                    System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                    uee.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Working")) {
                    Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid POST req...", Toast.LENGTH_LONG).show();
                    System.out.println(result);
                }
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(givenUsername, givenPassword);
    }
    // kick off a new thread which calls RESTful service
    // note: all networking calls in Android 3.0 must be in a separate thread from main thread
    // otherwise exception is thrown

    /*
        Thread t = new Thread() {
            public void run()                                                        // anonymous class
            {
                try {
                    URI uri = new URI("http://traingain.cloudapp.net/api/Account/UserInfo");                        // create URL
                    Log.d(TAG, "Connecting to" + uri.toString());                    // debug log message

                    // issue a GET to uri
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(uri);                                    // or HttpPost
                    HttpResponse response = httpClient.execute(get);

                    // get a reader to response content
                    InputStream is = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = null;
                    Log.d(TAG, "Retreivng data");
                    try {
                        while ((line = reader.readLine()) != null)                    // read line by line
                        {
                            Log.d(TAG, "appending" + line);
                            jsonData.append(line + "\n");
                        }
                    } catch (IOException e) {
                        Log.d(TAG, e.toString());
                        outputTextView.setText(e.toString());
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            Log.d(TAG, e.toString());
                            outputTextView.setText(e.toString());
                        }
                    }
                    Log.d(TAG, "Data retrieved" + jsonData);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    outputTextView.setText(e.toString());
                }
            }
        };
        try {
            t.start();                        // kick off thread
            t.join();                        // wait for thread to finish

            // create new JSON Object from JSON data
            JSONObject jObject = new JSONObject(jsonData.toString());

            Log.d(TAG, "Displaying data" + jsonData);

            // display on TextView
            // Message and To are properties on Message model class in MVC application
             outputTextView.setText(jObject.getString("UserName"));

            // could parse JSON data directly to a custom class e.g. Greeting
            // e.g. gson api
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            outputTextView.setText(e.toString());
        }
    }

*/


    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                Log.d("OK", "So far so GOOD!!!");
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                // outputTextView.setText(jsonObject.getString("UserName"));

            } catch (Exception e) {
                Log.d(TAG, "IT DID NOT WORK");
            }
        }
    }
/*
    public static String Login(String username, String password)
    {
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make POST request to this url
            HttpPost httpPost = new HttpPost(tokenUrl);
            String json = "";
       // HttpWebRequest request = new HttpWebRequest(new Uri(String.Format("{0}Token", Constants.BaseAddress)));
        //request.Method = "POST";

        String postString = String.Format("username={0}&amp;password={1}&amp;grant_type=password", HttpUtility.HtmlEncode(username), HttpUtility.HtmlEncode(password));
        byte[] bytes = Encoding.UTF8.GetBytes(postString);
        using (Stream requestStream = await request.GetRequestStreamAsync())
        {
            requestStream.Write(bytes, 0, bytes.Length);
        }

            BasicNameValuePair grantBasicNameValuePair = new BasicNameValuePair("grant_type", "password");
            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", username);
            BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("password", password);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            nameValuePairList.add(usernameBasicNameValuePair);
            nameValuePairList.add(passwordBasicNameValuePair);
            String encodedString = URLEncoder.encode(username, "UTF-8");

        try
        {
            //HttpWebResponse httpResponse =  (HttpWebResponse)(await request.GetResponseAsync());
            string json;
            using (Stream responseStream = httpResponse.GetResponseStream())
            {
                json = new StreamReader(responseStream).ReadToEnd();
            }

            HttpResponse httpResponse = httpClient.execute(httpPost);
            // HttpResponse httpResponse = httpClient.execute(httpGet);
            //receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                json = convertInputStreamToString(inputStream);
            }

            TokenResponseModel tokenResponse = JsonConvert.DeserializeObject(json);
            return tokenResponse.AccessToken;
        }
        catch (Exception ex)
        {
            throw new SecurityException("Bad credentials", ex);
        }
    }
*/

    public static String Token(String url, String username, String password) {// Person person) {
        InputStream inputStream = null;
        String result = "";
        String accessToken = "";
        String s = null;
        int statusCode = 0;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make POST request to this url
            HttpPost httpPost = new HttpPost(url);
            //HttpGet httpGet = new HttpGet(url);
            String json = "";

            BasicNameValuePair grantBasicNameValuePair = new BasicNameValuePair("grant_type", "password");
            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("Username", username);//person.getPersonName());//should be username
            BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("Password", password);//person.getPassword());

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            nameValuePairList.add(grantBasicNameValuePair);
            nameValuePairList.add(usernameBasicNameValuePair);
            nameValuePairList.add(passwordBasicNameValuePair);

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);//, HTTP.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);

                httpPost.setHeader("Accept", "application/json");
                //httpPost.setHeader("Accept-Charset", "UTF-8");
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                // httpPost.setHeader("Authorization", BASIC_AUTH + " " +
                //       new String(Base64.encodeBase64(new String(person.getPersonName() + ":" + person.getPassword()).getBytes("UTF-8"))));

                try {
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    StatusLine statusLine = httpResponse.getStatusLine();
                    statusCode = statusLine.getStatusCode();
                    inputStream = httpResponse.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String bufferedStrChunk = null;
                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                        result = stringBuilder.toString();
                        System.out.println("RESULT!!: " + result);

                        JSONObject obj = new JSONObject(result.toString());
                        accessToken = obj.getString("access_token");
                        //result = convertInputStreamToString(inputStream);
                    }

                    // return stringBuilder.toString();
                } catch (ClientProtocolException cpe) {
                    System.out.println("first Exception caz of HttpResponse :" + cpe);
                    cpe.printStackTrace();
                } catch (IOException ioe) {
                    System.out.println("Second Exception caz of HttpResponse :" + ioe);
                    ioe.printStackTrace();
                }
            } catch (UnsupportedEncodingException uee) {
                System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                uee.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Last Exception caught");
            e.printStackTrace();
        }
        s = Integer.toString(statusCode);
        return accessToken;
    }

    public static String Log(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                Log.d("OK", "So far so GOOD!!!");
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    public static String TokenT(String url, Person person) {//String u){
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
            jsonObject.put("grant_type:", "password");
            jsonObject.put("UserName:", person.getPersonName());
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
            // httpPost.setHeader("Accept", "application/json");
            // httpPost.setHeader("Content-type", "application/json");

            httpPost.setHeader("Accept", "application/json");

//            httpPost.setHeader("Accept-Charset", "UTF-8");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            // httpPost.setHeader("Authorization", BASIC_AUTH + " " +
            //       new String(Base64.encodeBase64(new String(person.getPersonName() + ":" + person.getPassword()).getBytes("UTF-8"))));


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

    public static String TestT(String url, String username, String password) {//String u){
        InputStream inputStream = null;
        String result = "";
        try {
            //Create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            //make POST request to this url
            HttpPost httpPost = new HttpPost(url);
            //HttpGet httpGet = new HttpGet(url);
            String json = "";

            BasicNameValuePair grantBasicNameValuePair = new BasicNameValuePair("grant_type", "password");
            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("Username", username);//person.getPersonName());
            BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("Password", password);//person.getPassword());

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            nameValuePairList.add(grantBasicNameValuePair);
            nameValuePairList.add(usernameBasicNameValuePair);
            nameValuePairList.add(passwordBasicNameValuePair);


            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);//, HTTP.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);

            //set some headers to inform server about the type of content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

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

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public class TokenAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            person = new Person();
            String username = myUsername.getText().toString();
            String password = myPassword.getText().toString();
            // person.setPersonName(myEmail.getText().toString());
            //person.setEmail(myEmail.getText().toString());
//            person.setPassword(myPassword.getText().toString());
            return Token(urls[0], username, password);//person);//u);
        }

        protected void onPostExecute(String result) {
           // int i = Integer.parseInt(result);
            String userName = myUsername.getText().toString();
            //if (i == 200) {
            //No value for access_token
            if(!result.equals(null))
            {
                Bundle extras = new Bundle();
                extras.putString("accessToken", result);
                extras.putString("username", userName);
                Intent myIntent = new Intent(TestAdding.this, HomePage.class);
               // myIntent.putExtra("email", userEmail);
                myIntent.putExtras(extras);
                TestAdding.this.startActivity(myIntent);
            } else {
                Toast.makeText(getBaseContext(), "Sorry those details are incorrect", Toast.LENGTH_LONG).show();
            }
        }
    }
    /*
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(Values);
                httpGet.setHeader("Authorization", "Bearer " + result);
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        Log.d("OK", "So far so GOOD!!!");
                        HttpEntity entity = response.getEntity();
                        InputStream inputStream = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                            vals = stringBuilder.toString();
                        }
                        inputStream.close();
                    } else {
                        Log.d("JSON", "Failed to download file");
                    }
                } catch (Exception e) {
                    Log.d("readJSONFeed", e.getLocalizedMessage());
                }
                System.out.println(vals);
            }
        }

                /*
                if(result != null) {
                    System.out.println("Result: " + result);
                    System.out.println("Got Token");
             //       System.out.println("Token expires at " + result.ExpiresAt);
                }
                else{
                    System.out.println("Get Token Failed");
                }
            }
            */
/*
    private class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls) {
            person = new Person();
            String u = myEmail.getText().toString();
            person.setPersonName(myUserName.getText().toString());
            person.setEmail(myEmail.getText().toString());
            person.setPassword(myPassword.getText().toString());
            return POST(urls[0], person);//u);
        }
        protected void onPostExecute(String result)
        {
            System.out.println("Result: " + result);
            Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
        }
    }
*/
    private boolean validate()
    {
        if(myUsername.getText().toString().trim().equals("")) {
            return false;
        }
        else if(myPassword.getText().toString().trim().equals("")) {
            return false;
        }
        else
        {
            return true;
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

    /*
        private class LoginTask extends AsyncTask<String, Void, String>
        {
            @Override
            protected String doInBackground(String... urls) {
                person = new Person();
                String u = myEmail.getText().toString();
                String user = myUserName.getText().toString();
                String password = myPassword.getText().toString();
                person.setPersonName(myUserName.getText().toString());
                person.setEmail(myEmail.getText().toString());
                person.setPassword(myPassword.getText().toString());
                String result = null;
                try {
                    doLogin(user, password, urls[0]);//u);
                    result = doLogin(user, password, urls[0]);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                return result;
            }
            protected void onPostExecute(String result)
            {
                System.out.println("Result for login test is: " + result);
                Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
            }
        }
    */
    private static String doLogin(String user, String pwd, String tsUrl)
            throws IOException, JSONException
    {
        String line = null;
        System.out.println("Making login request for " + user + " to server " + tsUrl);
        //try{
        HttpClient httpclient = new DefaultHttpClient();

        URL url = new URL(tsUrl);

        HttpPost httppost = new HttpPost(tsUrl);

        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Accept-Charset", "UTF-8");

        httppost.setHeader("Authorization", BASIC_AUTH + " " +
                new String(Base64.encodeBase64(new String(user + ":" + pwd).getBytes("UTF-8"))));

        // Execute the request
        HttpResponse authResponse = httpclient.execute(httppost);

        int status = authResponse.getStatusLine().getStatusCode();
        System.out.println("The server responded with status code: " + status);

        HttpEntity entity = authResponse.getEntity();

        StringBuffer response = new StringBuffer();

        // If the response does not enclose an entity, there is no need
        // to worry about connection release
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(instream));
                //String line = null;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (RuntimeException ex) {
                // In case of an unexpected exception you may want to abort
                // the HTTP request in order to shut down the underlying
                // connection and release it back to the connection manager.
                httppost.abort();
                throw ex;
            } finally {
                // Closing the input stream will trigger connection release
                if (reader != null) {
                    reader.close();
                }

            }
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

        JSONObject obj = new JSONObject(response.toString());
        String token = obj.getString(TOKEN_JSON_KEY);

        System.out.println("The login completed successfully and the server generated the following token\n\n\t" + token);
        return line;
        //doLogout(token, tsUrl);
    }

    public void onClick(View view) {
        //String name = myUserName.getText().toString();
        String username = myUsername.getText().toString();
        String password = myPassword.getText().toString();
        String grant_type = "grant_type";
        switch (view.getId()) {

            case R.id.signI:

                /*
               // new HttpAsyncTask().execute("http://137.135.189.41/api/Account/P/" + name + "/" + email + "/" + password);// + "/" + email + "/" + password);//P/" + name); //"http://traingain.cloudapp.net/api/Account/Register");
                new HttpAsyncTask().execute("http://23.102.22.15/api/Account/Register/" + name + "/" + email + "/" + password);
                break;

            case R.id.userInfo:
                new ReadJSONFeedTask().execute("http://191.237.219.156/api/Account/UserNames"); //http://traingain.cloudapp.net/api/Account/" + myEmail.getText().toString());
                break;
                */

                new TokenAsyncTask().execute("http://23.102.22.15/Token");
                break;
            case R.id.signUp:
                Intent intent = new Intent(TestAdding.this, RegisterNew.class);
                startActivity(intent);
        }
    }

/*
    public void onClick(View view) {
        if(view.getId() == R.id.signIn)
        {
                String givenUserName = myEmail.getText().toString();
                String givenPassword = myPassword.getText().toString();
                System.out.println("Given username :" + givenUserName + " Given password :" + givenPassword);
                sendPostRequest(givenUserName, givenPassword);
        }
    }
*/

    class CreateNewProduct extends AsyncTask<String, String, String> {

        /*
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
                    progressDialog = new ProgressDialog(TestAdding.this);
                    progressDialog.setMessage("Creating Product...");
                    progressDialog.show();
                }
        */
        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair("name", myUsername.getText().toString()));
            args.add(new BasicNameValuePair("password", myPassword.getText().toString()));
            //args.add(new BasicNameValuePair("description", editTextDescription.getText().toString()));
            JSONHttpClient jsonHttpClient = new JSONHttpClient();
            Person person = (Person) jsonHttpClient.PostParams(ServiceUrl.PRODUCT, args, Person.class);
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
            //          finish();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //       progressDialog.dismiss();
            Toast.makeText(getBaseContext(), "Data sent", Toast.LENGTH_LONG).show();
        }
    }

    class ServiceUrl{
        public static final String REST_SERVICE_URL = "http://191.237.219.156/api/Account"; //http://restwebserviceforandroid.apphb.com/api/";
        public static final String PRODUCT = REST_SERVICE_URL + "Account";
    }





    //  Register r1 = new Register();
    //  r1.execute(new String[] {urlTest});

    /*


            try {
                mClient = new MobileServiceClient(
                        "https://user.azure-mobile.net/",
                        "IeGHpCQHqBaTBkXzJjqOnRBdpabImP55",
                        this
                );
                mPerson = mClient.getTable(Person.class);
            } catch (MalformedURLException e) {
                createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify Url"), "Error");
            }

        }
    */
    /*
    try {
        mClient = new MobileServiceClient(
                "https://user.azure-mobile.net/",
                "IeGHpCQHqBaTBkXzJjqOnRBdpabImP55",
                this
        );


        Item item = new Item();
        item.text = "Item example 1";
        item.id = "01";
        mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
            public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert failed
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mToDotable = mClient.getTable(Item.class);

        mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);

        // mAdapter = new ToDoItemAdapter(this, R.layout.raw_list_to_do);
        //refreshItemsFromTable();
    } catch (MalformedURLException e) {
        createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify Url"), "Error");
    }
}
*/
    public void addUser() {
        String id = "01";
        String email = myUsername.getText().toString();
        String password = myPassword.getText().toString();
        Person p = new Person(id, password, email);
        if (mPerson.select("email").equals(email)) {
            Toast.makeText(getApplicationContext(), "email already exists", Toast.LENGTH_SHORT);
        } else {

            mClient.getTable(Person.class).insert(p, new TableOperationCallback<Person>() {
                public void onCompleted(Person entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null) {
                        // Insert succeeded
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        // Insert failed
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    class RegisterModel{
        String UserName;
        String Password;
        //String ConfirmPassword;

        public RegisterModel(String username, String password)
        {
            this.UserName = username;
            this.Password = password;
        }
    }

    class Register extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            RegisterModel r = null;
            String responseString = null;
            String email = myUsername.getText().toString();
            String password = myPassword.getText().toString();
            r = new RegisterModel(email, password);
            URL iurl = null;
            String jsonData = null;
            HttpURLConnection urlConnection = null;
            InputStream in = null;

            try {
                iurl = new URL(urlTest);
                //line below is equivalent to C# httpWebRequest
                urlConnection = (HttpURLConnection) iurl.openConnection();

                urlConnection.setRequestMethod("Post");

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.connect();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            try {
                if (urlConnection != null) {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                in.close();
                jsonData = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }


        /*
                HttpURLConnection request = (HttpURLConnection)WebRequest.Create(urlTest);

                ContentType content = "application/json";
                HttpWebRequest request = new HttpWebRequest(new Uri(String.Format("{0}api/Account/Register", Constants.BaseAddress)));
                request.Method = "POST";
                request.ContentType = "application/json";
                request.Accept = "application/json";
                string json = JsonConvert.SerializeObject(model);
                byte[] bytes = Xml.Encoding.UTF8.GetBytes(json);
                using(Stream stream = await request.GetRequestStreamAsync())
                {
                    stream.Write(bytes, 0, bytes.Length);
                }

                try
                {
                    await request.GetResponseAsync();
                    return true;
                }
                catch (Exception ex)
                {
                    return false;
                }
    */
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
        }
    }
    /*

        HttpGet get = new HttpGet(uri);
        HttpWebRequest request = new HttpWebRequest(new Uri(String.Format("{0}api/Account/Register", SyncStateContract.Constants.BaseAddress)));
        request.Method = "POST";
        request.ContentType = "application/json";
        request.Accept = "application/json";
        string json = JsonConvert.SerializeObject(model);
        byte[] bytes = Xml.Encoding.UTF8.GetBytes(json);
        using(Stream stream = await request.GetRequestStreamAsync())
        {
            stream.Write(bytes, 0, bytes.Length);
        }

        try {
            await request.GetResponseAsync();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
*/
    public void createAndShowDialog(Exception e, String error)
    {
        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_adding, menu);
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
