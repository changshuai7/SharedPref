package com.shuai.sharedpref;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.shuai.sharedpref.utils.Util;


public class SharePrefWrapper {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = DEBUG ? "SharePrefWrapper" : "spw";

    public static final String SP_NAME_DEFAULT = "sp_default";
    public static final String SP_NAME_OTHER = "sp_other";

    private static Context mContext;
    private static String mAuthority;


    public static void initContext(Context context) {
        mContext = context;
        mAuthority = Util.getProviderAuthority(context, SharedPreferenceProvider.class.getName());
    }

    public static Uri getDBSharedPrefUri(final String prefName) {
        return getDBSharedPrefUri(mContext.getPackageName(), prefName);
    }

    final static String AuthoritySuffix = ".provider.sharedprefer";

    /**
     * 获取URI
     * @param pkgName   应用包名
     * @param prefName  表类型
     * @return
     */
    public static Uri getDBSharedPrefUri(final String pkgName, final String prefName) {
        if (null == prefName) {
            return Uri.parse("content://" + pkgName + AuthoritySuffix + "/default");
        } else if (SP_NAME_OTHER.equals(prefName)) {
            return Uri.parse("content://" + pkgName + AuthoritySuffix + "/other");
        }  else {
            return Uri.parse("content://" + pkgName + AuthoritySuffix + "/default");
        }
    }


    public static void removeKey(final String pkgName, final String prefName, String key) {
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            contentResolver.delete(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), SpDbHelper.COL_KEY + "=?",
                    new String[]{key});
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "err", e);
            }
        }
    }


    public static boolean contains(final String pkgName, final String prefName, String key) {
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null && cursor.getCount() > 0) {
                ret = true;
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return ret;
    }

    public static void setString(final String pkgName, final String prefName, String key, String value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(prefName), values);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        }
    }


    public static String getString(final String pkgName, final String prefName, String key, String defVal) {
        String value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    value = cursor.getString(0);
                }
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setBoolean(final String pkgName, final String prefName, String key, boolean value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value ? 1 : 0);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(prefName), values);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        }
    }

    public static boolean getBoolean(final String pkgName, final String prefName, String key, boolean defVal) {
        boolean value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Integer.valueOf(cursor.getString(0)).intValue() == 1 ? true : false;
                    } catch (Exception e) {
                        if (DEBUG) {
                            Log.e(TAG, "Integer.valueOf [catch]", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "Exception [catch]", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.e(TAG, "Error [catch]", e);
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setInt(final String pkgName, final String prefName, String key, int value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(prefName), values);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        }
    }

    public static int getInt(final String pkgName, final String prefName, String key, int defVal) {
        int value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Integer.valueOf(cursor.getString(0)).intValue();
                    } catch (Exception e) {
                        if (DEBUG) {
                            Log.e(TAG, "Integer.valueOf [catch]", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.e(TAG, "Error [catch]", e);
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setLong(final String pkgName, final String prefName, String key, long value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();

        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(prefName), values);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        }
    }

    public static long getLong(final String pkgName, final String prefName, String key, long defVal) {
        long value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Long.valueOf(cursor.getString(0)).longValue();
                    } catch (Exception e) {
                        /**
                         * 根据崩溃日志,对数据类型装换进行保护
                         * Caused by: java.lang.NumberFormatException: Invalid long: "13876880w7665"
                         * at java.lang.Long.invalidLong(Long.java:125)
                         * at java.lang.Long.parse(Long.java:362)
                         * at java.lang.Long.parseLong(Long.java:353)
                         * at java.lang.Long.parseLong(Long.java:319)
                         * at java.lang.Long.valueOf(Long.java:477)
                         * at com.qihoo360.mobilesafe.share.SharedPref.long
                         */
                        if (DEBUG) {
                            Log.e(TAG, "Long.valueOf [catch]", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.e(TAG, "Error [catch]", e);
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setFloat(final String pkgName, final String prefName, String key, float value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);

        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(prefName), values);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        }
    }

    public static float getFloat(final String pkgName, final String prefName, String key, float defVal) {
        float value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, prefName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Float.valueOf(cursor.getString(0)).floatValue();
                    } catch (Exception e) {
                        if (DEBUG) {
                            Log.e(TAG, "Float.valueOf [catch]", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "err", e);
            }
        } catch (Error e) {
            if (DEBUG) {
                Log.e(TAG, "Error [catch]", e);
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void clearAll(final String pkgName,final String prefName) {
        mContext.getContentResolver()
                .delete(SharePrefWrapper.getDBSharedPrefUri(pkgName,prefName), null, null);
    }
}

