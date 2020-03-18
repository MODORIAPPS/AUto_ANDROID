package com.modori.kwonkiseokee.AUto.AutoSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.modori.kwonkiseokee.AUto.R
import kotlinx.android.synthetic.main.auto_start_settings.*

class fragment_StartSettings():Fragment(){

    lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_start_settings, container, false)

        // ViewModel Setup
        viewModel = ViewModelProviders.of(activity!!).get(SettingsViewModel::class.java)

        view.findViewById<Button>(R.id.startSettings).setOnClickListener {
            viewModel.setCurrentPosition(1)
        }

        return view
    }
}