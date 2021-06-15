package com.shuai.sharedpref;


import android.annotation.SuppressLint;
import android.content.Context;

import com.shuai.sharedpref.utils.Logger;

@SuppressLint("StaticFieldLeak")
public class SharedPref {

    private static Context mContext;
    private static boolean mAllowExternalVisit = false;//是否允许外部访问

    public static boolean isAllowExternalVisit() {
        return mAllowExternalVisit;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 请在Application中完成初始化操作
     *
     * @param context            上下文
     * @param allowExternalVisit 是否允许外部应用程序访问数据
     */
    public static void init(Context context, boolean debug, boolean allowExternalVisit) {
        mContext = context;
        mAllowExternalVisit = allowExternalVisit;
        Logger.debug(debug);
        SharedPrefWrapper.initContext(context);
    }

    public static void init(Context context, boolean debug) {
        init(context, debug, false);
    }


    /// 清除所有数据

    public static void clearAll(String pkgName) {
        SharedPrefWrapper.clearAll(pkgName, SharedPrefType.DEFAULT);
    }

    public static void clearAll() {
        removeKey(mContext.getPackageName());
    }


    /// 删除

    public static void removeKey(String pkgName, String key) {
        SharedPrefWrapper.removeKey(pkgName, SharedPrefType.DEFAULT, key);
    }

    public static void removeKey(String key) {
        removeKey(mContext.getPackageName(), key);
    }

    /// 包含

    public static boolean contains(String pkgName, String key) {
        return SharedPrefWrapper.contains(pkgName, SharedPrefType.DEFAULT, key);
    }

    public static boolean contains(String key) {
        return contains(mContext.getPackageName(), key);
    }

    /// String

    public static void setString(String key, String value) {
        setString(mContext.getPackageName(), key, value);
    }

    public static void setString(String pkgName, String key, String value) {
        SharedPrefWrapper.setString(pkgName, SharedPrefType.DEFAULT, key, value);
    }

    public static String getString(String key, String defValue) {
        return getString(mContext.getPackageName(), key, defValue);
    }

    public static String getString(String pkgName, String key, String defValue) {
        return SharedPrefWrapper.getString(pkgName, SharedPrefType.DEFAULT, key, defValue);
    }


    /// Boolean

    public static void setBoolean(String key, boolean value) {
        setBoolean(mContext.getPackageName(), key, value);
    }

    public static void setBoolean(String pkgName, String key, boolean value) {
        SharedPrefWrapper.setBoolean(pkgName, SharedPrefType.DEFAULT, key, value);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getBoolean(mContext.getPackageName(), key, defValue);
    }

    public static boolean getBoolean(String pkgName, String key, boolean defValue) {
        return SharedPrefWrapper.getBoolean(pkgName, SharedPrefType.DEFAULT, key, defValue);
    }

    /// Int

    public static void setInt(String key, int value) {
        setInt(mContext.getPackageName(), key, value);
    }

    public static void setInt(String pkgName, String key, int value) {
        SharedPrefWrapper.setInt(pkgName, SharedPrefType.DEFAULT, key, value);
    }

    public static int getInt(String key, int defValue) {
        return getInt(mContext.getPackageName(), key, defValue);
    }

    public static int getInt(String pkgName, String key, int defValue) {
        return SharedPrefWrapper.getInt(pkgName, SharedPrefType.DEFAULT, key, defValue);
    }

    /// Long

    public static void setLong(String key, long value) {
        setLong(mContext.getPackageName(), key, value);
    }

    public static void setLong(String pkgName, String key, long value) {
        SharedPrefWrapper.setLong(pkgName, SharedPrefType.DEFAULT, key, value);
    }

    public static long getLong(String key, long defValue) {
        return getLong(mContext.getPackageName(), key, defValue);
    }

    public static long getLong(String pkgName, String key, long defValue) {
        return SharedPrefWrapper.getLong(pkgName, SharedPrefType.DEFAULT, key, defValue);
    }

    /// Float

    public static void setFloat(String key, float value) {
        setFloat(mContext.getPackageName(), key, value);
    }

    public static void setFloat(String pkgName, String key, float value) {
        SharedPrefWrapper.setFloat(pkgName, SharedPrefType.DEFAULT, key, value);
    }

    public static float getFloat(String key, float defValue) {
        return getFloat(mContext.getPackageName(), key, defValue);
    }

    public static float getFloat(String pkgName, String key, float defValue) {
        return SharedPrefWrapper.getFloat(pkgName, SharedPrefType.DEFAULT, key, defValue);
    }

}
