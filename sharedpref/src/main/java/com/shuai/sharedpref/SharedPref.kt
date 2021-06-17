package com.shuai.sharedpref

import android.app.Application
import com.shuai.sharedpref.utils.Logger

class SharedPref {

    companion object {

        lateinit var app: Application
            private set

        var isAllowExternalVisit = false //是否允许外部访问
            private set

        /**
         * 请在Application中完成初始化操作
         *
         * @param context            上下文
         * @param debug              是否是debug模式
         * @param allowExternalVisit 是否允许外部应用程序访问数据
         */
        @JvmOverloads
        @JvmStatic
        fun init(context: Application, debug: Boolean, allowExternalVisit: Boolean = false) {
            app = context
            isAllowExternalVisit = allowExternalVisit
            Logger.debug(debug)
        }

        /// 清除所有数据
        @JvmOverloads
        @JvmStatic
        fun clearAll(pkgName: String = app.packageName) {
            SharedPrefWrapper.clearAll(app, pkgName, SharedPrefType.DEFAULT)
        }

        /// 删除
        @JvmOverloads
        @JvmStatic
        fun removeKey(key: String, pkgName: String = app.packageName) {
            SharedPrefWrapper.removeKey(app, pkgName, SharedPrefType.DEFAULT, key)
        }

        /// 包含
        @JvmOverloads
        @JvmStatic
        fun contains(key: String, pkgName: String = app.packageName): Boolean {
            return SharedPrefWrapper.contains(app, pkgName, SharedPrefType.DEFAULT, key)
        }

        /// String
        @JvmOverloads
        @JvmStatic
        fun setString(key: String, value: String, pkgName: String = app.packageName) {
            SharedPrefWrapper.setString(app, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getString(key: String, defValue: String, pkgName: String = app.packageName): String {
            return SharedPrefWrapper.getString(app, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Boolean
        @JvmOverloads
        @JvmStatic
        fun setBoolean(key: String, value: Boolean, pkgName: String = app.packageName) {
            SharedPrefWrapper.setBoolean(app, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getBoolean(key: String, defValue: Boolean, pkgName: String = app.packageName): Boolean {
            return SharedPrefWrapper.getBoolean(app, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Int
        @JvmOverloads
        @JvmStatic
        fun setInt(key: String, value: Int, pkgName: String = app.packageName) {
            SharedPrefWrapper.setInt(app, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getInt(key: String, defValue: Int, pkgName: String = app.packageName): Int {
            return SharedPrefWrapper.getInt(app, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Long
        @JvmOverloads
        @JvmStatic
        fun setLong(key: String, value: Long, pkgName: String = app.packageName) {
            SharedPrefWrapper.setLong(app, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getLong(key: String, defValue: Long, pkgName: String = app.packageName): Long {
            return SharedPrefWrapper.getLong(app, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Float
        @JvmOverloads
        @JvmStatic
        fun setFloat(key: String, value: Float, pkgName: String = app.packageName) {
            SharedPrefWrapper.setFloat(app, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getFloat(key: String, defValue: Float, pkgName: String = app.packageName): Float {
            return SharedPrefWrapper.getFloat(app, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }

        /// Double
        @JvmOverloads
        @JvmStatic
        fun setDouble(key: String, value: Double, pkgName: String = app.packageName) {
            SharedPrefWrapper.setDouble(app, pkgName, SharedPrefType.DEFAULT, key, value)
        }

        @JvmOverloads
        @JvmStatic
        fun getDouble(key: String, defValue: Double, pkgName: String = app.packageName): Double {
            return SharedPrefWrapper.getDouble(app, pkgName, SharedPrefType.DEFAULT, key, defValue)
        }
    }
}