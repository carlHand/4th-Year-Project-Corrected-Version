package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carl on 01/04/2015.
 */
public class ExerciseImage implements Parcelable {
    public int ID;
    public String URLimg;

    public ExerciseImage()
    {

    }
    public ExerciseImage(int ID, String URLimg)
    {
        this.ID = ID;
        this.URLimg = URLimg;
    }
    public ExerciseImage(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(ID);
        out.writeString(URLimg);
    }

    public static final Parcelable.Creator<ExerciseImage> CREATOR
            = new Parcelable.Creator<ExerciseImage>() {
        public ExerciseImage createFromParcel(Parcel in) {
            return new ExerciseImage(in);
        }

        public ExerciseImage[] newArray(int size) {
            return new ExerciseImage[size];
        }
    };

    public void readFromParcel(Parcel in) {
        ID = in.readInt();
        in.readString();
    }
}