package com.brezze.library_common.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;


/**
 * Create by zhangjiabiao on 2019/11/7
 */
public class CommonApp extends Application {

    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this,true);
    }

    public static synchronized void setApplication(@NonNull Application application,@NonNull boolean isDebug) {
        context = application;
    }


    //返回
    public static Context getAppContext(){
        return context.getApplicationContext();
    }
}
