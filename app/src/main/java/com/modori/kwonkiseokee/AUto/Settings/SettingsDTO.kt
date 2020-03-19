package com.modori.kwonkiseokee.AUto.Settings

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import com.modori.kwonkiseokee.AUto.AutoSettings.PeriodType
import com.modori.kwonkiseokee.AUto.AutoSettings.SourceType

@Entity(tableName = "change_settings")
data class SettingsDTO(

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