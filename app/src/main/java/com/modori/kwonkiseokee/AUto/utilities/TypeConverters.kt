package com.modori.kwonkiseokee.AUto.utilities

import androidx.room.TypeConverter

class TypeConverters {

    @TypeConverter
    fun integerToSourceType(value: Int): SourceType {
        return when (value) {
            0 -> SourceType.DOWNLOADED
            1 -> SourceType.MY_FOLDER
            2 -> SourceType.GALLERY_PICK

            else -> SourceType.DOWNLOADED
        }
    }

    @TypeConverter
    fun sourceTypeToInteger(sourceType: SourceType): Int? {
        return when (sourceType) {
            SourceType.DOWNLOADED -> 0
            SourceType.MY_FOLDER -> 1
            SourceType.GALLERY_PICK -> 2
            else -> 0
        }
    }

    @TypeConverter
    fun integerToPeriodType(value: Int): PeriodType {
        return when (value) {
            0 -> PeriodType.TWO_DAYS
            1 -> PeriodType.ONE_DAY
            2 -> PeriodType.TWE_HOURS
            3 -> PeriodType.SIX_HOURS
            4 -> PeriodType.ONE_HOUR
            5 -> PeriodType.THR_MINS
            6 -> PeriodType.FIT_MINS
            7 -> PeriodType.ONE_MIN
            else -> PeriodType.ONE_DAY
        }
    }

    @TypeConverter
    fun periodTypeToInteger(periodType: PeriodType): Int {
        return when (periodType) {
            PeriodType.TWO_DAYS -> 0
            PeriodType.ONE_DAY -> 1
            PeriodType.TWE_HOURS -> 2
            PeriodType.SIX_HOURS -> 3
            PeriodType.ONE_HOUR -> 4
            PeriodType.THR_MINS -> 5
            PeriodType.FIT_MINS -> 6
            PeriodType.ONE_MIN -> 7
            else -> 1
        }
    }
}