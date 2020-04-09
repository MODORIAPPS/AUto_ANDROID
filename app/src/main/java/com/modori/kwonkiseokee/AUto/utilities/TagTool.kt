package com.modori.kwonkiseokee.AUto.utilities

import com.modori.kwonkiseokee.AUto.data.Tag

object TagTool {
    fun overLapTagCheck(lists: List<Tag>, tag: String): Boolean {
        println("받은 태그 : $tag")
        println("받은 태그리스트  : $lists")
        var available = false
        for ((_, tag1) in lists) {
            if (tag1.toUpperCase() == tag.toUpperCase()) {
                // 중복된 태그가 있음
                available = true
            }
        }
        return if (available) {
            //중복된 태그가 있는 경우
            available
        } else false
    }
}

