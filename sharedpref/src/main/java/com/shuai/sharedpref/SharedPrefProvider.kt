package com.shuai.sharedpref

import android.content.*
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import com.shuai.sharedpref.utils.Util

internal class SharedPrefProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        if (context != null) {
            initURIMatcher(context as Context)
        }
        return true
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    @Synchronized
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        if (!permissionAllow(uri)) {
            return 0
        }
        val code = sURIMatcher.match(uri)
        val dbHelper: SharedPrefDbHelper = SharedPrefDbHelper.instance
        return when (code) {
            URI_MATCH_CODE_SP_DEFAULT -> {
                val ret = dbHelper.delete(SharedPrefType.DEFAULT, selection, selectionArgs)
                if (ret > 0) {
                    notifyChange(uri, null)
                }
                ret
            }
            URI_MATCH_CODE_SP_OTHER -> {
                val ret = dbHelper.delete(SharedPrefType.OTHER, selection, selectionArgs)
                if (ret > 0) {
                    notifyChange(uri, null)
                }
                ret
            }
            else -> 0
        }
    }

    @Synchronized
    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        if (!permissionAllow(uri)) {
            return null
        }
        val code = sURIMatcher.match(uri)
        val dbHelper: SharedPrefDbHelper = SharedPrefDbHelper.instance
        return when (code) {
            URI_MATCH_CODE_SP_DEFAULT -> {
                dbHelper.query(SharedPrefType.DEFAULT, projection, selection, selectionArgs)
            }
            URI_MATCH_CODE_SP_OTHER -> {
                dbHelper.query(SharedPrefType.OTHER, projection, selection, selectionArgs)
            }
            else -> null
        }
    }

    @Synchronized
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (!permissionAllow(uri)) {
            return null
        }
        val code = sURIMatcher.match(uri)
        val dbHelper: SharedPrefDbHelper = SharedPrefDbHelper.instance
        return when (code) {
            URI_MATCH_CODE_SP_DEFAULT -> {
                dbHelper.insert(SharedPrefType.DEFAULT, values)
                notifyChange(uri, null)
                uri
            }
            URI_MATCH_CODE_SP_OTHER -> {
                dbHelper.insert(SharedPrefType.OTHER, values)
                notifyChange(uri, null)
                uri
            }
            else -> null
        }
    }

    @Synchronized
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        if (!permissionAllow(uri)) {
            return 0
        }
        val code = sURIMatcher.match(uri)
        var count = 0
        val dbHelper: SharedPrefDbHelper = SharedPrefDbHelper.instance
        when (code) {
            URI_MATCH_CODE_SP_DEFAULT -> {
                count = dbHelper.update(SharedPrefType.DEFAULT, values, selection, selectionArgs)
                if (count > 0) {
                    notifyChange(uri, null)
                }
            }
            URI_MATCH_CODE_SP_OTHER -> {
                count = dbHelper.update(SharedPrefType.OTHER, values, selection, selectionArgs)
                if (count > 0) {
                    notifyChange(uri, null)
                }
            }
            else -> {
            }
        }
        return count
    }

    // 变更通知。
    private fun notifyChange(uri: Uri, observer: ContentObserver?) = context?.contentResolver?.notifyChange(uri, observer)

    // 是否允许访问。
    private fun permissionAllow(uri: Uri?): Boolean {
        // 从ContentProvider中获取调用者的PackageName的方法为：getCallingPackage();
        // 但是此API要求miniSDK为19以上。不够通用。
        if (uri != null && context != null) {
            //当前应用包名
            val curPkgName = context.packageName
            //调用者应用包名
            val callPkgName = uri.getQueryParameter(ParameterKeyCallingPkgName)
            return if (curPkgName == callPkgName) {
                //当前应用，可直接访问
                true
            } else {
                //外部应用，需要看配置是否允许访问
                SharedPref.isAllowExternalVisit
            }
        }
        return false
    }

    companion object {
        private const val URI_MATCH_CODE_SP_DEFAULT = 1
        private const val URI_MATCH_CODE_SP_OTHER = 2
        private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)
        const val ParameterKeyCallingPkgName = "callingPkgName" //参数Key：调用方的包名

        private fun initURIMatcher(context: Context) {
            sURIMatcher.addURI(getAuthority(context), SharedPrefType.DEFAULT.uriPath, URI_MATCH_CODE_SP_DEFAULT)
            sURIMatcher.addURI(getAuthority(context), SharedPrefType.OTHER.uriPath, URI_MATCH_CODE_SP_OTHER)
        }

        private fun getAuthority(context: Context): String? {
            return Util.getProviderAuthority(context, SharedPrefProvider::class.java.name)
        }
    }
}