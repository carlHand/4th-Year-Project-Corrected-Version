package com.projectyr4x00091174.carl.traingain;

import android.content.Context;
import android.os.AsyncTask;

import com.socialize.entity.User;

import java.util.Date;

/**
 * Created by carl on 05/11/2014.
 */
//This class represents the User using this application
public class ApplicationUser {

    private String Id;
    private String UserName;
    private String Email;
    private String Password;
    private Date Dob;
    private double Weight;
    private double Height;
    private String Sport;
    private String Gender;
    private double BMI;
    private double BMR;
    private double HeartMax;
    private double HeartRange1;
    private double HeartRange2;
    private double Calories;
    private boolean ShowHelp;
    public ApplicationUser()
    {

    }

    public ApplicationUser(String UserName, String Email, String Password)//, double BMI, double BMR, double HeartRateAge, double Calories)
    {
        this.UserName = UserName;
        this.Email = Email;
        this.Password = Password;
       // this.Gender = Gender;
     //   this.BMI = BMI;
      //  this.BMR = BMR;
        //this.HeartRateAge = HeartRateAge;
       // this.Calories = Calories;
    }

    public ApplicationUser(String UserName, String Email, String Password, Date Dob, String Gender, boolean ShowHelp, double HeartRange1, double HeartRange2, double HeartMax, double BMI, double BMR, double Calories)
    {
        this.UserName = UserName;
        this.Email = Email;
        this.Password = Password;
        this.Dob = Dob;
        this.Gender = Gender;
        this.ShowHelp = ShowHelp;
        this.HeartRange1 = HeartRange1;
        this.HeartRange2 = HeartRange2;
        this.HeartMax = HeartMax;
        this.BMI = BMI;
        this.BMR = BMR;
        this.Calories = Calories;
    }
    public ApplicationUser(String UserName, String Email, String Password, Date Dob, String Gender, double HeartRange1, double HeartRange2, double HeartMax, double BMI, double BMR, double Calories)
    {
        this.UserName = UserName;
        this.Email = Email;
        this.Password = Password;
        this.Dob = Dob;
        this.Gender = Gender;
        this.HeartRange1 = HeartRange1;
        this.HeartRange2 = HeartRange2;
        this.HeartMax = HeartMax;
        this.BMI = BMI;
        this.BMR = BMR;
        this.Calories = Calories;
    }
    public String getId()
    {
        return Id;
    }
    public String getUserName()
    {
        return UserName;
    }
    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public Date getDob() {
        return Dob;
    }

    public double getWeight() {
        return Weight;
    }

     public double getHeight() {
        return Height;
    }

    public String getSport() {
        return Sport;
    }

    public String getGender() {
        return Gender;
    }
    public boolean getShowHelp()
    {
        return ShowHelp;
    }
    public double getBMI()
    {
        return this.BMI;
    }
    public double getBMR()
    {
        return this.BMR;
    }
    public double getCalories()
    {
        return this.Calories;
    }
    public double getHeartMax()
    {
        return this.HeartMax;
    }
    public double getHeartRange1()
    {
        return  this.HeartRange1;
    }
    public double getHeartRange2()
    {
        return  this.HeartRange2;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }

    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String password) {
        this.Password = Password;
    }

    public void setDob(Date Dob) {
        this.Dob = Dob;
    }

    public void setWeight(double Weight) {
        this.Weight = Weight;
    }

    public void setHeight(double Height) {
        this.Height = Height;
    }

    public void setSport(String Sport) {
        this.Sport = Sport;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }
    public void setShowHelp(boolean ShowHelp){
        this.ShowHelp = ShowHelp;
    }
    public void setBMI(double BMI)
    {
        this.BMI = BMI;
    }
    public void setBMR(double BMR)
    {
        this.BMR = BMR;
    }
    public void setHeartMax(double HeartMax)
    {
        this.HeartMax = HeartMax;
    }
    public void setHeartRange1(double HeartRange1)
    {
        this.HeartRange1 = HeartRange1;
    }
    public void setHeartRange2(double HeartRange2)
    {
        this.HeartRange2 = HeartRange2;
    }
    public void setCalories(double Calories)
    {
        this.Calories = Calories;
    }
}
