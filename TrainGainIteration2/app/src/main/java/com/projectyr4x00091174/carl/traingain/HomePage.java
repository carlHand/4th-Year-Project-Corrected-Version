package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;


public class HomePage extends Activity {

    GridView myGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        myGrid = (GridView) findViewById(R.id.gridView); //have to cast here, i.e. (GridView), because findViewById returns a view but we are looking to return a gridview
        myGrid.setAdapter(new VivzAdapter(this));

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
}

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
            int[] homepageImages = {R.drawable.programs, R.drawable.progress, R.drawable.favourites,R.drawable.calculations, R.drawable.settings};
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

        class ViewHolder //created a seperate class to find the imageView only once, i.e. create one time initialisation
        {                //rather than finding each view again and again in the getView method as this is more expensive
            ImageButton myHomeImage;

            ViewHolder(View v) {
                myHomeImage = (ImageButton) v.findViewById(R.id.imageButton);
            }
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
            return row;
        }
    }
