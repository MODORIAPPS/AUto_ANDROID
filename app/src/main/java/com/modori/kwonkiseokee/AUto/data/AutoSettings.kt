package com.modori.kwonkiseokee.AUto.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.modori.kwonkiseokee.AUto.utilities.PeriodType
import com.modori.kwonkiseokee.AUto.utilities.SourceType

@Entity(tableName = "auto_settings")
data class AutoSettings(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val settingsId:Int = 0,

        @ColumnInfo(name = "source_type")
        val sourceType: SourceType, // enum

        @ColumnInfo(name = "period_type")
        val periodType: PeriodType // enum
)