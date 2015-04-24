package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carl on 02/04/2015.
 */
public class Step implements Parcelable {
    public int IDstep;
    public String StepDes;

    public Step(int IDstep, String StepDes) {
        this.IDstep = IDstep;
        this.StepDes = StepDes;
    }

    public Step()
    {

    }
    public Step(Parcel in) {
        readFromParcel(in);
    }
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(IDstep);
        out.writeString(StepDes);
    }

    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public void readFromParcel(Parcel in) {
        IDstep = in.readInt();
        StepDes = in.readString();
    }
}
