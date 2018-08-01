package com.example.lab.android.nuc.chat.Face;

/**
 * @Description: 相机检查回调
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 */
public interface ICameraCheckListener {
    void checkPermission(boolean isAllow);

    void checkPixels(long pixels, boolean isSupport);
}
