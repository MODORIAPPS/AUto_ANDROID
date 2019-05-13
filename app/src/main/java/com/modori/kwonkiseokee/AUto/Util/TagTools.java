package com.modori.kwonkiseokee.AUto.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.modori.kwonkiseokee.AUto.tab2_frag.PREFS_FILE;

public class TagTools {

    private static List<String> tagLists;
    private static int total;
    private static void makeTagLists(Context context) {
        MakePreferences.getInstance().setSettings(context);

        String tag1 = MakePreferences.getInstance().getSettings().getString("tag1", "Landscape");
        String tag2 = MakePreferences.getInstance().getSettings().getString("tag2", "Office");
        String tag3 = MakePreferences.getInstance().getSettings().getString("tag3", "Milkyway");
        String tag4 = MakePreferences.getInstance().getSettings().getString("tag4", "Yosemite");
        String tag5 = MakePreferences.getInstance().getSettings().getString("tag5", "Roads");
        String tag6 = MakePreferences.getInstance().getSettings().getString("tag6", "home");

        Log.d("태그 상황", tag1 + "");
        tagLists = new ArrayList<>();
        if (tagLists.size() < 6) {
            tagLists.add(tag1);
            tagLists.add(tag2);
            tagLists.add(tag3);
            tagLists.add(tag4);
            tagLists.add(tag5);
            tagLists.add(tag6);

            Log.d("태그 저장 상황", tagLists.get(0));
        }


    }

    public static List<String> getTagLists(Context context) {
        makeTagLists(context);
        return tagLists;

    }

    static boolean overLapTagCheck(Context context, String tag) {
        boolean available = false;
        makeTagLists(context);
        for (String checkTag : tagLists) {
            if (checkTag.toUpperCase().equals(tag.toUpperCase())) {
                // 중복된 태그가 있음
                available = true;
            }
        }

        if (available) {
            //중복된 태그가 있는 경우
            return available;
        }
        return available;
    }

}
