package com.example.lab.android.nuc.chat.Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import com.example.lab.android.nuc.chat.Translation.utils.RecognitionManager;
import com.example.lab.android.nuc.chat.Translation.utils.SynthesisManager;
import com.example.lab.android.nuc.chat.Communication.bean.DaoMaster;
import com.example.lab.android.nuc.chat.Communication.bean.DaoSession;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class MyApplication extends MultiDexApplication{


    private static MyApplication instance;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private DaoSession daoSession;

    public static MyApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // 注意：以下操作必须在主进程中进行
        // 1、UI相关初始化操作
        // 2、相关Service调用
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5ad97691");
        RecognitionManager.getSingleton().init(this,"5ad97691");
        SynthesisManager.getSingleton().init(this,"5ad97691");

        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "chat-message", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
        initImageLoader(this);
        instance = this;
    }


    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
                //.memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder( QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
