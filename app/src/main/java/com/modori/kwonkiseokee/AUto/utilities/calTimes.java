package com.modori.kwonkiseokee.AUto.utilities;

import com.modori.kwonkiseokee.AUto.AutoFragment;

public class calTimes {

    public static int calToMin(int day, int hour, int min) {
        int DAY = day * 24 * 60;
        int HOUR = hour * 60;
        return DAY + HOUR + min;
    }

    public static void minToThreeTypes(int min) {
        //다양한 타입 입력 준비 되지 않음.

        AutoFragment._MIN = min;
    }

}
