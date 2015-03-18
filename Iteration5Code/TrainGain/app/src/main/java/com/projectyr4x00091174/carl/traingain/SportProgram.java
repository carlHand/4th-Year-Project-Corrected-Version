package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public  class SportProgram implements  Parcelable {
        public int IDsport;
        public String Sport;
        public String Goal;
        public String Title;
        public String Program;
        public String Intensity;
        public double HrsPerDay;
        public int SessionsPerDay;
        public List<Exercise> Exercises;

        public SportProgram(String Title, String Program) {
            this.Title = Title;
            this.Program = Program;
        }

        public SportProgram() {
            Exercises = new ArrayList<Exercise>();
        }

        private SportProgram(Parcel in) {
            this();
            readFromParcel(in);
        }

        public SportProgram(int IDsport, String Sport, String Goal, String Title, String Program, String Intensity, double HrsPerDay, int SessionsPerDay, List<Exercise> Exercises) {
            this.IDsport = IDsport;
            this.Sport = Sport;
            this.Goal = Goal;
            this.Title = Title;
            this.Program = Program;
            this.Intensity = Intensity;
            this.HrsPerDay = HrsPerDay;
            this.SessionsPerDay = SessionsPerDay;
            this.Exercises = Exercises;
        }

        public String getProgram() {
            return Program;
        }

        public String getTitle() {
            return Title;
        }

        public void setProgram(String Program) {
            this.Program = Program;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String toString() {
            return Title;
        }

        // Parcelable implementation
        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(IDsport);
            out.writeString(Sport);
            out.writeString(Goal);
            out.writeString(Title);
            out.writeString(Program);
            out.writeString(Intensity);
            out.writeDouble(HrsPerDay);
            out.writeInt(SessionsPerDay);
            out.writeTypedList(Exercises);
        }

        public static final Parcelable.Creator<SportProgram> CREATOR
                = new Parcelable.Creator<SportProgram>() {
            public SportProgram createFromParcel(Parcel in) {
                return new SportProgram(in);
            }

            public SportProgram[] newArray(int size) {
                return new SportProgram[size];
            }
        };

        public void readFromParcel(Parcel in) {
            IDsport = in.readInt();
            Sport = in.readString();
            Goal = in.readString();
            Title = in.readString();
            Program = in.readString();
            Intensity = in.readString();
            HrsPerDay = in.readDouble();
            SessionsPerDay = in.readInt();
            in.readTypedList(Exercises, Exercise.CREATOR);
        }
    }
