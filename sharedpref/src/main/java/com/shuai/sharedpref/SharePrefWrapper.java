package com.shuai.sharedpref;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.shuai.sharedpref.utils.Util;


public class SharePrefWrapper {
    private static final boolean DEBUG = SharedPrefConfig.getConfig().isDebug();
    private static final String TAG = DEBUG ? "SharePrefWrapper" : "spw";

    public static final String SP_NAME_DEFAULT = "sp_default";
    public static final String SP_NAME_STAT = "sp_stat";
    public static final String SP_NAME_ACCOUNT = "sp_account";
    public static final String SP_NAME_UPDATESTATE = "sp_updatestate";


    private static final Uri URI_DEFAULT = Uri.parse("content://" + SharedPreferenceProvider.AUTHORITY + "/value");
    private static final Uri URI_STAT = Uri.parse("content://" + SharedPreferenceProvider.AUTHORITY + "/stat");
    private static final Uri URI_ACCOUNT = Uri.parse("content://" + SharedPreferenceProvider.AUTHORITY + "/account");
    
    private static Context mContext = null;

    public static void initContext (Context context) {
        mContext = context;
    }

    public static Uri getDBSharedPrefUri(final String prefName) {
        if (null == prefName) {
            return URI_DEFAULT;
        } else if (SP_NAME_STAT.equals(prefName)) {
            return URI_STAT;
        } else if (SP_NAME_ACCOUNT.equals(prefName)) {
            return URI_ACCOUNT;
        } else {
            return URI_DEFAULT;
        }
    }

    public static void removeKey(final String prefName, String key) {
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            contentResolver.delete(SharePrefWrapper.getDBSharedPrefUri(prefName), SpDbHelper.COL_KEY + "=?",
                    new String[] { key });
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "err", e);
            }
        }
    }

    public static boolean contains(final String prefName, String key) {
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(prefName), new String[] { SpDbHelper.COL_VALUE },
                            SpDbHelper.COL_KEY + "=?", new String[] { key }, null);
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

    public static final void setString(final String prefName, String key, String value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[] { key });

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

    public static final String getString(final String prefName, String key, String defVal) {
        String value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(prefName), new String[] { SpDbHelper.COL_VALUE },
                            SpDbHelper.COL_KEY + "=?", new String[] { key }, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    value = cursor.getString(0);

                    /**
                     * java.lang.IllegalStateException: get field slot from row 0
                     * col 14 failed
                     * at android.database.CursorWindow.getLong_native(Native Method)
                     * at android.database.CursorWindow.getLong(CursorWindow.java:411)
                     * at
                     * android.database.AbstractWindowedCursor.getLong(AbstractWindowedCursor.java:139)
                     * at android.database.AbstractCursor.moveToPosition(AbstractCursor.java:225)
                     * at android.database.AbstractCursor.moveToFirst(AbstractCursor.java:279)
                     * at android.database.CursorWrapper.moveToFirst(CursorWrapper.java:117)
                     */
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

    public static String getString(final String prefName, String key) {
        return SharePrefWrapper.getString(prefName, key, null);
    }

    public static final void setBoolean(final String prefName, String key, boolean value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value ? 1 : 0);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[] { key });

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

    public static final boolean getBoolean(final String prefName, String key, boolean defVal) {
        boolean value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(prefName), new String[] { SpDbHelper.COL_VALUE },
                            SpDbHelper.COL_KEY + "=?", new String[] { key }, null);
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
                /**
                 * java.lang.OutOfMemoryError
                 * at android.os.Parcel.readString(Native Method)
                 * at android.content.pm.PackageItemInfo.<init>(PackageItemInfo.java:249)
                 * at android.content.pm.ComponentInfo.<init>(ComponentInfo.java:149)
                 * at android.content.pm.ProviderInfo.<init>(ProviderInfo.java:135)
                 * at android.content.pm.ProviderInfo.<init>(ProviderInfo.java:29)
                 * at android.content.pm.ProviderInfo$1.createFromParcel(ProviderInfo.java:121)
                 * at android.content.pm.ProviderInfo$1.createFromParcel(ProviderInfo.java:119)
                 * at
                 * android.app.IActivityManager$ContentProviderHolder.<init>(IActivityManager.java:419)
                 * at
                 * android.app.IActivityManager$ContentProviderHolder.<init>(IActivityManager.java:384)
                 * at
                 * android.app.IActivityManager$ContentProviderHolder$1.createFromParcel(IActivityManager.java:410)
                 * at
                 * android.app.IActivityManager$ContentProviderHolder$1.createFromParcel(IActivityManager.java:408)
                 * at
                 * android.app.ActivityManagerProxy.getContentProvider(ActivityManagerNative.java:2205)
                 * at android.app.ActivityThread.acquireProvider(ActivityThread.java:4024)
                 * at
                 * android.app.ContextImpl$ApplicationContentResolver.acquireProvider(ContextImpl.java:1624)
                 * at android.content.ContentResolver.acquireProvider(ContentResolver.java:918)
                 * at android.content.ContentResolver.query(ContentResolver.java:305)
                 */
            }
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static final void setInt(final String prefName, String key, int value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[] { key });

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

    public static final int getInt(final String prefName, String key, int defVal) {
        int value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(prefName), new String[] { SpDbHelper.COL_VALUE },
                            SpDbHelper.COL_KEY + "=?", new String[] { key }, null);
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

    public static final void setLong(final String prefName, String key, long value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();

        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[] { key });
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

    public static final long getLong(final String prefName, String key, long defVal) {
        long value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(prefName), new String[] { SpDbHelper.COL_VALUE },
                            SpDbHelper.COL_KEY + "=?", new String[] { key }, null);
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

    public static final void setFloat(final String prefName, String key, float value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);

        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(prefName), values,
                    SpDbHelper.COL_KEY + "=?", new String[] { key });
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

    public static final float getFloat(final String prefName, String key, float defVal) {
        float value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(prefName), new String[] { SpDbHelper.COL_VALUE },
                            SpDbHelper.COL_KEY + "=?", new String[] { key }, null);
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

    public static void clearAll(final String prefName) {
        mContext.getContentResolver()
                .delete(SharePrefWrapper.getDBSharedPrefUri(prefName), null, null);
    }
}
