package com.example.lab.android.nuc.chat.Face;

/**
 * @Description: 相机检查回调
 */
public interface ICameraCheckListener {
    void checkPermission(boolean isAllow);

    void checkPixels(long pixels, boolean isSupport);
}
