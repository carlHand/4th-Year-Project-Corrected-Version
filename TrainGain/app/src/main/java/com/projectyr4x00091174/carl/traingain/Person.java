package com.projectyr4x00091174.carl.traingain;

/**
 * Created by carl on 05/11/2014.
 */
public class Person {
    public String email;
    private String password;
    private int age;
    private double weight;
    private double height;
    private String sport;
    private char sex;
    private String activityLevel;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
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

    public char getSex() {
        return sex;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
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

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }
}
