package com.example.lab.android.nuc.chat.Base.Contacts;

import android.net.Uri;

public class UserInfo {
    private String eamil;
    private String interest;
    private String languageLevel;
    public static String name;
    private String nativeLanguage;
    private String studyLanguage;
    private String toGoal;
    private String toPerson;
    public static String UserID = "10";
    public static String picture;
    public static String country;

    public String getName() {
        return name;
    }

    public String getEamil() {
        return eamil;
    }

    public String getInterest() {
        return interest;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public static void setPicture(String picture) {
        UserInfo.picture = picture;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setStudyLanguage(String studyLanguage) {
        this.studyLanguage = studyLanguage;
    }

    public String getStudyLanguage() {
        return studyLanguage;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setToGoal(String toGoal) {
        this.toGoal = toGoal;
    }

    public String getToGoal() {
        return toGoal;
    }

    public void setToPerson(String toPerson) {
        this.toPerson = toPerson;
    }

    public String getToPerson() {
        return toPerson;
    }

    public static String getPicture() {
        return picture;
    }
}
