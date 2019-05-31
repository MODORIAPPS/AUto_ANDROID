package com.modori.kwonkiseokee.AUto.Util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import static android.content.Context.ACTIVITY_SERVICE;

public class DEVICE_INFO {
    public static long getDeviceTotalRam(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);



        return mi.totalMem / 1048576L;
    }

    public static long getDeviceFreeRam(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);



        return mi.availMem / 1048576L;
    }
}
