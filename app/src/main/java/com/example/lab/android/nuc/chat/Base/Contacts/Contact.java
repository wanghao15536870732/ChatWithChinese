package com.example.lab.android.nuc.chat.Base.Contacts;

import android.widget.ImageView;

public class Contact {

    //语伴学子的姓名
    private String name;

    //学习目标
    private String target;

    //好友对其的评价

    private String thnking;

    //个人介绍
    private String detail;

    //好友头像的Id
    private int imageId;

    //在学语言的水平
    private String language_level;

    //联系人的母语
    private String mother_language;

    //正在学习的语言
    private String learn_language;

    private int line;

    public Contact(String name, int ImageId,int onoffline,String learn_language,String mother_language,String language_level){
        this.name = name;
        this.imageId = ImageId;
        this.learn_language = learn_language;
        this.line = onoffline;
        this.mother_language = mother_language;
        this.language_level = language_level;
    }





    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getLanguage_level() {
        return language_level;
    }

    public void setLanguage_level(String language_level) {
        this.language_level = language_level;
    }

    public String getLearn_language() {
        return learn_language;
    }

    public void setLearn_language(String learn_language) {
        this.learn_language = learn_language;
    }

    public String getMother_language() {
        return mother_language;
    }

    public void setMother_language(String mother_language) {
        this.mother_language = mother_language;
    }



    //添加getter、setter方法
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setThnking(String thnking) {
        this.thnking = thnking;
    }

    public String getThnking() {
        return thnking;
    }

    public void setImagrId(int imagrId) {
        this.imageId = imagrId;
    }

    public int getImagrId() {
        return imageId;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
