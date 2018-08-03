package com.example.lab.android.nuc.chat.Face;

import android.hardware.Camera;

/**
 * @Description: 识别接口
 */
public interface IFaceDetector<T> {
    void detector();

    void release();

    void setDataListener(IDataListener<T> mDataListener);

    void setCameraPreviewData(byte[] data, Camera camera);

    void setMaxFacesCount(int mMaxFacesCount);

    void setCameraHeight(int mCameraHeight);

    void setCameraWidth(int mCameraWidth);

    void setPreviewHeight(int mPreviewHeight);

    void setPreviewWidth(int mPreviewWidth);

    void setCameraId(int mCameraId);

    void setOrientionOfCamera(int mOrientionOfCamera);

    void setZoomRatio(float mZoomRatio);

    void setOpenCamera(boolean isOpenCamera);
}
