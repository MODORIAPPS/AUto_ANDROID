package com.modori.kwonkiseokee.AUto.Tab2_frag;

import android.content.Context;
import android.util.Log;

import com.modori.kwonkiseokee.AUto.Util.MakePreferences;

import java.util.ArrayList;
import java.util.List;

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

    public static void resetTags(Context context){
        MakePreferences.getInstance().setSettings(context);

        MakePreferences.getInstance().getEditor().putString("tag1", "Landscape").apply();
        MakePreferences.getInstance().getEditor().putString("tag2", "Office").apply();
        MakePreferences.getInstance().getEditor().putString("tag3", "Milkyway").apply();
        MakePreferences.getInstance().getEditor().putString("tag4", "Yosemite").apply();
        MakePreferences.getInstance().getEditor().putString("tag5", "Roads").apply();
        MakePreferences.getInstance().getEditor().putString("tag6", "home").apply();

    }

    public static List<String> getTagLists(Context context) {
        makeTagLists(context);
        return tagLists;

    }

    public static boolean overLapTagCheck(List<Tag> lists, String tag) {
        System.out.println("받은 태그 : "+ tag);
        System.out.println("받은 태그리스트  : "+ lists);
        boolean available = false;
        for (Tag checkTag : lists) {
            if (checkTag.getTag().toUpperCase().equals(tag.toUpperCase())) {
                // 중복된 태그가 있음
                available = true;
            }
        }

        if (available) {
            //중복된 태그가 있는 경우
            return available;
        }
        return false;
    }

}
