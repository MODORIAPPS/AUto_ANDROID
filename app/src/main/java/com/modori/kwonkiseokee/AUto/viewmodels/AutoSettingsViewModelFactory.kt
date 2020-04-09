package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.modori.kwonkiseokee.AUto.data.AutoSettingsRepository

class AutoSettingsViewModelFactory(private val repository:AutoSettingsRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AutoSettingsViewModel::class.java)){
            return AutoSettingsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")

    }

}