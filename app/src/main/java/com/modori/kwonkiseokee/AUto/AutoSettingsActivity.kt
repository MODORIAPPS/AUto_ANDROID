package com.modori.kwonkiseokee.AUto

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.modori.kwonkiseokee.AUto.adapters.AutoSettingsAdapter
import com.modori.kwonkiseokee.AUto.utilities.InjectorUtils
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import kotlinx.android.synthetic.main.activity_auto_settings.*

class AutoSettingsActivity : AppCompatActivity() {

    private val viewModelAuto: AutoSettingsViewModel by viewModels {
        InjectorUtils.provideAutoSettingsViewModelFactory(this)
    }
    var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_settings)


        setSupportActionBar(autoToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "닫기"


        // ViewPager setup
        val list: List<Fragment> = listOf(StartSettingsFragment(), SourceSettingsFragment(), PeriodSettingsFragment(), EndSettingsFragment())
        val fm = supportFragmentManager
        val autoSettingsAdapter = AutoSettingsAdapter(fm, this, list)
        settingsViewPager.adapter = autoSettingsAdapter

        // setUp ViewModel
        viewModelAuto.currentPage.observe(this, androidx.lifecycle.Observer {
            Log.d("AutoSettings", "CurrentPosition change Detected")
            val position = viewModelAuto.currentPage.value
            if (position != null) {
                Log.d("AutoSettings", "Set to position $position")
                //autoSettingsAdapter.getItemPosition(position)
                currentPosition = position
                settingsViewPager.currentItem = position

                if (position == 0) {
                    supportActionBar?.title = "닫기"
                } else {
                    supportActionBar?.title = "뒤로가기"
                }

            } else {
                Toast.makeText(this, "죄송합니다. 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                Log.d("AutoSettings", "Set to position 0")
                //autoSettingsAdapter.getItemPosition(0)
                settingsViewPager.currentItem = 0
            }

            autoSettingsAdapter.notifyDataSetChanged()
        })

    }

    private fun backBtnBehavior() {
        if (currentPosition == 0) {
            finish()
        } else {
            settingsViewPager.currentItem = currentPosition - 1
            viewModelAuto._currentPage.value = currentPosition - 1
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                backBtnBehavior()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
