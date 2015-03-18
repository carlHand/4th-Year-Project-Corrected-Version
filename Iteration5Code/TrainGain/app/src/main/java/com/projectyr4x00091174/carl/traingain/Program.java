package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carl on 28/02/2015.
 */
public class Program implements Parcelable {
    public String title;
    public String program;

    public Program(String title, String program) {
        this.title = title;
        this.program = program;
    }

    public String getProgram() {
        return program;
    }

    public String getTitle() {
        return title;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return program;
    }

    // Parcelable implementation
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
    }

    public static final Parcelable.Creator<Program> CREATOR
            = new Parcelable.Creator<Program>() {
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        public Program[] newArray(int size) {
            return new Program[size];
        }
    };

    private Program(Parcel in) {
        title = in.readString();
    }
}