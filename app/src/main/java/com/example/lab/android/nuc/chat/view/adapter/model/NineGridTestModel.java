package com.example.lab.android.nuc.chat.view.adapter.model;

import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NineGridTestModel implements Serializable {
    private static final long serialVersionUID = 2189052605715370758L;

    public List<String> urlList = new ArrayList<>();

    public boolean isShowAll = false;

    public String detail;

    public int image;

    public String imageUri;

    public String name;

    public String time;

    public String start;

    public int country_image;

}
