package com.shuai.sharedpref

import android.annotation.SuppressLint
import android.content.Context
import com.shuai.sharedpref.utils.Logger

class SharedPref {

    companion object {

        // 全局 applicationContext
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set

        var isAllowExternalVisit = false //是否允许外部访问
            private set

        /**
         * 请在Application中完成初始化操作
         *
         * @param context            上下文
         * @param allowExternalVisit 是否允许外部应用程序访问数据
         */
        @JvmOverloads
        @JvmStatic
        fun init(context: Context,allowExternalVisit: Boolean = false) {
            this.context = context.applicationContext
            isAllowExternalVisit = allowExternalVisit
            Logger.debug(BuildConfig.DEBUG)
        }

        /// 清除所有数据
        @JvmOverloads
        @JvmStatic
        fun clearAll(pkgName: String = context.packageName) {
            SharedPrefWrapper.clearAll(context, pkgName, SharedPrefType.DEFAULT)
        }

        /// 删除
        @JvmOverloads
        @JvmStatic
        fun removeKey(key: String, pkgName: String = context.packageName) {
            SharedPrefWrapper.removeKey(context, pkgName, SharedPrefType.DEFAULT, key)
        }

        /// 包含
        @JvmOverloads
        @JvmStatic
        fun contains(key: String, pkgName: String = context.packageName): Boolean {
            return SharedPrefWrapper.contains(context, pkgName, SharedPrefType.DEFAULT, key)
        }

        /// String
        @JvmOverloads
        @JvmStatic
        fun setString(key: String, value: String, pkgName: String = context.packageName) {
            SharedPrefWrapper.setString(context, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getString(key: String, defValue: String, pkgName: String = context.packageName): String {
            return SharedPrefWrapper.getString(context, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Boolean
        @JvmOverloads
        @JvmStatic
        fun setBoolean(key: String, value: Boolean, pkgName: String = context.packageName) {
            SharedPrefWrapper.setBoolean(context, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getBoolean(key: String, defValue: Boolean, pkgName: String = context.packageName): Boolean {
            return SharedPrefWrapper.getBoolean(context, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Int
        @JvmOverloads
        @JvmStatic
        fun setInt(key: String, value: Int, pkgName: String = context.packageName) {
            SharedPrefWrapper.setInt(context, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getInt(key: String, defValue: Int, pkgName: String = context.packageName): Int {
            return SharedPrefWrapper.getInt(context, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Long
        @JvmOverloads
        @JvmStatic
        fun setLong(key: String, value: Long, pkgName: String = context.packageName) {
            SharedPrefWrapper.setLong(context, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getLong(key: String, defValue: Long, pkgName: String = context.packageName): Long {
            return SharedPrefWrapper.getLong(context, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Float
        @JvmOverloads
        @JvmStatic
        fun setFloat(key: String, value: Float, pkgName: String = context.packageName) {
            SharedPrefWrapper.setFloat(context, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getFloat(key: String, defValue: Float, pkgName: String = context.packageName): Float {
            return SharedPrefWrapper.getFloat(context, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Double
        @JvmOverloads
        @JvmStatic
        fun setDouble(key: String, value: Double, pkgName: String = context.packageName) {
            SharedPrefWrapper.setDouble(context, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getDouble(key: String, defValue: Double, pkgName: String = context.packageName): Double {
            return SharedPrefWrapper.getDouble(context, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }
    }
}