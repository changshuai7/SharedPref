package com.shuai.sharedpref;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shuai.sharedpref.utils.Logger;


public class SharedPrefDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "SharedPrefDbHelper";
    private static final String DB_NAME = "shared_pref.db";
    private static final int DB_VERSION = 1;

    public static final String COL_KEY = "K";
    public static final String COL_VALUE = "V";

    private static SharedPrefDbHelper mInstance;
    private SQLiteDatabase mDb;
    private Context mContext;

    public synchronized static SharedPrefDbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefDbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private SharedPrefDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;

        checkAndOpenDb();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SharedPrefType.DEFAULT.tableName + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SharedPrefType.OTHER.tableName + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Logger.d(TAG, "onUpgrade, old: " + oldVersion + " new: " + newVersion);
    }

    @SuppressLint({"NewApi", "Override", "unused"})
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        Logger.d(TAG, "onOpen");
    }

    private boolean checkAndOpenDb() {
        if (mDb != null) {
            return true;
        }

        try {
            mDb = getWritableDatabase();
            return true;
        } catch (Exception e) {
            Logger.printStackTrace(e);
            mDb = null;
        }

        return false;
    }

    /**
     * 查询
     * 注意：用完cursor需要自己关闭
     *
     * @return 有效的cursor或者null
     */
    Cursor query(SharedPrefType type, String[] projection, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return null;
        }

        return mDb.query(type.tableName, projection, selection, selectionArgs, null, null, null);
    }

    /**
     * 插入
     *
     * @param type
     * @param values
     */
    void insert(SharedPrefType type, ContentValues values) {
        if (!checkAndOpenDb()) {
            return;
        }

        mDb.insert(type.tableName, null, values);
    }

    /**
     * 更新
     *
     * @param type
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    int update(SharedPrefType type, ContentValues values, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.update(type.tableName, values, selection, selectionArgs);
    }

    /**
     * 删除
     *
     * @param type
     * @param selection
     * @param selectionArgs
     * @return
     */
    int delete(SharedPrefType type, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.delete(type.tableName, selection, selectionArgs);
    }

}
