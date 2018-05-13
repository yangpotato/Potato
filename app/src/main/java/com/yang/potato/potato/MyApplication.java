package com.yang.potato.potato;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by potato on 2018/4/16.
 */

public class MyApplication extends Application {
    public static MyApplication app;//单例化Application
    //上下文
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        MobSDK.init(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        UMConfigure.init(this,"5ae41333f43e48796f00000e"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");

        PlatformConfig.setQQZone("101469007", "fde970316375b32e07e63a9aa8583ce3");
        PlatformConfig.setWeixin("wx7332ccbceeb3d2b9", "102e3d0e4c165524b686d0d781b7283a");

    }

    public static MyApplication getApp() {
        if (app == null) {
            synchronized (MyApplication.class) { //线程安全
                if (app == null) {
                    app = new MyApplication();
                }
            }
        }
        return app;
    }

    public static Context getContext() {
        return context;
    }

}
