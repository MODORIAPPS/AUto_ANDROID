package com.modori.kwonkiseokee.AUto.AutoSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.modori.kwonkiseokee.AUto.R

class Fragment_PeriodSettings(): Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_period_settings, container, false)
        return view
    }
}