package com.shuai.sharedpref.utils;

import android.database.Cursor;

public class Util {
    /**
     * 关闭Cursor
     *
     * @param cursor
     */
    public static void closeCursor(Cursor cursor) {
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception ex) {
        }
    }
}
