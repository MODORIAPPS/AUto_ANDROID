package com.modori.kwonkiseokee.AUto

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1

//    // ViewModel Setup
//
//    private val photoViewModel: PhotoViewModel by viewModels {
//        InjectorUtils.provideDevicePhotoViewModelFactory(this)
//    }
//    private val autoSettingsViewModel: AutoSettingsViewModel by viewModels {
//        InjectorUtils.provideAutoSettingsViewModelFactory(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isPermissionGranted()

        // navigation and fragment setup
        val albumFragment = AlbumFragment()
        val exploreFragment = ExploreFragment()
        val autoFragmentK = AutoFragmentK()

        supportFragmentManager.beginTransaction().replace(R.id.mainFrame, albumFragment).commit()
        navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.goTranslate -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, albumFragment).commit()
                    true
                }

                R.id.goPhotos -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, exploreFragment).commit()
                    true
                }

                R.id.goAutoSet -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, autoFragmentK).commit()
                    true
                }

                else -> false
            }
        }

    }

    private fun isPermissionGranted() {
        val check = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE -> {

            }
        }
    }
}