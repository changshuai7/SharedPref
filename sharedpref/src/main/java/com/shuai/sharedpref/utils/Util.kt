package com.shuai.sharedpref.utils

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor

internal object Util {
    /**
     * 关闭Cursor
     *
     * @param cursor
     */
    fun closeCursor(cursor: Cursor?) {
        try {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        } catch (ex: Exception) {
        }
    }

    /**
     * 根据providerName获取Provider中的Authority
     *
     * @param context
     * @param providerName
     * @return
     * 比如：
     * Util.getProviderAuthority(context, SharedPrefProvider::class.java.name)
     * Util.getProviderAuthority(context, SharedPrefProvider.class.getName());
     */
    fun getProviderAuthority(context: Context, providerName: String): String? {
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PROVIDERS)
            val providers = info.providers
            for (provider in providers) {
                if (providerName == provider.name) {
                    return provider.authority
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    //此方法可以获取Provider中的一些信息
    //    public static void getPackageInfo(Context context ,String packageName) {
    //        try {
    //            PackageInfo info = context.getPackageManager().getPackageInfo(packageName,
    //                    PackageManager.GET_PROVIDERS);
    //            ProviderInfo[] providers = info.providers;
    //            for (ProviderInfo provider : providers) {
    //                Log.d(TAG, "name is " + provider.name);
    //                Log.d(TAG, "authority is " + provider.authority);
    //                if (provider.metaData != null) {
    //                    Log.d(TAG, "metadata is " + provider.metaData.toString());
    //                    Log.d(TAG,
    //                            "resource in metadata is "
    //                                    + provider.metaData.getString("THE_KEY",
    //                                    "Unkonown"));
    //                }
    //            }
    //        } catch (Exception e) {
    //            Log.d(TAG, "package not found");
    //            e.printStackTrace();
    //        }
    //    }
}