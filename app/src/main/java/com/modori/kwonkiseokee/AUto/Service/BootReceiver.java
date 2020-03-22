package com.modori.kwonkiseokee.AUto.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.modori.kwonkiseokee.AUto.utilities.calTimes;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static com.modori.kwonkiseokee.AUto.tab3_frag.PREFS_FILE;

public class BootReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, 0);


        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("부팅 감지", "감지됨, 서비스 실행 대기중");

            if (settings.getBoolean("BootLaunch", false)) {
                //context.startActivity(new Intent(context, MainActivity.class));

                Log.d("서비스 알림", "서비스 시작됨");

                //tab3_frag.setAutoChangeSlide(settings.getInt("Cycle", 5));

                int cycle = calTimes.calToMin(settings.getInt("_DAY", 0), settings.getInt("_HOUR", 0), settings.getInt("_MIN", 0));
                setAutoChangeSlide(cycle);
                //tab3_frag.setAutoChangeSlide(cycle);
            }


        }
    }

    public void setAutoChangeSlide(int _cycle) {
        Intent intent = new Intent(context, SetWallpaperJob.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        calendar.add(Calendar.MINUTE, _cycle);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), _cycle * 60000, sender);
    }


}
