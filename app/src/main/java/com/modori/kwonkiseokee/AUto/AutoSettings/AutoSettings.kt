package com.modori.kwonkiseokee.AUto.AutoSettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.modori.kwonkiseokee.AUto.R
import kotlinx.android.synthetic.main.activity_auto_settings.*

class AutoSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_settings)


        setSupportActionBar(autoToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "자동 설정하기"


        // ViewPager setup
        val list: List<Fragment> = listOf(fragment_StartSettings(), fragment_SourceSettings(), fragment_PeriodSettings())
        val fm = supportFragmentManager
        val autoSettingsAdapter = ViewPagerAdapter(fm, this, list)
        settingsViewPager.adapter = autoSettingsAdapter

        // setUp ViewModel
        val viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        viewModel.getCurrentPosition().observe(this, androidx.lifecycle.Observer {
            Log.d("AutoSettings", "CurrentPosition change Detected")
            val position = viewModel.getCurrentPosition().value
            if (position != null) {
                Log.d("AutoSettings", "Set to position $position")
                //autoSettingsAdapter.getItemPosition(position)
                settingsViewPager.currentItem = position

            } else {
                Toast.makeText(this, "죄송합니다. 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                Log.d("AutoSettings", "Set to position 0")
                //autoSettingsAdapter.getItemPosition(0)
                settingsViewPager.currentItem = 0
            }

            autoSettingsAdapter.notifyDataSetChanged()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                // 취소할 건지 물어보기
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
