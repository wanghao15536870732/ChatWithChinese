package com.example.lab.android.nuc.chat.Communication.bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;

import com.example.lab.android.nuc.chat.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;


public class ChatConst {
    public static final String LISTVIEW_DATABASE_NAME = "listview.db";
    public static final String RECYCLER_DATABASE_NAME = "recycler.db";
    public static final int SENDING = 0;
    public static final int COMPLETED = 1;
    public static final int SENDERROR = 2;

    @IntDef({SENDING, COMPLETED, SENDERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SendState {
    }

    public static String RESPONSE_HEAD_IMAGE = "response_head_image";
    public static int RESPONSE_HEAD_IMAGEVIEW = 0;
    public static Drawable NEW_DRAWABLE = null;

    public static int TAG = 0;

    public static ArrayList<String> notes;
}
