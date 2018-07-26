package com.example.lab.android.nuc.chat.utils.Utils;

import android.content.Context;

class ScreenUtil {
    private static int screenWidth = 0;

    private static int screenHeight = 0;
    private static int screenTotalHeight = 0;
    private static int statusBarHeight = 0;

    private static final int TITLE_HEIGHT = 0;

    /**
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
