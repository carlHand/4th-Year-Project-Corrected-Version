package com.projectyr4x00091174.carl.traingain;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by carl on 05/11/2014.
 */
public class Person extends AsyncTask<Void, Void, Boolean> {
    public long userId;
    private String name;
    public String email;
    public String password;
    private String dob;
    private double weight;
    private double height;
    private String sport;
    private String gender;
    private String activityLevel;
    private boolean mComplete;
    private Context mContext;

    public Person(long userId, String name, String email, String password, String dob, double weight, double height, String sport, String gender, String activityLevel)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.weight = weight;
        this.height = height;
        this.sport = sport;
        this.gender = gender;
        this.activityLevel = activityLevel;
        //this.mContext = mContext;
    }

    public long getUserId()
    {
        return userId;
    }
    public String getName()
    {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return dob;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getSport() {
        return sport;
    }

    public String getGender() {
        return gender;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public Context getmContext()
    {
        return mContext;
    }
    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(String dob) {
        this.dob = dob;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public boolean isComplete()
    {
        return mComplete;
    }

    public void setComplete(boolean complete)
    {
        mComplete = complete;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }
}
