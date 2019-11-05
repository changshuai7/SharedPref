package com.shuai.sharedpref;

import android.content.Context;

public class SharedPrefConfig {

    private static SharedPrefConfig.Config sConfig;

    public static SharedPrefConfig.Config getConfig() {
        if (sConfig == null) {
            sConfig = new SharedPrefConfig.Config();
        }
        return sConfig;
    }

    public static class Config {

        private boolean isDebug = false;
        private String mSharedPreferenceAuthority;

        public boolean isDebug() {
            return isDebug;
        }

        public SharedPrefConfig.Config setDebug(boolean debug) {
            this.isDebug = debug;
            return this;
        }

        public String getSharedPreferenceAuthority() {
            return mSharedPreferenceAuthority;
        }

        /**
         * 此处必须在attachBaseContext中初始化Authority
         *
         * @param mSharedPreferenceAuthority
         * @return
         */
        public SharedPrefConfig.Config setSharedPreferenceAuthority(String mSharedPreferenceAuthority) {
            this.mSharedPreferenceAuthority = mSharedPreferenceAuthority;
            return this;
        }
    }

    /**
     * 初始化Context，必须在Application的onCreate中初始化。
     *
     * @param context
     */
    public static void initContext(Context context) {
        SharePrefWrapper.initContext(context);
    }
}
