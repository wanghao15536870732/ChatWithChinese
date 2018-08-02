package com.example.lab.android.nuc.chat.Translation;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
    private static Toast myToast;
    public  static void showToast(Context context, String msg){
        if (myToast==null){
            myToast= Toast.makeText(context,msg, Toast.LENGTH_SHORT);
        }else {
            myToast.setText(msg);
        }
        myToast.show();
    }
}