package com.example.lab.android.nuc.chat.Base.Search;

public class SearchTag {

    public String title,about,time,message_num;
    public int photo,viewtype;

    public SearchTag(String title, String about, int photo, int viewtype,String time,String message_num) {
        this.title = title;
        this.about = about;
        this.photo = photo;
        this.viewtype=viewtype;
        this.time = time;
        this.message_num = message_num;
    }

    public SearchTag(String title, String about, int viewtype) {
        this.title = title;
        this.about = about;
        this.viewtype=viewtype;
    }
}
