package com.projectyr4x00091174.carl.traingain;

//import org.apache.commons.codec.binary.Base64;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.List;

//import com.facebook.FacebookSdk;
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
import org.json.JSONException;
import org.json.JSONObject;

public class TestAdding extends Activity implements View.OnClickListener {

    private EditText myUsername;
    private EditText myPassword;
    private Button sign;
    private TextView signup;
    ApplicationUser person;
    RESTurl rest = new RESTurl();
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adding);
        myUsername = (EditText) findViewById(R.id.username);
        myPassword = (EditText) findViewById(R.id.password);
        sign = (Button) findViewById(R.id.signI);
        signup = (TextView) findViewById(R.id.signUp);
        //progressBar = (ProgressBar) findViewById(R.id.customProgressBar);
        sign.setOnClickListener(this);
        signup.setOnClickListener(this);
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
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

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

    public static String Token(String url, String username, String password) {// Person person) {
        InputStream inputStream = null;
        String result = "";
        String accessToken = "";
        String s = null;
        int statusCode = 0;
        String expires = "";
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
                        expires = obj.getString("expires_in");
                        System.out.println("EXPIRES: " + expires);
                    }
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
        return s;
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
        MyProgressDialog dialog;
        Context context;
        protected void onPreExecute() {
            dialog = new MyProgressDialog(TestAdding.this, R.drawable.progress_dialog_img);
            dialog.setTitle("Warming Up...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            person = new ApplicationUser();
            String username = myUsername.getText().toString();
            String password = myPassword.getText().toString();
            return Token(urls[0], username, password);
        }

        protected void onPostExecute(String result) {
            int i = Integer.parseInt(result);
            String userName = myUsername.getText().toString();
            dialog.dismiss();
            if (i == 200) {
                Bundle extras = new Bundle();
               // extras.putString("accessToken", result);
                extras.putString("username", userName);
                Intent myIntent = new Intent(TestAdding.this, NavigationDrawer.class);
                myIntent.putExtras(extras);
                TestAdding.this.startActivity(myIntent);
            } else {
                Toast.makeText(getBaseContext(), "Sorry those details are incorrect", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validate() {
        if (myUsername.getText().toString().trim().equals("")) {
            return false;
        } else if (myPassword.getText().toString().trim().equals("")) {
            return false;
        } else {
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
        switch (view.getId()) {

            case R.id.signI:
                new TokenAsyncTask().execute(rest.getREST_LOGIN());
                break;
            case R.id.signUp:
                Intent intent = new Intent(TestAdding.this, RegisterNew.class);
                startActivity(intent);
        }
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