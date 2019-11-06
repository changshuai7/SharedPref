package com.shuai.sharedpref;


import android.content.Context;

public class SharedPref {

    /**
     * 请在Application中完成初始化操作
     * @param context
     */
    public static void init(Context context){
        SharePrefWrapper.initContext(context);
    }

    public static void removeKey(String key) {
        SharePrefWrapper.removeKey(SharePrefWrapper.SP_NAME_DEFAULT, key);
    }

    public static boolean contains(String key) {
        return SharePrefWrapper.contains(SharePrefWrapper.SP_NAME_DEFAULT, key);
    }

    public static final void setString(String key, String value) {
        SharePrefWrapper.setString(SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static final String getString(String key, String defVal) {
        return SharePrefWrapper.getString(SharePrefWrapper.SP_NAME_DEFAULT, key, defVal);
    }

    public static String getString(String key) {
        return SharedPref.getString(key, null);
    }

    public static final void setBoolean(String key, boolean value) {
        SharePrefWrapper.setBoolean(SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static final boolean getBoolean(String key, boolean defValue) {
        return SharePrefWrapper.getBoolean(SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static final void setInt(String key, int value) {
        SharePrefWrapper.setInt(SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static final int getInt(String key, int defValue) {
        return SharePrefWrapper.getInt(SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static final void setLong(String key, long value) {
        SharePrefWrapper.setLong(SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static final long getLong(String key, long defValue) {
        return SharePrefWrapper.getLong(SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static final void setFloat(String key, float value) {
        SharePrefWrapper.setFloat(SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static final float getFloat(String key, float defValue) {
        return SharePrefWrapper.getFloat(SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }
}
