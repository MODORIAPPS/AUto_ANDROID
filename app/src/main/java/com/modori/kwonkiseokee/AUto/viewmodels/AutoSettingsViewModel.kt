package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modori.kwonkiseokee.AUto.data.AutoSettingsRepository
import com.modori.kwonkiseokee.AUto.utilities.PeriodType
import com.modori.kwonkiseokee.AUto.utilities.SourceType
import kotlinx.coroutines.async


class AutoSettingsViewModel(private val repository: AutoSettingsRepository) : ViewModel() {

    var isWorking: MutableLiveData<Boolean> = MutableLiveData()

    var _currentPage: MutableLiveData<Int> = MutableLiveData()
    val currentPage: LiveData<Int>
        get() = _currentPage

    var _sourceType: MutableLiveData<SourceType> = MutableLiveData()
    val sourceType: LiveData<SourceType>
        get() = _sourceType

    var _periodType: MutableLiveData<PeriodType> = MutableLiveData()
    val periodType: LiveData<PeriodType>
        get() = _periodType


    fun getAutoSettingsById(id: Int) = viewModelScope.async {
        repository.getAutoSettingsById(id)
    }



}