package com.shuai.sharedpref;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.shuai.sharedpref.utils.Logger;
import com.shuai.sharedpref.utils.Util;

import static com.shuai.sharedpref.SharedPrefProvider.ParameterKeyCallingPkgName;


public class SharedPrefWrapper {

    private static Context mContext;
    private static String mAuthority;


    public static void initContext(Context context) {
        mContext = context;
        mAuthority = Util.getProviderAuthority(context, SharedPrefProvider.class.getName());
    }

    final static String AuthoritySuffix = ".provider.sharedpref";

    /**
     * 获取URI
     *
     * @param pkgName        要被访问的 应用包名
     * @param sharedPrefType 表类型
     * @return
     */
    public static Uri getDBSharedPrefUri(final String pkgName, final SharedPrefType sharedPrefType) {

        // 形如=》 content://com.baidu.com.provider.sharedpref/default?callingPkgName=com.baidu.com
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content")
                .authority(pkgName + AuthoritySuffix)
                .appendPath(sharedPrefType != null ? sharedPrefType.uriPath : SharedPrefType.DEFAULT.uriPath)
                .appendQueryParameter(ParameterKeyCallingPkgName, mContext.getPackageName());// 访问者的应用包名
        Uri uri = builder.build();
        Logger.d("getDBSharedPrefUri => Uri = " + uri.toString());
        return uri;

    }


    public static void removeKey(final String pkgName, final SharedPrefType sharedPrefType, String key) {
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            contentResolver.delete(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), SharedPrefDbHelper.COL_KEY + "=?",
                    new String[]{key});
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static boolean contains(final String pkgName, final SharedPrefType sharedPrefType, String key) {
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
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

    public static void setString(final String pkgName, final SharedPrefType sharedPrefType, String key, String value) {
        ContentValues values = new ContentValues();
        values.put(SharedPrefDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values,
                    SharedPrefDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SharedPrefDbHelper.COL_KEY, key);
                contentResolver.insert(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }


    public static String getString(final String pkgName, final SharedPrefType sharedPrefType, String key, String defVal) {
        String value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
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

    public static void setBoolean(final String pkgName, final SharedPrefType sharedPrefType, String key, boolean value) {
        ContentValues values = new ContentValues();
        values.put(SharedPrefDbHelper.COL_VALUE, value ? 1 : 0);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values,
                    SharedPrefDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SharedPrefDbHelper.COL_KEY, key);
                contentResolver.insert(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static boolean getBoolean(final String pkgName, final SharedPrefType sharedPrefType, String key, boolean defVal) {
        boolean value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
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

    public static void setInt(final String pkgName, final SharedPrefType sharedPrefType, String key, int value) {
        ContentValues values = new ContentValues();
        values.put(SharedPrefDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values,
                    SharedPrefDbHelper.COL_KEY + "=?", new String[]{key});

            if (count == 0) {
                values.put(SharedPrefDbHelper.COL_KEY, key);
                contentResolver.insert(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static int getInt(final String pkgName, final SharedPrefType sharedPrefType, String key, int defVal) {
        int value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
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

    public static void setLong(final String pkgName, final SharedPrefType sharedPrefType, String key, long value) {
        ContentValues values = new ContentValues();
        values.put(SharedPrefDbHelper.COL_VALUE, value);
        ContentResolver contentResolver = mContext.getContentResolver();

        try {
            int count = contentResolver.update(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values,
                    SharedPrefDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SharedPrefDbHelper.COL_KEY, key);
                contentResolver.insert(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static long getLong(final String pkgName, final SharedPrefType sharedPrefType, String key, long defVal) {
        long value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
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

    public static void setFloat(final String pkgName, final SharedPrefType sharedPrefType, String key, float value) {
        ContentValues values = new ContentValues();
        values.put(SharedPrefDbHelper.COL_VALUE, value);

        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values,
                    SharedPrefDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SharedPrefDbHelper.COL_KEY, key);
                contentResolver.insert(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static float getFloat(final String pkgName, final SharedPrefType sharedPrefType, String key, float defVal) {
        float value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
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


    public static void setDouble(final String pkgName, final SharedPrefType sharedPrefType, String key, double value) {
        ContentValues values = new ContentValues();
        values.put(SharedPrefDbHelper.COL_VALUE, value);

        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            int count = contentResolver.update(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values,
                    SharedPrefDbHelper.COL_KEY + "=?", new String[]{key});
            if (count == 0) {
                values.put(SharedPrefDbHelper.COL_KEY, key);
                contentResolver.insert(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), values);
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
        }
    }

    public static double getDouble(final String pkgName, final SharedPrefType sharedPrefType, String key, double defVal) {
        double value = defVal;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), new String[]{SharedPrefDbHelper.COL_VALUE},
                            SharedPrefDbHelper.COL_KEY + "=?", new String[]{key}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        value = Double.parseDouble(cursor.getString(0));
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



    public static void clearAll(final String pkgName, final SharedPrefType sharedPrefType) {
        mContext.getContentResolver()
                .delete(SharedPrefWrapper.getDBSharedPrefUri(pkgName, sharedPrefType), null, null);
    }
}

