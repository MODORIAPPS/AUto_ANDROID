package com.modori.kwonkiseokee.AUto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import kotlinx.android.synthetic.main.auto_end_settings.*

class EndSettingsFragment():Fragment(){

    lateinit var viewModelAuto: AutoSettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_end_settings, container, false)

        // ViewModel Setup
        viewModelAuto = ViewModelProviders.of(this).get(AutoSettingsViewModel::class.java)

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