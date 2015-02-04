package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONObject;

public class TestAdding extends Activity implements View.OnClickListener {

    private MobileServiceClient mClient;

    private MobileServiceTable<Item> mToDotable;

    private MobileServiceTable<Person> mPerson;//= mClient.getTable(Person.class);
    //private ToDoItemAdapter mAdapter;

    private EditText mTextNewToDo;

    private ProgressBar mProgressBar;

    private ProgressDialog progressDialog;

    private EditText myUserName;
    private EditText myEmail;
    private EditText myPassword;
    private Button sign;
    private TextView con;
    Person person;
    String urlTest = "%5B0%5Dapi/Account/Register";
    private Button info;
    private TextView outputTextView;

    private static String TAG = "HelloRESTfullClient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adding);


        final StringBuilder jsonData = new StringBuilder();

        //outputTextView = (TextView) findViewById(R.id.outputTextView);
// mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        myUserName = (EditText) findViewById(R.id.username);
        myEmail = (EditText) findViewById(R.id.email);
        myPassword = (EditText) findViewById(R.id.password);
        sign = (Button) findViewById(R.id.signIn);
        con = (TextView) findViewById(R.id.connected);
        info = (Button) findViewById(R.id.userInfo);

        if (isConnected()) {
            con.setBackgroundColor(0xff00CC00);
            con.setText("You are Connected");
        } else {
            con.setText("You are NOT connected");
        }
        sign.setOnClickListener(this);
        //    info.setOnClickListener(this);
        info.setOnClickListener(this);
    }


    public void testing(String url)
    {

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
            try{
                JSONObject jsonObject = new JSONObject(result);
               // outputTextView.setText(jsonObject.getString("UserName"));

            } catch (Exception e) {
                Log.d(TAG, "IT DID NOT WORK");
            }
        }
    }

    public static String POST(String url,Person person){//String u){
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
            jsonObject.put("PersonName:", person.getPersonName());
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else
        {
            return false;
        }
    }

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

    private boolean validate()
    {
        if(myEmail.getText().toString().trim().equals("")) {
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


    public void onClick(View view) {
        String name = myUserName.getText().toString();
        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();
        switch ((view.getId())) {

            case R.id.signIn:
                if (!validate())
                    Toast.makeText(getBaseContext(), "Enter Some Data", Toast.LENGTH_LONG).show();
                new HttpAsyncTask().execute("http://137.135.189.41/api/Account/P/" + name + "/" + email + "/" + password);// + "/" + email + "/" + password);//P/" + name); //"http://traingain.cloudapp.net/api/Account/Register");
                //new HttpAsyncTask().execute("http://localhost:57115/api/Account/Register");
                break;
            case R.id.userInfo:
                new ReadJSONFeedTask().execute("http://191.237.219.156/api/Account/UserNames"); //http://traingain.cloudapp.net/api/Account/" + myEmail.getText().toString());
                break;

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
            args.add(new BasicNameValuePair("name", myEmail.getText().toString()));
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
        String email = myEmail.getText().toString();
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
            String email = myEmail.getText().toString();
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
