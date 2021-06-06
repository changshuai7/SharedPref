package com.shuai.sharedpref;


import android.annotation.SuppressLint;
import android.content.Context;

public class SharedPref {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    /**
     * 请在Application中完成初始化操作
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        SharePrefWrapper.initContext(context);
    }

    /// 删除

    public static void removeKey(String pkgName, String key) {
        SharePrefWrapper.removeKey(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key);
    }

    public static void removeKey(String key) {
        SharePrefWrapper.removeKey(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key);
    }

    /// 包含

    public static boolean contains(String pkgName, String key) {
        return SharePrefWrapper.contains(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key);
    }

    public static boolean contains(String key) {
        return SharePrefWrapper.contains(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key);
    }

    /// String

    public static void setString(String key, String value) {
        SharePrefWrapper.setString(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static void setString(String pkgName, String key, String value) {
        SharePrefWrapper.setString(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static String getString(String key, String defVal) {
        return SharePrefWrapper.getString(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, defVal);
    }

    public static String getString(String pkgName, String key, String defVal) {
        return SharePrefWrapper.getString(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, defVal);
    }


    /// Boolean

    public static void setBoolean(String key, boolean value) {
        SharePrefWrapper.setBoolean(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static void setBoolean(String pkgName, String key, boolean value) {
        SharePrefWrapper.setBoolean(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return SharePrefWrapper.getBoolean(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static boolean getBoolean(String pkgName, String key, boolean defValue) {
        return SharePrefWrapper.getBoolean(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    /// Int


    public static void setInt(String key, int value) {
        SharePrefWrapper.setInt(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static void setInt(String pkgName, String key, int value) {
        SharePrefWrapper.setInt(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static int getInt(String key, int defValue) {
        return SharePrefWrapper.getInt(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static int getInt(String pkgName, String key, int defValue) {
        return SharePrefWrapper.getInt(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    /// Long

    public static void setLong(String key, long value) {
        SharePrefWrapper.setLong(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static void setLong(String pkgName, String key, long value) {
        SharePrefWrapper.setLong(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static long getLong(String key, long defValue) {
        return SharePrefWrapper.getLong(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static long getLong(String pkgName, String key, long defValue) {
        return SharePrefWrapper.getLong(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    /// Float


    public static void setFloat(String key, float value) {
        SharePrefWrapper.setFloat(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static void setFloat(String pkgName, String key, float value) {
        SharePrefWrapper.setFloat(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, value);
    }

    public static float getFloat(String key, float defValue) {
        return SharePrefWrapper.getFloat(mContext.getPackageName(), SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }

    public static float getFloat(String pkgName, String key, float defValue) {
        return SharePrefWrapper.getFloat(pkgName, SharePrefWrapper.SP_NAME_DEFAULT, key, defValue);
    }
}
