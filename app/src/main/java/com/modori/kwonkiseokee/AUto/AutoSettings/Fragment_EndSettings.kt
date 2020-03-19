package com.modori.kwonkiseokee.AUto.AutoSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.modori.kwonkiseokee.AUto.R
import kotlinx.android.synthetic.main.auto_end_settings.*
import kotlinx.android.synthetic.main.auto_start_settings.*

class Fragment_EndSettings():Fragment(){

    lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_end_settings, container, false)

        // ViewModel Setup
        viewModel = ViewModelProviders.of(activity!!).get(SettingsViewModel::class.java)

        // SourceType
        viewModel.getSourceType().observe(this, androidx.lifecycle.Observer {
            val sourceType = viewModel.getSourceType().value
            if(sourceType != null){
                showSourceType.text = sourceType.source
            }
        })

        // PeriodType
        viewModel.getPeriodType().observe(this, androidx.lifecycle.Observer {
            val periodType = viewModel.getPeriodType().value

            if(periodType != null){
                showPeriodType.text = periodType.info
            }
        })



        return view
    }
}