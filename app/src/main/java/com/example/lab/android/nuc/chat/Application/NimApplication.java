package com.example.lab.android.nuc.chat.Application;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;



public class NimApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext( base );
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // SDK初始化（启动后台服务，若已经存在用户登录信息，SDK 将完成自动登录）
        NIMClient.init( this, loginInfo(),options());
    }

    private LoginInfo loginInfo(){
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = Preference.getUserAccount();
        String token = Preference.getUserToken();
        if (!TextUtils.isEmpty( account ) && !TextUtils.isEmpty( token )){
            DemoCache.setAccount( account.toLowerCase() );
            return new LoginInfo( account,token );
        }else {
            return null;
        }
    }


    private SDKOptions options(){
        return null;
    }
}
