package com.modori.kwonkiseokee.AUto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.modori.kwonkiseokee.AUto.utilities.InjectorUtils
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel

class PeriodSettingsFragment(): Fragment(){

    private val viewModelAuto: AutoSettingsViewModel by viewModels {
        InjectorUtils.provideAutoSettingsViewModelFactory(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_period_settings, container, false)
        return view
    }
}