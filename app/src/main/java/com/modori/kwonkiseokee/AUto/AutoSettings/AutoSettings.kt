package com.modori.kwonkiseokee.AUto.AutoSettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.modori.kwonkiseokee.AUto.R
import kotlinx.android.synthetic.main.activity_auto_settings.*

class AutoSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_settings)

        val list = listOf<Fragment>(StartSettings(), SourceSettings(), TimeSettings())
        val fm = supportFragmentManager
        settingsViewPager.adapter = ViewPagerAdapter(fm,list)
    }
}
