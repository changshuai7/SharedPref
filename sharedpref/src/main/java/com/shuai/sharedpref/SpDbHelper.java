package com.shuai.sharedpref;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class SpDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "SpDbOpenHelper";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String DB_NAME = "sp.db";
    private static final int DB_VER = 3;

    /** ver 1 */
    private static final String DB_TABLE_SP = "t_sp";
    private static final String DB_TABLE_OTHER = "t_other";

    public static final String COL_KEY = "key";
    public static final String COL_VALUE = "value";

    private static SpDbHelper mInstance;
    private SQLiteDatabase mDb;
    private Context mContext;

    public synchronized static SpDbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SpDbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private SpDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);

        if (DEBUG)
            Log.d(TAG, "get instance");

        mContext = context;

        checkAndOpenDb();

        if (DEBUG)
            Log.d(TAG, "get instance done");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DEBUG)
            Log.d(TAG, "onCreate");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_TABLE_SP + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE
                + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_TABLE_OTHER + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE
                + " TEXT);");

        if (DEBUG)
            Log.d(TAG, "onCreate done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DEBUG)
            Log.d(TAG, "onUpgrade, old: " + oldVersion + " new: " + newVersion);
    }

    @SuppressLint({ "NewApi", "Override", "unused" })
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        if (DEBUG)
            Log.d(TAG, "onOpen");
    };

    private boolean checkAndOpenDb() {
        if (mDb != null) {
            return true;
        }

        try {
            mDb = getWritableDatabase();
            return true;
        } catch (Exception e) {
            if (DEBUG)
                Log.e(TAG, "Err", e);
            mDb = null;
        }

        return false;
    }

    /**
     * 注意：用完cursor需要自己关闭
     *
     * @return 有效的cursor或者null
     */
    Cursor query(String[] projection, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return null;
        }

        return mDb.query(DB_TABLE_SP, projection, selection, selectionArgs, null, null, null);
    }

    void insert(ContentValues values) {
        if (!checkAndOpenDb()) {
            return;
        }

        mDb.insert(DB_TABLE_SP, null, values);
    }

    int update(ContentValues values, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.update(DB_TABLE_SP, values, selection, selectionArgs);
    }

    int delete(String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.delete(DB_TABLE_SP, selection, selectionArgs);
    }

    /**
     * 注意：用完cursor需要自己关闭
     *
     * @return 有效的cursor或者null
     */
    Cursor queryOther(String[] projection, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return null;
        }

        return mDb.query(DB_TABLE_OTHER, projection, selection, selectionArgs, null, null, null);
    }


    void insertOther(ContentValues values) {
        if (!checkAndOpenDb()) {
            return;
        }

        mDb.insert(DB_TABLE_OTHER, null, values);
    }


    int updateOther(ContentValues values, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.update(DB_TABLE_OTHER, values, selection, selectionArgs);
    }


    int deleteOther(String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.delete(DB_TABLE_OTHER, selection, selectionArgs);
    }

}
