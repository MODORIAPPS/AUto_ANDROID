package com.modori.kwonkiseokee.AUto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.modori.kwonkiseokee.AUto.utilities.InjectorUtils
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import kotlinx.android.synthetic.main.auto_end_settings.*

class EndSettingsFragment():Fragment(){

    private val viewModelAuto: AutoSettingsViewModel by viewModels {
        InjectorUtils.provideAutoSettingsViewModelFactory(requireActivity())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_end_settings, container, false)

        // SourceType
        viewModelAuto.sourceType.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val sourceType = viewModelAuto.sourceType.value
            if(sourceType != null){
                showSourceType.text = sourceType.source
            }
        })

        // PeriodType
        viewModelAuto.periodType.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val periodType = viewModelAuto.periodType.value

            if(periodType != null){
                showPeriodType.text = periodType.info
            }
        })



        return view
    }
}