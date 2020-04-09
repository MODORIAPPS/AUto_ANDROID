package com.modori.kwonkiseokee.AUto.utilities

import android.content.Context
import android.net.ConnectivityManager

object NetWorkTool {
    fun getNetWorkType(context: Context): Int {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = manager.activeNetworkInfo
        return if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIMAX || activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                1
            } else {
                2
            }
        } else {
            0
        }
    }
}

