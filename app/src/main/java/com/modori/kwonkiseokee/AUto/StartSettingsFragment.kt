package com.modori.kwonkiseokee.AUto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.modori.kwonkiseokee.AUto.utilities.InjectorUtils
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel

class StartSettingsFragment():Fragment(){

    private val viewModelAuto: AutoSettingsViewModel by viewModels {
        InjectorUtils.provideAutoSettingsViewModelFactory(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_start_settings, container, false)

        view.findViewById<Button>(R.id.startSettings).setOnClickListener {
            viewModelAuto._currentPage.value = 1
        }

        return view
    }
}