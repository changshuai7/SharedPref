package com.shuai.sharedpref;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.shuai.sharedpref.utils.Logger;
import com.shuai.sharedpref.utils.Util;


public class SharePrefWrapper {

    //SP类型
    public enum SpName{
        DEFAULT(SharedPreferenceProvider.URI_PATH_SP_DEFAULT),
        // 这里额外申明了另一张表，以备以后需要。
        OTHER(SharedPreferenceProvider.URI_PATH_SP_OTHER);

        private final String uriPath;

        SpName(String uriPath) {
            this.uriPath = uriPath;
        }
    }

    private static Context mContext;
    private static String mAuthority;


    public static void initContext(Context context) {
        mContext = context;
        mAuthority = Util.getProviderAuthority(context, SharedPreferenceProvider.class.getName());
    }

    final static String AuthoritySuffix = ".provider.sharedpref";

    /**
     * 获取URI
     * @param pkgName   应用包名
     * @param spName  表类型
     * @return
     */
    public static Uri getDBSharedPrefUri(final String pkgName, final SpName spName) {
        return Uri.parse("content://" + pkgName + AuthoritySuffix + "/" + (spName == null ? SpName.DEFAULT.uriPath : spName.uriPath));
    }


    public static void removeKey(final String pkgName, final SpName spName, String key) {
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            contentResolver.delete(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), SpDbHelper.COL_KEY + "=?",
                    new String[]{key});
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static boolean contains(final String pkgName, final SpName spName, String key) {
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null && cursor.getCount() > 0) {
                ret = true;
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } finally {
            Util.closeCursor(cursor);
        }
        return ret;
    }

    public static void setString(final String pkgName, final SpName spName, String key, String value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(pkgName,spName), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }


    public static String getString(final String pkgName, final SpName spName, String key, String defVal) {
        String value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    value = cursor.getString(0);
                }
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setBoolean(final String pkgName, final SpName spName, String key, boolean value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value ? 1 : 0);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(pkgName,spName), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static boolean getBoolean(final String pkgName, final SpName spName, String key, boolean defVal) {
        boolean value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Integer.parseInt(cursor.getString(0)) == 1;
                    } catch (Exception e) {
                        Logger.printStackTrace(e);
                    }
                }
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } catch (Error e) {
            Logger.printStackTrace(e);
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setInt(final String pkgName, final SpName spName, String key, int value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(pkgName,spName), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static int getInt(final String pkgName, final SpName spName, String key, int defVal) {
        int value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Integer.parseInt(cursor.getString(0));
                    } catch (Exception e) {
                        Logger.printStackTrace(e);
                    }
                }
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } catch (Error e) {
            Logger.printStackTrace(e);
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setLong(final String pkgName, final SpName spName, String key, long value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();

        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(pkgName,spName), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static long getLong(final String pkgName, final SpName spName, String key, long defVal) {
        long value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Long.parseLong(cursor.getString(0));
                    } catch (Exception e) {
                        Logger.printStackTrace(e);
                    }
                }
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } catch (Error e) {
            Logger.printStackTrace(e);
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void setFloat(final String pkgName, final SpName spName, String key, float value) {
        ContentValues values = new ContentValues();
        values.put(SpDbHelper.COL_VALUE, value);

        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), values,
                    SpDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SpDbHelper.COL_KEY, key);
                contentResolver.insert(SharePrefWrapper.getDBSharedPrefUri(pkgName,spName), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static float getFloat(final String pkgName, final SpName spName, String key, float defVal) {
        float value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharePrefWrapper.getDBSharedPrefUri(pkgName, spName), new String[]{SpDbHelper.COL_VALUE},
                            SpDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Float.parseFloat(cursor.getString(0));
                    } catch (Exception e) {
                        Logger.printStackTrace(e);
                    }
                }
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        } catch (Error e) {
            Logger.printStackTrace(e);
        } finally {
            Util.closeCursor(cursor);
        }
        return value;
    }

    public static void clearAll(final String pkgName,final SpName spName) {
        mContext.getContentResolver()
                .delete(SharePrefWrapper.getDBSharedPrefUri(pkgName,spName), null, null);
    }
}

