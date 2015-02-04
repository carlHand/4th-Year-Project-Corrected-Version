package com.projectyr4x00091174.carl.traingain;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by carl on 05/11/2014.
 */
public class Person extends AsyncTask<Void, Void, Boolean> {
    public String PersonId;
    private String PersonName;
    private String email;
    private String password;
    private String dob;
    private double weight;
    private double height;
    private String sport;
    private String gender;
    private String activityLevel;
    private boolean mComplete;
    private Context mContext;

    public Person()
    {

    }
    public Person(String personName, String password)
    {
        this.PersonName = personName;
        this.password = password;
    }
    public Person(String personId, String personName, String email)
    {
        setPersonId(personId);
        setPersonName(personName);
        setEmail(email);
    }
    public Person(String personId, String personName, String email, String password, String dob, double weight, double height, String sport, String gender, String activityLevel)
    {
        this.PersonId = personId;
        this.PersonName = personName;
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

    public String getPersonId()
    {
        return PersonId;
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
    public String getPersonName()
    {
        return PersonName;
    }

    public void setPersonId(String personId)
    {
        this.PersonId = personId;
    }

    public void setPersonName(String personName)
    {
        this.PersonName = personName;
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
