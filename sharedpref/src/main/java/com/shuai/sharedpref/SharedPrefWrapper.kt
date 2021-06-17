package com.shuai.sharedpref

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.shuai.sharedpref.utils.Logger
import com.shuai.sharedpref.utils.Util

internal class SharedPrefWrapper {

    companion object {

        private const val AuthoritySuffix = ".provider.sharedpref"

        /**
         * 获取URI
         *
         * @param pkgName        要被访问的 应用包名
         * @param sharedPrefType 表类型
         * @return
         */
        private fun getDBSharedPrefUri(context: Context, pkgName: String, sharedPrefType: SharedPrefType): Uri {

            // 形如=》 content://com.baidu.com.provider.sharedpref/default?callingPkgName=com.baidu.com
            val builder = Uri.Builder()
            builder.scheme("content")
                    .authority(pkgName + AuthoritySuffix)
                    .appendPath(sharedPrefType.uriPath)
                    .appendQueryParameter(SharedPrefProvider.Companion.ParameterKeyCallingPkgName, context.packageName) // 访问者的应用包名
            val uri = builder.build()
            Logger.d("getDBSharedPrefUri => Uri = $uri")
            return uri
        }

        fun removeKey(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String) {
            val contentResolver = context.contentResolver
            try {
                contentResolver.delete(getDBSharedPrefUri(context, pkgName, sharedPrefType), SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun contains(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String): Boolean {
            var cursor: Cursor? = null
            var ret = false
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null && cursor.count > 0) {
                    ret = true
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return ret
        }

        fun setString(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, value: String) {
            val values = ContentValues()
            values.put(SharedPrefDbHelper.Companion.COL_VALUE, value)
            val contentResolver = context.contentResolver
            try {
                val count = contentResolver.update(getDBSharedPrefUri(context, pkgName, sharedPrefType), values,
                        SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
                if (count == 0) {
                    values.put(SharedPrefDbHelper.Companion.COL_KEY, key)
                    contentResolver.insert(getDBSharedPrefUri(context, pkgName, sharedPrefType), values)
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun getString(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, defVal: String): String {
            var value = defVal
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        value = cursor.getString(0)
                    }
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return value
        }

        fun setBoolean(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, value: Boolean) {
            val values = ContentValues()
            values.put(SharedPrefDbHelper.Companion.COL_VALUE, if (value) 1 else 0)
            val contentResolver = context.contentResolver
            try {
                val count = contentResolver.update(getDBSharedPrefUri(context, pkgName, sharedPrefType), values,
                        SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
                if (count == 0) {
                    values.put(SharedPrefDbHelper.Companion.COL_KEY, key)
                    contentResolver.insert(getDBSharedPrefUri(context, pkgName, sharedPrefType), values)
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun getBoolean(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, defVal: Boolean): Boolean {
            var value = defVal
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            value = cursor.getString(0).toInt() == 1
                        } catch (e: Exception) {
                            Logger.printStackTrace(e)
                        }
                    }
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } catch (e: Error) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return value
        }

        fun setInt(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, value: Int) {
            val values = ContentValues()
            values.put(SharedPrefDbHelper.Companion.COL_VALUE, value)
            val contentResolver = context.contentResolver
            try {
                val count = contentResolver.update(getDBSharedPrefUri(context, pkgName, sharedPrefType), values,
                        SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
                if (count == 0) {
                    values.put(SharedPrefDbHelper.Companion.COL_KEY, key)
                    contentResolver.insert(getDBSharedPrefUri(context, pkgName, sharedPrefType), values)
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun getInt(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, defVal: Int): Int {
            var value = defVal
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            value = cursor.getString(0).toInt()
                        } catch (e: Exception) {
                            Logger.printStackTrace(e)
                        }
                    }
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } catch (e: Error) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return value
        }

        fun setLong(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, value: Long) {
            val values = ContentValues()
            values.put(SharedPrefDbHelper.Companion.COL_VALUE, value)
            val contentResolver = context.contentResolver
            try {
                val count = contentResolver.update(getDBSharedPrefUri(context, pkgName, sharedPrefType), values,
                        SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
                if (count == 0) {
                    values.put(SharedPrefDbHelper.Companion.COL_KEY, key)
                    contentResolver.insert(getDBSharedPrefUri(context, pkgName, sharedPrefType), values)
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun getLong(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, defVal: Long): Long {
            var value = defVal
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            value = cursor.getString(0).toLong()
                        } catch (e: Exception) {
                            Logger.printStackTrace(e)
                        }
                    }
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } catch (e: Error) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return value
        }

        fun setFloat(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, value: Float) {
            val values = ContentValues()
            values.put(SharedPrefDbHelper.Companion.COL_VALUE, value)
            val contentResolver = context.contentResolver
            try {
                val count = contentResolver.update(getDBSharedPrefUri(context, pkgName, sharedPrefType), values,
                        SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
                if (count == 0) {
                    values.put(SharedPrefDbHelper.Companion.COL_KEY, key)
                    contentResolver.insert(getDBSharedPrefUri(context, pkgName, sharedPrefType), values)
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun getFloat(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, defVal: Float): Float {
            var value = defVal
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            value = cursor.getString(0).toFloat()
                        } catch (e: Exception) {
                            Logger.printStackTrace(e)
                        }
                    }
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } catch (e: Error) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return value
        }

        fun setDouble(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, value: Double) {
            val values = ContentValues()
            values.put(SharedPrefDbHelper.Companion.COL_VALUE, value)
            val contentResolver = context.contentResolver
            try {
                val count = contentResolver.update(getDBSharedPrefUri(context, pkgName, sharedPrefType), values,
                        SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key))
                if (count == 0) {
                    values.put(SharedPrefDbHelper.Companion.COL_KEY, key)
                    contentResolver.insert(getDBSharedPrefUri(context, pkgName, sharedPrefType), values)
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            }
        }

        fun getDouble(context: Context, pkgName: String, sharedPrefType: SharedPrefType, key: String, defVal: Double): Double {
            var value = defVal
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                        .query(getDBSharedPrefUri(context, pkgName, sharedPrefType), arrayOf(SharedPrefDbHelper.Companion.COL_VALUE),
                                SharedPrefDbHelper.Companion.COL_KEY + "=?", arrayOf(key), null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        try {
                            value = cursor.getString(0).toDouble()
                        } catch (e: Exception) {
                            Logger.printStackTrace(e)
                        }
                    }
                }
            } catch (e: Exception) {
                Logger.printStackTrace(e)
            } catch (e: Error) {
                Logger.printStackTrace(e)
            } finally {
                Util.closeCursor(cursor)
            }
            return value
        }

        fun clearAll(context: Context, pkgName: String, sharedPrefType: SharedPrefType) {
            context.contentResolver
                    .delete(getDBSharedPrefUri(context, pkgName, sharedPrefType), null, null)
        }
    }

}