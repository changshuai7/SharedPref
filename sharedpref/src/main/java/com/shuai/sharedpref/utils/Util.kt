package com.shuai.sharedpref.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
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

    /**
     * 根据providerName获取Provider中的Authority
     *
     * @param context
     * @param providerName
     * @return
     */
    public static String getProviderAuthority(Context context, String providerName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PROVIDERS);
            ProviderInfo[] providers = info.providers;
            for (ProviderInfo provider : providers) {
                if (providerName.equals(provider.name)) {
                    return provider.authority;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
