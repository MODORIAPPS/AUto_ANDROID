package com.modori.kwonkiseokee.AUto.data

import androidx.lifecycle.LiveData
import com.modori.kwonkiseokee.AUto.utilities.PeriodType
import com.modori.kwonkiseokee.AUto.utilities.SourceType

class AutoSettingsRepository(private val dao:AutoSettingsDao) {

    suspend fun update(id:Int, periodType: PeriodType, sourceType: SourceType){
        dao.update(id, periodType, sourceType)
    }

    suspend fun getAutoSettingsById(id:Int) : LiveData<AutoSettings>{
        return dao.getAutoSettingsById(id)
    }
}