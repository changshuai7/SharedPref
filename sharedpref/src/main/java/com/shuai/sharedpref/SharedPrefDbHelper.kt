package com.shuai.sharedpref

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shuai.sharedpref.utils.Logger

internal class SharedPrefDbHelper private constructor(private val mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {

    private var mDb: SQLiteDatabase? = null
        get() {
            try {
                if (field == null) field = writableDatabase
            } catch (e: Exception) {
                Logger.printStackTrace(e)
                field = null
            }
            return field
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SharedPrefType.DEFAULT.tableName + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE + " TEXT);")
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SharedPrefType.OTHER.tableName + " (" + COL_KEY + " TEXT NOT NULL, " + COL_VALUE + " TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    override fun onOpen(db: SQLiteDatabase) {}

    fun query(type: SharedPrefType, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?): Cursor? {
        // 返回游标
        return mDb?.query(type.tableName, projection, selection, selectionArgs, null, null, null)
    }

    fun insert(type: SharedPrefType, values: ContentValues?): Long {
        // 返回新插入行的行ID，如果发生错误则为-1
        return mDb?.insert(type.tableName, null, values) ?: -1
    }

    fun update(type: SharedPrefType, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        // 返回受影响的行数
        return mDb?.update(type.tableName, values, selection, selectionArgs) ?: 0
    }

    fun delete(type: SharedPrefType, selection: String?, selectionArgs: Array<out String>?): Int {
        // 返回受影响的行数
        return mDb?.delete(type.tableName, selection, selectionArgs) ?: 0
    }

    companion object {
        private const val DB_NAME = "shared_pref.db"
        private const val DB_VERSION = 1
        const val COL_KEY = "K"
        const val COL_VALUE = "V"
        val instance: SharedPrefDbHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SharedPrefDbHelper(SharedPref.context.applicationContext)
        }
    }


}