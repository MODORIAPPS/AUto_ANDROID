package com.modori.kwonkiseokee.AUto.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class MakePreferences {

    private static MakePreferences instance = null;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private MakePreferences() {

    }

    public static MakePreferences getInstance() {
        if (instance == null) {
            Log.d("MakePreferences", "새 객체 생성됨");
            instance = new MakePreferences();
        }

        return instance;
    }

    public SharedPreferences getSettings() {
        return settings;
    }

    SharedPreferences.Editor getEditor() {

        return editor;
    }

    public void setSettings(Context context) {
        String PREFS_FILE = "PrefsFile";
        settings = context.getSharedPreferences(PREFS_FILE, 0);
        editor = settings.edit();

    }

}
