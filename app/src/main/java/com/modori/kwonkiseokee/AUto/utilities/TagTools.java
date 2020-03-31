package com.modori.kwonkiseokee.AUto.utilities;

import com.modori.kwonkiseokee.AUto.data.Tag;

import java.util.List;

class TagTools {

    static boolean overLapTagCheck(List<Tag> lists, String tag) {
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
