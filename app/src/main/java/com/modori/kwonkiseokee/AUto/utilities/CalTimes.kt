package com.modori.kwonkiseokee.AUto.utilities

object CalTimes{
    fun calToMin(day: Int, hour: Int, min: Int): Int {
        val DAY = day * 24 * 60
        val HOUR = hour * 60
        return DAY + HOUR + min
    }
}