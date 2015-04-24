package com.projectyr4x00091174.carl.traingain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

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
import java.util.ArrayList;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomePage extends Fragment implements AdapterView.OnItemClickListener {

    GridView myGrid;
    private Button logO;

    public HomePage()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_page, container, false);
        //setContentView(R.layout.activity_home_page);
        logO = (Button) rootView.findViewById(R.id.logout);
        myGrid = (GridView) rootView.findViewById(R.id.gridView); //have to cast here, i.e. (GridView), because findViewById returns a view but we are looking to return a gridview
        myGrid.setAdapter(new VivzAdapter(getActivity()));
        //logO.setOnClickListener(this);
        myGrid.setOnItemClickListener(this);
        return rootView;
    }

    /*
        @Override
        public void onClick(View v) {
            switch ((v.getId())) {
                case R.id.logout:
                    //if (!validate())
                    Toast.makeText(getBaseContext(), "Enter Some Data", Toast.LENGTH_LONG).show();
                    new LogoutAsyncTask().execute("http://191.237.219.156/api/Account/Logout");
                    break;
            }
        }
    */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        ViewHolder holder = (ViewHolder) view.getTag();
        Fragment fragment = null;
       // Intent intent2 = getIntent();
        //Bundle extras = intent2.getExtras();
        //String userName = extras.getString("username");
        //String access_token = extras.getString("accessToken");
        //System.out.println("HomePage Test username: " + userName);
        //System.out.println("HomePage Test Access_token: " + access_token);

        Bundle extras = getActivity().getIntent().getExtras();
        String userName = extras.getString("username");
        String access_token = extras.getString("accessToken");
        System.out.println("HOMEFRAG TEST USERNAME: " + userName);
        System.out.println("HOMEFRAG TEST ACCESS_TOKEN: " + access_token);
        HomeImage temp = (HomeImage) holder.myHomeImage.getTag();
        if (temp.name.equals("Training Programs")) {
    /*
            intent = new Intent(getActivity(), SearchProgram.class);
           // intent.putExtra("email", userEmail);
           // intent.putExtras(extras);
            startActivity(intent);
      */
            fragment = new SearchProgram();
        }
        else if (temp.name.equals("Calculations")) {
            intent = new Intent(getActivity(), CalcHealthMetrics.class);
            //intent.putExtra("email", userEmail);
            //intent.putExtras(extras);
            startActivity(intent);
        }else if (temp.name.equals("Favourites")) {
            fragment = new Profile();
            //intent = new Intent(getActivity(), Profile.class);
            //intent.putExtra("email", userEmail);
           // intent.putExtras(extras);
//            startActivity(intent);
        } else {
            System.out.println("ERRor: " + temp.name);
        }
        if (fragment != null) {
            fragment.setArguments(extras);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
/*
    private class LogoutAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls) {
            return Logout(urls[0]);
        }
        protected void onPostExecute(String result)
        {
            if(result.equals("200 Ok"))
            {
                Intent myIntent = new Intent(HomePage.this,TestAdding.class);
                HomePage.this.startActivity(myIntent);
            }
            else {
                System.out.println("Result: " + result);
                Toast.makeText(getBaseContext(), "Error please try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String Logout(String url){//String u){
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */


    class HomeImage //need to create a class because we are using an image with a text below it in our VivzAdapter class
    {
        int imageId;
        String name;
        HomeImage(int imageId, String name)
        {
            this.imageId = imageId;
            this.name = name;
        }
    }

class ViewHolder //created a seperate class to find the imageView only once, i.e. create one time initialisation
{                //rather than finding each view again and again in the getView method as this is more expensive
    ImageView myHomeImage;

    ViewHolder(View v) {
        myHomeImage = (ImageView) v.findViewById(R.id.imageView);
    }
}

    class VivzAdapter extends BaseAdapter //this class puts the data into the gridview
    {
        ArrayList<HomeImage> list;
        Context context;

        VivzAdapter(Context context) //need Context to get the values that we specified in our strings.xml file and pass them into our arraylist
        {
            this.context = context;
            list = new ArrayList<HomeImage>();
            Resources res = context.getResources();
            String[] tempHomepageNames = res.getStringArray(R.array.homepage_names);
            int[] homepageImages = {R.drawable.programs, R.drawable.progress, R.drawable.calculations, R.drawable.settings};
            for (int i = 0; i < 5; i++) {
                HomeImage tempImage = new HomeImage(homepageImages[i], tempHomepageNames[i]);
                list.add(tempImage);
            }
        }

        @Override
        public int getCount() { //returns the size of our arrayList
            return list.size();
        }

        @Override
        public Object getItem(int position) { //this will get the Image at the specified position
            return list.get(position);
        }

        @Override
        public long getItemId(int position) { //position identifies the rows
            return position;
        }


        @Override
        public View getView(int i, View view, ViewGroup ViewGroup) { // the second parameter 'view' tells us if we are creating the object for the first time or not. If it is null then we are creating the object for the first time, otherwise it is not Null
            View row = view;
            ViewHolder holder = null;
            if (row == null)//we are creating the object for the first time
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//not using findViewById here to get the view because the LayoutInlater allows us to return a new object every time we call the Inflater method.
                //it is this inflater that returns that single_item.xml from xml to java in the form of an object
                row = inflater.inflate(R.layout.single_item, ViewGroup, false); //*****check the LayoutInflater Video
                holder = new ViewHolder(row); //this row object contains our relative layout in our single_item.xml file
                row.setTag(holder); //using setTag to store the holder inside the view object here so we dont have to keep calling the findViewById method in our ViewHolder Constructor because that is an expensive operation
            } else {
                holder = (ViewHolder) row.getTag(); //now here we just call getTag so now we are not constantly calling the constructor of ViewHolder and calling the findViewById method, thus saving resources
            }
            HomeImage temp = list.get(i); //setting the image for the given item, i.e. first item i = 0, second item i = 1 etc.
            holder.myHomeImage.setImageResource(temp.imageId);
            holder.myHomeImage.setTag(temp);
            return row;
        }

    }
