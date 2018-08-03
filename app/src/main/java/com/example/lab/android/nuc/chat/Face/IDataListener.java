package com.example.lab.android.nuc.chat.Face;

/**
 * @Description: 识别数据监听
 */
public interface IDataListener<T> {
    void onDetectorData(DetectorData<T> detectorData);
}
