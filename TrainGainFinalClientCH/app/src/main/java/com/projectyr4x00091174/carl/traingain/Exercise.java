package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carl on 16/03/2015.
 */
public class Exercise implements Parcelable {

    private int IDexercise;
    private String Name;
    private double LiftValue;
    private Date DateLogged;
    public List<SportProgram> SportProgramCollectionE;
    public List<ExerciseImage> ExerciseImageL;
    public List<Step> StepL;

    public Exercise(int IDexercise, String Name, double LiftValue, Date DateLogged, List<Step> StepL, List<ExerciseImage> ExerciseImageL, List<SportProgram> SportProgramCollectionE)
    {
        this.IDexercise = IDexercise;
        this.Name = Name;
        this.LiftValue = LiftValue;
        this.DateLogged = DateLogged;
        this.StepL = StepL;
        this.ExerciseImageL = ExerciseImageL;
        this.SportProgramCollectionE = SportProgramCollectionE;
    }
    public Exercise() {
        StepL = new ArrayList<Step>();
        ExerciseImageL = new ArrayList<ExerciseImage>();
        SportProgramCollectionE = new ArrayList<SportProgram>();
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
    public Date getDateLogged()
    {
        return DateLogged;
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
    public void setDateLogged(Date DateLogged)
    {
        this.DateLogged = DateLogged;
    }
    private Exercise(Parcel in){
        this();
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
        DateLogged = new Date(in.readLong());
//        in.readStringList(Steps);
       // Img = new byte[in.readInt()];
        //in.readByteArray(Img);
        in.readTypedList(StepL, Step.CREATOR);
        in.readTypedList(ExerciseImageL, ExerciseImage.CREATOR);
        in.readTypedList(SportProgramCollectionE, SportProgram.CREATOR);
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
        out.writeLong(DateLogged.getTime());
        out.writeTypedList(StepL);
        out.writeTypedList(ExerciseImageL);
        out.writeTypedList(SportProgramCollectionE);
    }
}

