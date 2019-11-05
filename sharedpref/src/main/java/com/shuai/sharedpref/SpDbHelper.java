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
    private static final boolean DEBUG = SharedPrefConfig.getConfig().isDebug();

    private static final String DB_NAME = "sp.db";
    private static final int DB_VER = 3;

    /** ver 1 */
    private static final String DB_TABLE_SP = "t_sp";
    private static final String DB_TABLE_STAT = "t_stat";
    /** ver 2 */
    private static final String DB_TABLE_ACCOUNT = "t_account_data";

    public static final String COL_KEY = "key";
    public static final String COL_VALUE = "value";

    public static final int TYPE_STRING = 1;
    public static final int TYPE_INT = 2;
    public static final int TYPE_LONG = 3;
    public static final int TYPE_BOOLEAN = 4;
    public static final int TYPE_FLOAT = 5;

    public static final String[] PROJECTIONS = new String[] {COL_KEY, COL_VALUE};

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
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_TABLE_STAT + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE
                + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_TABLE_ACCOUNT + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE
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
    Cursor queryStat(String[] projection, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return null;
        }

        return mDb.query(DB_TABLE_STAT, projection, selection, selectionArgs, null, null, null);
    }

    Cursor queryAccount(String[] projection, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return null;
        }

        return mDb.query(DB_TABLE_ACCOUNT, projection, selection, selectionArgs, null, null, null);
    }

    void insertStat(ContentValues values) {
        if (!checkAndOpenDb()) {
            return;
        }

        mDb.insert(DB_TABLE_STAT, null, values);
    }

    void insertAccount(ContentValues values) {
        if (!checkAndOpenDb()) {
            return;
        }

        mDb.insert(DB_TABLE_ACCOUNT, null, values);
    }

    int updateStat(ContentValues values, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.update(DB_TABLE_STAT, values, selection, selectionArgs);
    }

    int updateAccount(ContentValues values, String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.update(DB_TABLE_ACCOUNT, values, selection, selectionArgs);
    }

    int deleteStat(String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.delete(DB_TABLE_STAT, selection, selectionArgs);
    }

    int deleteAccount(String selection, String[] selectionArgs) {
        if (!checkAndOpenDb()) {
            return 0;
        }

        return mDb.delete(DB_TABLE_ACCOUNT, selection, selectionArgs);
    }
}
