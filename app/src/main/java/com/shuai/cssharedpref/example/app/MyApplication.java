package com.shuai.cssharedpref.example.app;

import android.app.Application;
import android.content.Context;

import com.shuai.sharedpref.SharedPrefConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefConfig.initContext(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        SharedPrefConfig.getConfig()
                .setDebug(BuildConfig.DEBUG)
                .setSharedPreferenceAuthority("com.shuai.cssharedpref.example.app.provider.sharedprefer");

    }
}
