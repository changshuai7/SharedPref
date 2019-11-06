package com.shuai.sharedpref;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.shuai.sharedpref.utils.Util;


public class SharedPreferenceProvider extends ContentProvider {

    private static final int SP_VALUE = 1;
    private static final int SP_STAT = 2;
    private static final int SP_ACCOUNT = 3;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static void initURIMatcher(Context context){
        sURIMatcher.addURI(getAuthority(context), "value", SP_VALUE);
        sURIMatcher.addURI(getAuthority(context), "stat", SP_STAT);
        sURIMatcher.addURI(getAuthority(context), "account", SP_ACCOUNT);
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
        case SP_VALUE: {
            int ret = dbHelper.delete(selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return ret;
        }

        case SP_STAT: {
            return dbHelper.deleteStat(selection, selectionArgs);
        }

        case SP_ACCOUNT: {
            return dbHelper.deleteAccount(selection, selectionArgs);
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
        case SP_VALUE: {
            return dbHelper.query(projection, selection, selectionArgs);
        }

        case SP_STAT: {
            return dbHelper.queryStat(projection, selection, selectionArgs);
        }

        case SP_ACCOUNT: {
            return dbHelper.queryAccount(projection, selection, selectionArgs);
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
        case SP_VALUE: {
            dbHelper.insert(values);
            getContext().getContentResolver().notifyChange(uri, null);
            return uri;
        }

        case SP_STAT: {
            dbHelper.insertStat(values);
            return uri;
        }

        case SP_ACCOUNT: {
            dbHelper.insertAccount(values);
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
        case SP_VALUE: {
            count = dbHelper.update(values, selection, selectionArgs);
            if (count > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }
            break;

        case SP_STAT: {
            count = dbHelper.updateStat(values, selection, selectionArgs);
        }
            break;

        case SP_ACCOUNT: {
            count = dbHelper.updateAccount(values, selection, selectionArgs);
        }
            break;

        default:
            break;

        }

        return count;
    }

}
