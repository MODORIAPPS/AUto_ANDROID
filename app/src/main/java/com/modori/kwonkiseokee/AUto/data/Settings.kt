package com.modori.kwonkiseokee.AUto.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.modori.kwonkiseokee.AUto.utilities.PeriodType
import com.modori.kwonkiseokee.AUto.utilities.SourceType

@Entity(tableName = "change_settings")
data class Settings(

        @NonNull
        @ColumnInfo(name = "settings_id")
        val id:Int,

        @NonNull
        @ColumnInfo(name = "source_type")
        val sourceType: SourceType,

        @NonNull
        @ColumnInfo(name = "period_type")
        val periodType: PeriodType,

        @NonNull
        @ColumnInfo(name = "isEnabled")
        val isEnabled:Boolean
)