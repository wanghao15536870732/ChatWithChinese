package com.example.lab.android.nuc.chat.Base.Question;

public class Question {

    private int questionNumber;
    //提出问题的姓名
    private String name;

    //头像的Id
    private int people_imageId;

    //提出问题的时间
    private String time;

    //提问人的国家
    private String country;

    //提问人国家的图片
    private int country_imageId;

    private String question_title;
    private String question_detail;


    //将数据写入到Item上
    public Question(String name, int people_imageId, String time, String country, int country_imageId,String question_detail,String question_title){
        this.name = name;
        this.people_imageId = people_imageId;
        this.time = time;
        this.country = country;
        this.country_imageId = country_imageId;
        this.question_detail =  question_detail;
        this.question_title = question_title;
    }

    //获取问题界面的数据
    public String getName() {
        return name;
    }

    public int getPeople_imageId() {
        return people_imageId;
    }

    public String getTime() {
        return time;
    }

    public String getCountry() {
        return country;
    }
    public int getCountry_imageId() {
        return country_imageId;
    }

    public String getQuestion_detail() {
        return question_detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountry_imageId(int country_imageId) {
        this.country_imageId = country_imageId;
    }

    public void setPeople_imageId(int people_imageId) {
        this.people_imageId = people_imageId;
    }

    public void setQuestion_detail(String question_detail) {
        this.question_detail = question_detail;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
