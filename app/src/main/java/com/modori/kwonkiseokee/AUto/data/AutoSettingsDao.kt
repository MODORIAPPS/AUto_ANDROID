package com.modori.kwonkiseokee.AUto.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.modori.kwonkiseokee.AUto.utilities.PeriodType
import com.modori.kwonkiseokee.AUto.utilities.SourceType

@Dao
interface AutoSettingsDao {
    @Insert()
    fun insert(settings:AutoSettings)

    @Query("SELECT * FROM auto_settings WHERE id=:settingsId")
    fun getAutoSettingsById(settingsId: Int) : LiveData<AutoSettings>

    @Query("UPDATE auto_settings SET period_type=:periodType, source_type=:sourceType  WHERE id=:settingsId")
    fun update(settingsId:Int, periodType: PeriodType, sourceType: SourceType)

    @Query("SELECT * FROM auto_settings")
    fun getAutoSettingsList() : LiveData<List<AutoSettings>>

    // 삭제 기능
}