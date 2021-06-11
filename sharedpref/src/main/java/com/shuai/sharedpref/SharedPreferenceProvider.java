package com.shuai.sharedpref;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import com.shuai.sharedpref.utils.Util;


public class SharedPreferenceProvider extends ContentProvider {

    private static final int URI_MATCH_CODE_SP_DEFAULT = 1;
    private static final int URI_MATCH_CODE_SP_OTHER = 2;
    public static final String URI_PATH_SP_DEFAULT = "default";
    public static final String URI_PATH_SP_OTHER = "other";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static void initURIMatcher(Context context){
        sURIMatcher.addURI(getAuthority(context), URI_PATH_SP_DEFAULT, URI_MATCH_CODE_SP_DEFAULT);
        sURIMatcher.addURI(getAuthority(context), URI_PATH_SP_OTHER, URI_MATCH_CODE_SP_OTHER);
    }

    public static String getAuthority(Context context){
        return Util.getProviderAuthority(context,SharedPreferenceProvider.class.getName());
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
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int code = sURIMatcher.match(uri);
        SpDbHelper dbHelper = SpDbHelper.getInstance(getContext());

        switch (code) {
        case URI_MATCH_CODE_SP_DEFAULT: {
            int ret = dbHelper.delete(selection, selectionArgs);
            notifyChange(uri,null);
            return ret;
        }

        case URI_MATCH_CODE_SP_OTHER: {
            return dbHelper.deleteOther(selection, selectionArgs);
        }


        default:
            return 0;
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int code = sURIMatcher.match(uri);
        SpDbHelper dbHelper = SpDbHelper.getInstance(getContext());

        switch (code) {
        case URI_MATCH_CODE_SP_DEFAULT: {
            return dbHelper.query(projection, selection, selectionArgs);
        }

        case URI_MATCH_CODE_SP_OTHER: {
            return dbHelper.queryOther(projection, selection, selectionArgs);
        }

        default:
            return null;
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int code = sURIMatcher.match(uri);
        SpDbHelper dbHelper = SpDbHelper.getInstance(getContext());

        switch (code) {
        case URI_MATCH_CODE_SP_DEFAULT: {
            dbHelper.insert(values);
            notifyChange(uri,null);
            return uri;
        }

        case URI_MATCH_CODE_SP_OTHER: {
            dbHelper.insertOther(values);
            return uri;
        }


        default:
            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int code = sURIMatcher.match(uri);
        int count = 0;
        SpDbHelper dbHelper = SpDbHelper.getInstance(getContext());

        switch (code) {
        case URI_MATCH_CODE_SP_DEFAULT: {
            count = dbHelper.update(values, selection, selectionArgs);
            if (count > 0) {
                notifyChange(uri,null);
            }
        }
            break;

        case URI_MATCH_CODE_SP_OTHER: {
            count = dbHelper.updateOther(values, selection, selectionArgs);
        }
            break;

        default:
            break;

        }

        return count;
    }


    private void notifyChange(Uri uri, ContentObserver observer) {
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, observer);
        }
    }

}
