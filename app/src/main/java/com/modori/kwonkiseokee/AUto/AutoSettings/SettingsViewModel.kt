package com.modori.kwonkiseokee.AUto.AutoSettings

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class SettingsViewModel : ViewModel() {

    var currentPage: MutableLiveData<Int> = MutableLiveData()
    var sourceType:MutableLiveData<SourceType> = MutableLiveData()
    var periodType:MutableLiveData<PeriodType> = MutableLiveData()

    fun getCurrentPosition(): LiveData<Int> {
        Log.d("getCurrentPosition", currentPage.value.toString())
        return currentPage
    }

    fun setCurrentPosition(position:Int){
        Log.d("setCurrentPosition", position.toString())
        currentPage.value = position
    }

    fun getSourceType() : LiveData<SourceType>{
        return sourceType
    }

    fun setSourceType(type: SourceType){
        sourceType.value = type
        Log.d("setSourceType", type.toString())
    }

    fun getPeriodType() : LiveData<PeriodType>{
        return periodType
    }

    fun setPeriodType(type:PeriodType){
        periodType.value = type
    }




}