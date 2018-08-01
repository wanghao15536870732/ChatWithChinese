package com.example.lab.android.nuc.chat.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ScreenUtils {

    //屏幕宽度
    private static int mScreenW;
    //屏幕高度
    private static int mScreenH;
    //密度
    private static float mDensity;

    /**
     * 使用前必须初始化一下
     *
     * @param activity
     */

    public static void init(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        Display display = wm.getDefaultDisplay();
        mScreenW = display.getWidth() < display.getHeight() ? display.getWidth() : display.getHeight();
        mScreenH = display.getWidth() > display.getHeight() ? display.getWidth() : display.getHeight();
        mDensity = activity.getResources().getDisplayMetrics().density;
    }





    public static int getScreenH() {
        return mScreenH;
    }





    public static int px2dip(float pxValue) {
        return (int) (pxValue / mDensity + 0.5f);
    }



    public static int px720dip(float pxValue) {
        int result = (int) (pxValue * mScreenW / 720);
        return result;
    }


    public static int px1080dip(float pxValue) {
        int result = (int) (pxValue * mScreenW / 1080);
        return result;
    }


    private int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }


    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

}
