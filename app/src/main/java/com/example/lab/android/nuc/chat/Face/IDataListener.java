package com.example.lab.android.nuc.chat.Face;

/**
 * @Description: 识别数据监听
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 */
public interface IDataListener<T> {
    void onDetectorData(DetectorData<T> detectorData);
}
