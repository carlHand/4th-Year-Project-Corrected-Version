package com.projectyr4x00091174.carl.traingain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

    public  class SportProgram implements  Parcelable {
        private int IDsport;
        private String Sport;
        private String Goal;
        private String Title;
        private String Program;
        private String Intensity;
        private double HrsPerDay;
        private int SessionsPerDay;
        private String Genre;
        private String PreTrainingNotes;
        private String DurationInfo;
        private List<Exercise> Exercises;

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

        public SportProgram(int IDsport, String Sport, String Goal, String Title, String Program, String Intensity, double HrsPerDay, int SessionsPerDay, String Genre, String PreTrainingNotes, String DurationInfo, List<Exercise> Exercises) {
            this.IDsport = IDsport;
            this.Sport = Sport;
            this.Goal = Goal;
            this.Title = Title;
            this.Program = Program;
            this.Intensity = Intensity;
            this.HrsPerDay = HrsPerDay;
            this.SessionsPerDay = SessionsPerDay;
            this.Genre = Genre;
            this.PreTrainingNotes = PreTrainingNotes;
            this.DurationInfo = DurationInfo;
            this.Exercises = Exercises;
        }

        public int getIDsport()
        {
            return IDsport;
        }
        public String getSport()
        {
            return Sport;
        }
        public String getGoal()
        {
            return Goal;
        }
        public String getTitle() {
            return Title;
        }
        public String getProgram() {
            return Program;
        }
        public String getIntensity()
        {
            return Intensity;
        }
        public double getHrsPerDay()
        {
            return HrsPerDay;
        }
        public int getSessionsPerDay()
        {
            return SessionsPerDay;
        }
        public String getGenre()
        {
            return Genre;
        }
        public String getPreTrainingNotes()
        {
            return PreTrainingNotes;
        }
        public String getDurationInfo()
        {
            return DurationInfo;
        }
        public List<Exercise> getExercises()
        {
            return Exercises;
        }
        public void setIDsport(int IDsport)
        {
            this.IDsport = IDsport;
        }

        public void setSport(String Sport)
        {
            this.Sport = Sport;
        }
        public void setGoal(String Goal)
        {
            this.Goal = Goal;
        }
        public void setTitle(String Title) {
            this.Title = Title;
        }
        public void setProgram(String Program) {
            this.Program = Program;
        }
        public void setIntensity(String Intensity)
        {
            this.Intensity = Intensity;
        }
        public void setHrsPerDay(double HrsPerDay)
        {
            this.HrsPerDay = HrsPerDay;
        }
        public void setSessionsPerDay(int SessionsPerDay)
        {
            this.SessionsPerDay = SessionsPerDay;
        }
        public void setGenre(String Genre)
        {
            this.Genre = Genre;
        }
        public void setPreTrainingNotes(String PreTrainingNotes)
        {
            this.PreTrainingNotes = PreTrainingNotes;
        }
        public void setDurationInfo(String DurationInfo)
        {
            this.DurationInfo = DurationInfo;
        }
        public void setExercises(List<Exercise> Exercises)
        {
            this.Exercises = Exercises;
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
            out.writeString(Genre);
            out.writeString(PreTrainingNotes);
            out.writeString(DurationInfo);
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
            Genre = in.readString();
            PreTrainingNotes = in.readString();
            DurationInfo = in.readString();
            in.readTypedList(Exercises, Exercise.CREATOR);
        }
    }
