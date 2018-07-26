package com.example.lab.android.nuc.chat.Base.Search;

public class SearchTag {

    public String title,about,time;
    public int photo,viewtype;

    public SearchTag(String title, String about, int photo, int viewtype,String time) {
        this.title = title;
        this.about = about;
        this.photo = photo;
        this.viewtype=viewtype;
        this.time = time;
    }

    public SearchTag(String title, String about, int viewtype) {
        this.title = title;
        this.about = about;
        this.viewtype=viewtype;
    }
}
