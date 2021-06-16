package com.shuai.sharedpref;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import com.shuai.sharedpref.utils.Util;


public class SharedPrefProvider extends ContentProvider {

    private static final int URI_MATCH_CODE_SP_DEFAULT = 1;
    private static final int URI_MATCH_CODE_SP_OTHER = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String ParameterKeyCallingPkgName = "callingPkgName";//参数Key：调用方的包名

    private static void initURIMatcher(Context context) {
        sURIMatcher.addURI(getAuthority(context), SharedPrefType.DEFAULT.uriPath, URI_MATCH_CODE_SP_DEFAULT);
        sURIMatcher.addURI(getAuthority(context), SharedPrefType.OTHER.uriPath, URI_MATCH_CODE_SP_OTHER);
    }

    public static String getAuthority(Context context) {
        return Util.getProviderAuthority(context, SharedPrefProvider.class.getName());
    }

    @Override
    public boolean onCreate() {
        initURIMatcher(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {

        if (!permissionAllow(uri)) {
            return 0;
        }

        final int code = sURIMatcher.match(uri);
        SharedPrefDbHelper dbHelper = SharedPrefDbHelper.getInstance(getContext());

        switch (code) {
            case URI_MATCH_CODE_SP_DEFAULT: {
                int ret = dbHelper.delete(SharedPrefType.DEFAULT, selection, selectionArgs);
                notifyChange(uri, null);
                return ret;
            }

            case URI_MATCH_CODE_SP_OTHER: {
                int ret = dbHelper.delete(SharedPrefType.OTHER, selection, selectionArgs);
                notifyChange(uri, null);
                return ret;
            }

            default:
                return 0;
        }

    }

    @Override
    public synchronized Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if (!permissionAllow(uri)) {
            return null;
        }

        final int code = sURIMatcher.match(uri);
        SharedPrefDbHelper dbHelper = SharedPrefDbHelper.getInstance(getContext());

        switch (code) {
            case URI_MATCH_CODE_SP_DEFAULT: {
                return dbHelper.query(SharedPrefType.DEFAULT, projection, selection, selectionArgs);
            }

            case URI_MATCH_CODE_SP_OTHER: {
                return dbHelper.query(SharedPrefType.OTHER, projection, selection, selectionArgs);
            }

            default:
                return null;
        }

    }

    @Override
    public synchronized Uri insert(Uri uri, ContentValues values) {

        if (!permissionAllow(uri)) {
            return null;
        }

        final int code = sURIMatcher.match(uri);
        SharedPrefDbHelper dbHelper = SharedPrefDbHelper.getInstance(getContext());

        switch (code) {
            case URI_MATCH_CODE_SP_DEFAULT: {
                dbHelper.insert(SharedPrefType.DEFAULT, values);
                notifyChange(uri, null);
                return uri;
            }

            case URI_MATCH_CODE_SP_OTHER: {
                dbHelper.insert(SharedPrefType.OTHER, values);
                notifyChange(uri, null);
                return uri;
            }

            default:
                return null;
        }
    }

    @Override
    public synchronized int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (!permissionAllow(uri)) {
            return 0;
        }

        final int code = sURIMatcher.match(uri);
        int count = 0;
        SharedPrefDbHelper dbHelper = SharedPrefDbHelper.getInstance(getContext());

        switch (code) {
            case URI_MATCH_CODE_SP_DEFAULT: {
                count = dbHelper.update(SharedPrefType.DEFAULT, values, selection, selectionArgs);
                if (count > 0) {
                    notifyChange(uri, null);
                }
            }
            break;

            case URI_MATCH_CODE_SP_OTHER: {
                count = dbHelper.update(SharedPrefType.OTHER, values, selection, selectionArgs);
                if (count > 0) {
                    notifyChange(uri, null);
                }
            }
            break;

            default:
                break;

        }

        return count;
    }

    // 变更通知。
    private synchronized void notifyChange(Uri uri, ContentObserver observer) {
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, observer);
        }
    }

    // 是否允许访问。
    private boolean permissionAllow(Uri uri) {

        // 从ContentProvider中获取调用者的PackageName的方法为：getCallingPackage();但是此API要求miniSDK为19以上。不够通用。

        if (uri != null && getContext() != null) {
            //当前应用包名
            String curPkgName = getContext().getPackageName();
            //调用者应用包名
            String callPkgName = uri.getQueryParameter(ParameterKeyCallingPkgName);

            if (curPkgName.equals(callPkgName)) {
                //当前应用，可直接访问
                return true;
            } else {
                //外部应用，需要看配置是否允许访问
                return SharedPref.isAllowExternalVisit();
            }
        }
        return false;

    }
}
