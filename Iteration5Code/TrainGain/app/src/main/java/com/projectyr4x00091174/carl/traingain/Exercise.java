package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowManager;

/**
 * Created by carl on 16/03/2015.
 */
public class Exercise implements Parcelable {

    private int IDexercise;
    private String Name;
    private double LiftValue;

    public Exercise()
    {

    }
    public Exercise(int IDexercise, String Name, double LiftValue)
    {
        this.IDexercise = IDexercise;
        this.Name = Name;
        this.LiftValue = LiftValue;
    }
    public int getIDexercise()
    {
        return IDexercise;
    }
    public String getName() {
        return Name;
    }
    public double getLiftValue()
    {
        return LiftValue;
    }
    public void setIDexercise(int IDexercise)
    {
        this.IDexercise = IDexercise;
    }
    public void setName(String Name)
    {
        this.Name = Name;
    }
    public void setLiftValue(double LiftValue)
    {
        this.LiftValue = LiftValue;
    }

    private Exercise(Parcel in){

        readFromParcel(in);
    }

    public static final Parcelable.Creator<Exercise> CREATOR
            = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public void readFromParcel(Parcel in)
    {
        IDexercise = in.readInt();
        Name = in.readString();
        LiftValue = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(IDexercise);
        out.writeString(Name);
        out.writeDouble(LiftValue);
    }
}

