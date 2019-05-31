package com.modori.kwonkiseokee.AUto.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.function.BinaryOperator;

public class NETWORKS {
    public static int getNetWorkType(Context context){
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();

        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            if(activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIMAX || activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                return 1;
            }else{
                return 2;
            }
        }else{
            return 0;
        }


    }
}
