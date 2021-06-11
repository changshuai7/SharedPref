package com.shuai.cssharedpref.example.app;

import android.app.Application;

import com.shuai.sharedpref.SharedPref;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        SharedPref.init(this,BuildConfig.DEBUG,true);
    }

}
